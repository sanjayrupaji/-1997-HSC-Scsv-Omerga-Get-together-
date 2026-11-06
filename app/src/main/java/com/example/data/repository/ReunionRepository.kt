package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.ClassmateEntity
import com.example.data.local.EventEntity
import com.example.data.local.InitialDataLoader
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject

class ReunionRepository(private val database: AppDatabase) {

    private val classmateDao = database.classmateDao()
    private val eventDao = database.eventDao()

    val allClassmates: Flow<List<ClassmateEntity>> = classmateDao.getAllClassmates()
    val allEvents: Flow<List<EventEntity>> = eventDao.getAllEvents()
    val classmateCount: Flow<Int> = classmateDao.getClassmateCount()

    fun searchClassmates(query: String): Flow<List<ClassmateEntity>> {
        return if (query.isBlank()) {
            classmateDao.getAllClassmates()
        } else {
            classmateDao.searchClassmates(query.trim())
        }
    }

    fun getClassmateById(id: Long): Flow<ClassmateEntity?> {
        return classmateDao.getClassmateById(id)
    }

    suspend fun insertClassmate(classmate: ClassmateEntity): Long {
        return classmateDao.insertClassmate(classmate)
    }

    suspend fun updateClassmate(classmate: ClassmateEntity) {
        classmateDao.updateClassmate(classmate)
    }

    suspend fun deleteClassmate(classmate: ClassmateEntity) {
        classmateDao.deleteClassmate(classmate)
    }

    suspend fun insertEvent(event: EventEntity): Long {
        return eventDao.insertEvent(event)
    }

    suspend fun updateEvent(event: EventEntity) {
        eventDao.updateEvent(event)
    }

    suspend fun deleteEvent(event: EventEntity) {
        eventDao.deleteEvent(event)
    }

    suspend fun initializeDefaultDataIfNeeded() {
        if (classmateDao.getClassmateCountSync() == 0) {
            classmateDao.insertAll(InitialDataLoader.getSampleClassmates())
        }
        if (eventDao.getEventCount() == 0) {
            eventDao.insertAll(InitialDataLoader.getSampleEvents())
        }
    }

    suspend fun resetToSampleData() {
        classmateDao.deleteAll()
        classmateDao.insertAll(InitialDataLoader.getSampleClassmates())
        // Keep events or re-seed
        if (eventDao.getEventCount() == 0) {
            eventDao.insertAll(InitialDataLoader.getSampleEvents())
        }
    }

