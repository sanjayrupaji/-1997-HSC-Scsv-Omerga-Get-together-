package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.ClassmateEntity
import com.example.data.local.EventEntity
import com.example.data.local.InitialDataLoader
import com.example.data.model.CollegeMemoryItem
import com.example.data.model.CommitteeMember
import com.example.data.repository.ReunionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen(val titleMarathi: String, val icon: String) {
    HOME("मुख्य", "home"),
    CLASSMATES("मित्र परिवार", "people"),
    EVENTS("कार्यक्रम रूपरेषा", "calendar"),
    MEMORIES("आठवणी", "sparkles")
}

data class ReunionStats(
    val totalCount: Int = 0,
    val attendingCount: Int = 0,
    val tentativeCount: Int = 0,
    val absentCount: Int = 0,
    val uniqueCitiesCount: Int = 0,
    val uniqueVillagesCount: Int = 0
)

class ReunionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ReunionRepository(AppDatabase.getInstance(application))

    // Navigation & UI state
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _selectedClassmate = MutableStateFlow<ClassmateEntity?>(null)
    val selectedClassmate: StateFlow<ClassmateEntity?> = _selectedClassmate.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _categoryFilter = MutableStateFlow("सर्व")
    val categoryFilter: StateFlow<String> = _categoryFilter.asStateFlow()

    private val _attendanceFilter = MutableStateFlow("सर्व")
    val attendanceFilter: StateFlow<String> = _attendanceFilter.asStateFlow()

    private val _showAddEditDialog = MutableStateFlow(false)
    val showAddEditDialog: StateFlow<Boolean> = _showAddEditDialog.asStateFlow()

    private val _classmateBeingEdited = MutableStateFlow<ClassmateEntity?>(null)
    val classmateBeingEdited: StateFlow<ClassmateEntity?> = _classmateBeingEdited.asStateFlow()

    private val _showImportExportDialog = MutableStateFlow(false)
    val showImportExportDialog: StateFlow<Boolean> = _showImportExportDialog.asStateFlow()

    private val _showEventDialog = MutableStateFlow(false)
    val showEventDialog: StateFlow<Boolean> = _showEventDialog.asStateFlow()

    private val _eventBeingEdited = MutableStateFlow<EventEntity?>(null)
    val eventBeingEdited: StateFlow<EventEntity?> = _eventBeingEdited.asStateFlow()

    private val _googleFormUrl = MutableStateFlow("https://forms.gle/scsm-omerga-1997-reunion")
    val googleFormUrl: StateFlow<String> = _googleFormUrl.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    val committeeMembers: List<CommitteeMember> = InitialDataLoader.getCommitteeMembers()
    val memories: List<CollegeMemoryItem> = InitialDataLoader.getMemories()

    // Classmates data flow
    private val rawClassmates = repository.allClassmates

    val eventsList: StateFlow<List<EventEntity>> = repository.allEvents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredClassmates: StateFlow<List<ClassmateEntity>> = combine(
        rawClassmates,
        _searchQuery,
        _categoryFilter,
        _attendanceFilter
    ) { list, query, category, rsvp ->
        list.filter { c ->
            val matchesQuery = query.isBlank() ||
                    c.fullName.contains(query, ignoreCase = true) ||
                    c.nickname.contains(query, ignoreCase = true) ||
                    c.nativeVillage.contains(query, ignoreCase = true) ||
                    c.currentCity.contains(query, ignoreCase = true) ||
                    c.jobTitle.contains(query, ignoreCase = true) ||
                    c.organization.contains(query, ignoreCase = true) ||
                    c.thoughtsAndMemories.contains(query, ignoreCase = true)

            val matchesCategory = category == "सर्व" || c.occupationCategory.contains(category)
            val matchesRsvp = attendanceFilter.value == "सर्व" || c.attendanceStatus == rsvp

            matchesQuery && matchesCategory && matchesRsvp
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val stats: StateFlow<ReunionStats> = rawClassmates.combine(eventsList) { classmates, _ ->
        val total = classmates.size
        val attending = classmates.count { it.attendanceStatus.contains("उपस्थित") || it.attendanceStatus.contains("Attending") }
        val tentative = classmates.count { it.attendanceStatus.contains("नक्की") || it.attendanceStatus.contains("Tentative") }
        val absent = total - attending - tentative
        val cities = classmates.map { it.currentCity.trim().lowercase() }.filter { it.isNotBlank() }.distinct().size
        val villages = classmates.map { it.nativeVillage.trim().lowercase() }.filter { it.isNotBlank() }.distinct().size
        ReunionStats(
            totalCount = total,
            attendingCount = attending,
            tentativeCount = tentative,
            absentCount = maxOf(0, absent),
            uniqueCitiesCount = cities,
            uniqueVillagesCount = villages
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReunionStats())

    init {
        viewModelScope.launch {
            repository.initializeDefaultDataIfNeeded()
        }
    }

    fun setScreen(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategoryFilter(category: String) {
        _categoryFilter.value = category
    }

    fun setAttendanceFilter(rsvp: String) {
        _attendanceFilter.value = rsvp
    }

    fun selectClassmate(classmate: ClassmateEntity?) {
        _selectedClassmate.value = classmate
    }

    fun openAddClassmateDialog() {
        _classmateBeingEdited.value = null
        _showAddEditDialog.value = true
    }

    fun openEditClassmateDialog(classmate: ClassmateEntity) {
        _classmateBeingEdited.value = classmate
        _showAddEditDialog.value = true
    }

    fun dismissAddEditDialog() {
        _showAddEditDialog.value = false
        _classmateBeingEdited.value = null
    }

    fun saveClassmate(classmate: ClassmateEntity) {
        viewModelScope.launch {
            if (classmate.id == 0L) {
                repository.insertClassmate(classmate)
                showToast("नवीन मित्राची माहिती जोडली गेली!")
            } else {
                repository.updateClassmate(classmate)
                if (_selectedClassmate.value?.id == classmate.id) {
                    _selectedClassmate.value = classmate
                }
                showToast("मित्राची माहिती अद्ययावत केली!")
            }
            dismissAddEditDialog()
        }
    }

    fun deleteClassmate(classmate: ClassmateEntity) {
        viewModelScope.launch {
            repository.deleteClassmate(classmate)
            if (_selectedClassmate.value?.id == classmate.id) {
                _selectedClassmate.value = null
            }
            showToast("माहिती हटवली गेली")
        }
    }

    fun openImportExportDialog() {
        _showImportExportDialog.value = true
    }

    fun dismissImportExportDialog() {
        _showImportExportDialog.value = false
    }

    fun importData(rawText: String, onComplete: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val result = repository.importClassmates(rawText)
            if (result.isSuccess) {
                val count = result.getOrNull() ?: 0
                showToast("$count मित्रांची माहिती यशस्वीरीत्या आयात झाली!")
                onComplete(true, "$count मित्रांची माहिती आयात झाली.")
                dismissImportExportDialog()
            } else {
                val err = result.exceptionOrNull()?.localizedMessage ?: "त्रुटी आली"
                onComplete(false, err)
            }
        }
    }

    fun getExportCsv(): String {
        return repository.exportToCsv(filteredClassmates.value)
    }

    fun resetToDefaultData() {
        viewModelScope.launch {
            repository.resetToSampleData()
            showToast("मूळ नमुना माहिती पूर्ववत केली!")
        }
    }

    fun updateGoogleFormUrl(url: String) {
        _googleFormUrl.value = url.trim()
        showToast("गुगल फॉर्म लिंक अद्ययावत केली!")
    }

    // Event operations
    fun openAddEventDialog() {
        _eventBeingEdited.value = null
        _showEventDialog.value = true
    }

    fun openEditEventDialog(event: EventEntity) {
        _eventBeingEdited.value = event
        _showEventDialog.value = true
    }

    fun dismissEventDialog() {
        _showEventDialog.value = false
        _eventBeingEdited.value = null
    }

    fun saveEvent(event: EventEntity) {
        viewModelScope.launch {
            if (event.id == 0L) {
                repository.insertEvent(event)
                showToast("नवीन कार्यक्रम जोडला!")
            } else {
                repository.updateEvent(event)
                showToast("कार्यक्रम अद्ययावत केला!")
            }
            dismissEventDialog()
        }
    }

    fun deleteEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.deleteEvent(event)
            showToast("कार्यक्रम हटवला!")
        }
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }

    private fun showToast(msg: String) {
        _snackbarMessage.value = msg
    }

    // Utility Intent launchers
    fun dialPhone(context: Context, phoneNumber: String) {
        val cleanNumber = phoneNumber.replace(Regex("[^0-9+]"), "")
        if (cleanNumber.isBlank()) {
            Toast.makeText(context, "फोन नंबर उपलब्ध नाही", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$cleanNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "कॉल करता आला नाही", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, phoneNumber: String, classmateName: String) {
        val cleanNumber = phoneNumber.replace(Regex("[^0-9]"), "")
        if (cleanNumber.isBlank()) {
            Toast.makeText(context, "व्हॉट्सॲप नंबर उपलब्ध नाही", Toast.LENGTH_SHORT).show()
            return
        }
        val fullNumber = if (cleanNumber.length == 10) "91$cleanNumber" else cleanNumber
        val defaultMsg = "जय शिवराय $classmateName! श्री छत्रपती शिवाजी महाविद्यालय उमरगा १९९७ बॅचच्या गेट-टुगेदर संदर्भात संपर्क करत आहे."
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://api.whatsapp.com/send?phone=$fullNumber&text=${Uri.encode(defaultMsg)}")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "व्हॉट्सॲप उघडता आले नाही", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendEmail(context: Context, email: String, classmateName: String) {
        if (email.isBlank()) {
            Toast.makeText(context, "ईमेल उपलब्ध नाही", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, "श्री छत्रपती शिवाजी महाविद्यालय उमरगा - १९९७ बॅच गेट-टुगेदर")
                putExtra(Intent.EXTRA_TEXT, "सस्नेह नमस्कार $classmateName,\n\nआपल्या १९९७ बारावी बॅचच्या गेट-टुगेदर कार्यक्रमाच्या संदर्भात...")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "ईमेल ॲप उघडता आले नाही", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareClassmateCard(context: Context, classmate: ClassmateEntity) {
        val text = buildString {
            append("🎓 *श्री छत्रपती शिवाजी महाविद्यालय उमरगा*\n")
            append("✨ *१९९७ बारावी वर्ग मित्र परिचय*\n\n")
            append("👤 *नाव:* ${classmate.fullName}\n")
            if (classmate.nickname.isNotBlank()) append("🏷️ *टोपणनाव:* ${classmate.nickname}\n")
            append("📚 *शाखा:* ${classmate.stream}\n")
            append("💼 *व्यवसाय/काम:* ${classmate.jobTitle} (${classmate.organization})\n")
            append("🏡 *मूळ गाव:* ${classmate.nativeVillage}\n")
            append("📍 *सध्याचे शहर:* ${classmate.currentCity}\n")
            append("📞 *मोबाईल:* ${classmate.phone}\n")
            if (classmate.bloodGroup.isNotBlank()) append("🩸 *रक्तगट:* ${classmate.bloodGroup}\n")
            append("🗓️ *गेट-टुगेदर हजेरी:* ${classmate.attendanceStatus}\n\n")
            if (classmate.thoughtsAndMemories.isNotBlank()) {
                append("💭 *मनातील विचार व आठवणी:*\n\"${classmate.thoughtsAndMemories}\"\n\n")
            }
            append("📱 *मित्रमेळावा १९९७ ॲप द्वारे सामायिक*")
        }
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(Intent.createChooser(intent, "${classmate.fullName} यांची माहिती शेअर करा"))
    }

    fun openGoogleForm(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(_googleFormUrl.value))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "गुगल फॉर्म उघडता आला नाही", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareGoogleFormLink(context: Context) {
        val shareText = "🎓 *श्री छत्रपती शिवाजी महाविद्यालय उमरगा*\n" +
                "🎉 *१९९७ बारावी वर्ग (HSC Batch) गेट-टुगेदर*\n\n" +
                "सर्व वर्गमित्रांना विनंती की खालील गुगल फॉर्म भरून आपली ताजी माहिती, फोटो, काम व कॉलेजच्या आठवणी नोंदवाव्यात:\n" +
                "${_googleFormUrl.value}\n\n" +
                "चला पुन्हा एकदा भेटूया, जुन्या आठवणींना उजाळा देऊया! 🙏"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "गुगल फॉर्म लिंक शेअर करा"))
    }

    fun openCollegeMap(context: Context) {
        try {
            val geoUri = Uri.parse("geo:0,0?q=Shri+Chhatrapati+Shivaji+College+Omerga")
            val intent = Intent(Intent.ACTION_VIEW, geoUri)
            context.startActivity(intent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=Shri+Chhatrapati+Shivaji+College+Omerga"))
            context.startActivity(webIntent)
        }
    }
}
