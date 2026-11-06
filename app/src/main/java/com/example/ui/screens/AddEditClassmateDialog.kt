package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.ClassmateEntity
import com.example.ui.theme.SaffronPrimary
import com.example.ui.viewmodel.ReunionViewModel

val StreamOptions = listOf("विज्ञान (Science)", "कला (Arts)", "वाणिज्य (Commerce)")

val OccupationOptions = listOf(
    "व्यवसाय / उद्योग",
    "वैद्यकीय / डॉक्टर",
    "शिक्षण / प्राध्यापक",
    "शासकीय सेवा",
    "शेती / कृषी व्यवसाय",
    "अभियांत्रिकी / IT",
    "बँकिंग / वित्त",
    "इतर"
)

val BloodGroupOptions = listOf("O+ve", "O-ve", "A+ve", "A-ve", "B+ve", "B-ve", "AB+ve", "AB-ve", "माहित नाही")

val RsvpOptions = listOf("उपस्थित राहणार", "नक्की नाही", "उपस्थित राहू शकत नाही")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditClassmateDialog(
    classmate: ClassmateEntity?,
    viewModel: ReunionViewModel,
    onDismiss: () -> Unit
) {
    var fullName by remember { mutableStateOf(classmate?.fullName ?: "") }
    var nickname by remember { mutableStateOf(classmate?.nickname ?: "") }
    var stream by remember { mutableStateOf(classmate?.stream ?: "विज्ञान (Science)") }
    var occupationCategory by remember { mutableStateOf(classmate?.occupationCategory ?: "व्यवसाय / उद्योग") }
    var jobTitle by remember { mutableStateOf(classmate?.jobTitle ?: "") }
    var organization by remember { mutableStateOf(classmate?.organization ?: "") }
    var nativeVillage by remember { mutableStateOf(classmate?.nativeVillage ?: "उमरगा") }
    var currentCity by remember { mutableStateOf(classmate?.currentCity ?: "उमरगा") }
    var address by remember { mutableStateOf(classmate?.address ?: "") }
    var phone by remember { mutableStateOf(classmate?.phone ?: "") }
    var whatsapp by remember { mutableStateOf(classmate?.whatsapp ?: "") }
    var email by remember { mutableStateOf(classmate?.email ?: "") }
    var bloodGroup by remember { mutableStateOf(classmate?.bloodGroup ?: "O+ve") }
    var thoughtsAndMemories by remember { mutableStateOf(classmate?.thoughtsAndMemories ?: "") }
    var favoriteMemory by remember { mutableStateOf(classmate?.favoriteMemory ?: "") }
    var attendanceStatus by remember { mutableStateOf(classmate?.attendanceStatus ?: "उपस्थित राहणार") }

    var isError by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f)
                .testTag("add_edit_dialog")
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Column {
                        Text(
                            text = if (classmate == null) "नवीन मित्राची माहिती जोडा" else "माहिती संपादित करा",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "गुगल फॉर्म माहिती संकलन",
                            style = MaterialTheme.typography.bodySmall,
                            color = SaffronPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "बंद करा")
                    }
                }

                HorizontalDivider()

                // Scrollable Form Fields
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Full Name
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            isError = false
                        },
                        label = { Text("मित्राचे पूर्ण नाव *") },
                        placeholder = { Text("उदा. सचिन प्रभाकर पाटील") },
                        isError = isError && fullName.isBlank(),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_full_name")
                    )

                    // Nickname
                    OutlinedTextField(
                        value = nickname,
                        onValueChange = { nickname = it },
                        label = { Text("कॉलेजमधील टोपणनाव (ऐच्छिक)") },
                        placeholder = { Text("उदा. सच्या / गजाभाऊ") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_nickname")
                    )

                    // Stream Dropdown
                    DropdownSelector(
                        label = "बारावी शाखा (Stream)",
                        selectedOption = stream,
                        options = StreamOptions,
                        onOptionSelected = { stream = it }
                    )

                    // Occupation Category Dropdown
                    DropdownSelector(
                        label = "कामाचे / व्यवसायाचे क्षेत्र",
                        selectedOption = occupationCategory,
                        options = OccupationOptions,
                        onOptionSelected = { occupationCategory = it }
                    )

                    // Job Title & Organization
                    OutlinedTextField(
                        value = jobTitle,
                        onValueChange = { jobTitle = it },
                        label = { Text("कामाचे स्वरूप / हुद्दा / व्यवसाय") },
                        placeholder = { Text("उदा. एम.डी. मेडिसिन / संचालक / मुख्याध्यापक") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = organization,
                        onValueChange = { organization = it },
                        label = { Text("कंपनी / हॉस्पिटल / संस्था / व्यवसाय नाव") },
                        placeholder = { Text("उदा. संजीवनी हॉस्पिटल / जिल्हा परिषद हायस्कूल") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Native Village & Current City
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = nativeVillage,
                            onValueChange = { nativeVillage = it },
                            label = { Text("मूळ गाव *") },
                            placeholder = { Text("उदा. गुंजोटी / मुरुम") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedTextField(
                            value = currentCity,
                            onValueChange = { currentCity = it },
                            label = { Text("सध्याचे शहर *") },
                            placeholder = { Text("उदा. पुणे / सोलापूर") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Contact info
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = phone,
                            onValueChange = {
                                phone = it
                                if (whatsapp.isBlank()) whatsapp = it
                            },
                            label = { Text("मोबाईल नंबर *") },
                            placeholder = { Text("९८२२०...") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedTextField(
                            value = whatsapp,
                            onValueChange = { whatsapp = it },
                            label = { Text("व्हॉट्सॲप नंबर") },
                            placeholder = { Text("९८२२०...") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("ईमेल पत्ता") },
                        placeholder = { Text("friend@example.com") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Blood Group & RSVP Attendance
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            DropdownSelector(
                                label = "रक्तगट",
                                selectedOption = bloodGroup,
                                options = BloodGroupOptions,
                                onOptionSelected = { bloodGroup = it }
                            )
                        }

                        Box(modifier = Modifier.weight(1.3f)) {
                            DropdownSelector(
                                label = "गेट-टुगेदर हजेरी",
                                selectedOption = attendanceStatus,
                                options = RsvpOptions,
                                onOptionSelected = { attendanceStatus = it }
                            )
                        }
                    }

                    // Thoughts and Memories
                    OutlinedTextField(
                        value = thoughtsAndMemories,
                        onValueChange = { thoughtsAndMemories = it },
                        label = { Text("मनातील विचार / मित्रांसाठी संदेश") },
                        placeholder = { Text("कॉलेजचे ते सुंदर दिवस आणि मित्रांना उद्देशून दोन शब्द...") },
                        minLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_thoughts")
                    )

                    OutlinedTextField(
                        value = favoriteMemory,
                        onValueChange = { favoriteMemory = it },
                        label = { Text("महाविद्यालयातील आवडती आठवण") },
                        placeholder = { Text("उदा. सायन्स एक्झिबिशन / स्नेहसंमेलन नाटक") },
                        minLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("सध्याचा पूर्ण पत्ता (ऐच्छिक)") },
                        placeholder = { Text("घर/फ्लॅट क्र., रस्ता, परिसर...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                HorizontalDivider()

                // Actions Footer
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("रद्द करा")
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            if (fullName.isBlank()) {
                                isError = true
                                return@Button
                            }
                            val updated = (classmate ?: ClassmateEntity(fullName = fullName)).copy(
                                fullName = fullName.trim(),
                                nickname = nickname.trim(),
                                stream = stream,
                                occupationCategory = occupationCategory,
                                jobTitle = jobTitle.trim(),
                                organization = organization.trim(),
                                nativeVillage = nativeVillage.trim().ifBlank { "उमरगा" },
                                currentCity = currentCity.trim().ifBlank { "उमरगा" },
                                address = address.trim(),
                                phone = phone.trim(),
                                whatsapp = whatsapp.trim().ifBlank { phone.trim() },
                                email = email.trim(),
                                bloodGroup = bloodGroup,
                                thoughtsAndMemories = thoughtsAndMemories.trim(),
                                favoriteMemory = favoriteMemory.trim(),
                                attendanceStatus = attendanceStatus,
                                avatarColorIndex = classmate?.avatarColorIndex ?: ((fullName.hashCode() and 0x7FFFFFFF) % 8)
                            )
                            viewModel.saveClassmate(updated)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("btn_save_classmate")
                    ) {
                        Text("माहिती जतन करा", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt) },
                    onClick = {
                        onOptionSelected(opt)
                        expanded = false
                    }
                )
            }
        }
    }
}