    // Export helpers
    fun exportToCsv(classmates: List<ClassmateEntity>): String {
        val sb = StringBuilder()
        sb.append("नाव,टोपणनाव,शाखा,व्यवसाय_प्रवर्ग,काम_हुद्दा,कंपनी_संस्था,मूळ_गाव,सध्याचे_शहर,पत्ता,मोबाईल,व्हॉट्सॲप,ईमेल,रक्तगट,हजेरी,आठवणी\n")
        classmates.forEach { c ->
            sb.append("\"${c.fullName.replace("\"", "\"\"")}\",")
            sb.append("\"${c.nickname.replace("\"", "\"\"")}\",")
            sb.append("\"${c.stream.replace("\"", "\"\"")}\",")
            sb.append("\"${c.occupationCategory.replace("\"", "\"\"")}\",")
            sb.append("\"${c.jobTitle.replace("\"", "\"\"")}\",")
            sb.append("\"${c.organization.replace("\"", "\"\"")}\",")
            sb.append("\"${c.nativeVillage.replace("\"", "\"\"")}\",")
            sb.append("\"${c.currentCity.replace("\"", "\"\"")}\",")
            sb.append("\"${c.address.replace("\"", "\"\"")}\",")
            sb.append("\"${c.phone.replace("\"", "\"\"")}\",")
            sb.append("\"${c.whatsapp.replace("\"", "\"\"")}\",")
            sb.append("\"${c.email.replace("\"", "\"\"")}\",")
            sb.append("\"${c.bloodGroup.replace("\"", "\"\"")}\",")
            sb.append("\"${c.attendanceStatus.replace("\"", "\"\"")}\",")
            sb.append("\"${c.thoughtsAndMemories.replace("\"", "\"\"")}\"\n")
        }
        return sb.toString()
    }

    // Import from Google Form CSV line or JSON
    suspend fun importClassmates(rawText: String): Result<Int> {
        return try {
            val trimmed = rawText.trim()
            val list = mutableListOf<ClassmateEntity>()
            if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
                val jsonArr = JSONArray(trimmed)
                for (i in 0 until jsonArr.length()) {
                    val obj = jsonArr.getJSONObject(i)
                    list.add(
                        ClassmateEntity(
                            fullName = obj.optString("fullName", obj.optString("name", "मित्र")),
                            nickname = obj.optString("nickname", ""),
                            stream = obj.optString("stream", "विज्ञान (Science)"),
                            occupationCategory = obj.optString("occupationCategory", "इतर"),
                            jobTitle = obj.optString("jobTitle", ""),
                            organization = obj.optString("organization", ""),
                            nativeVillage = obj.optString("nativeVillage", "उमरगा"),
                            currentCity = obj.optString("currentCity", "उमरगा"),
                            address = obj.optString("address", ""),
                            phone = obj.optString("phone", ""),
                            whatsapp = obj.optString("whatsapp", obj.optString("phone", "")),
                            email = obj.optString("email", ""),
                            bloodGroup = obj.optString("bloodGroup", "माहित नाही"),
                            thoughtsAndMemories = obj.optString("thoughtsAndMemories", obj.optString("memories", "")),
                            favoriteMemory = obj.optString("favoriteMemory", ""),
                            attendanceStatus = obj.optString("attendanceStatus", "उपस्थित राहणार"),
                            avatarColorIndex = (list.size % 8)
                        )
                    )
                }
            } else {
                // Parse CSV rows
                val lines = trimmed.lines().filter { it.isNotBlank() }
                val rows = if (lines.firstOrNull()?.contains("नाव") == true) lines.drop(1) else lines
                for ((idx, line) in rows.withIndex()) {
                    val parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)".toRegex())
                        .map { it.trim().removeSurrounding("\"").trim() }
                    if (parts.isNotEmpty() && parts[0].isNotBlank()) {
                        val name = parts[0]
                        val nickname = parts.getOrElse(1) { "" }
                        val stream = parts.getOrElse(2) { "विज्ञान (Science)" }
                        val cat = parts.getOrElse(3) { "इतर" }
                        val job = parts.getOrElse(4) { "" }
                        val org = parts.getOrElse(5) { "" }
                        val village = parts.getOrElse(6) { "उमरगा" }
                        val city = parts.getOrElse(7) { "उमरगा" }
                        val addr = parts.getOrElse(8) { "" }
                        val phone = parts.getOrElse(9) { "" }
                        val whatsapp = parts.getOrElse(10) { phone }
                        val email = parts.getOrElse(11) { "" }
                        val blood = parts.getOrElse(12) { "माहित नाही" }
                        val rsvp = parts.getOrElse(13) { "उपस्थित राहणार" }
                        val thoughts = parts.getOrElse(14) { "" }

                        list.add(
                            ClassmateEntity(
                                fullName = name,
                                nickname = nickname,
                                stream = stream,
                                occupationCategory = cat,
                                jobTitle = job,
                                organization = org,
                                nativeVillage = village,
                                currentCity = city,
                                address = addr,
                                phone = phone,
                                whatsapp = whatsapp,
                                email = email,
                                bloodGroup = blood,
                                attendanceStatus = rsvp,
                                thoughtsAndMemories = thoughts,
                                avatarColorIndex = (idx % 8)
                            )
                        )
                    }
                }
            }

            if (list.isNotEmpty()) {
                classmateDao.insertAll(list)
                Result.success(list.size)
            } else {
                Result.failure(Exception("कोणतीही वैध माहिती आढळली नाही"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
