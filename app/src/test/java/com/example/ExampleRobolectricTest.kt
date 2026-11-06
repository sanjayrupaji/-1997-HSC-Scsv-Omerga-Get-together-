package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.local.ClassmateEntity
import com.example.data.local.InitialDataLoader
import com.example.data.repository.ReunionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var db: AppDatabase
    private lateinit var repository: ReunionRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = ReunionRepository(db)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testAppNameStringResource() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("मित्रमेळावा १९९७", appName)
    }

    @Test
    fun testDefaultDataLoaderHasEntries() {
        val classmates = InitialDataLoader.getSampleClassmates()
        val events = InitialDataLoader.getSampleEvents()
        assertTrue("Classmates should not be empty", classmates.isNotEmpty())
        assertTrue("Events should not be empty", events.isNotEmpty())
        assertTrue("Classmates should have thoughts and memories", classmates.all { it.thoughtsAndMemories.isNotBlank() })
    }

    @Test
    fun testInsertAndRetrieveClassmate() = runBlocking {
        val friend = ClassmateEntity(
            fullName = "आनंद कुलकर्णी",
            nickname = "आनंद्या",
            stream = "विज्ञान (Science)",
            occupationCategory = "वैद्यकीय / डॉक्टर",
            jobTitle = "डॉक्टर",
            organization = "ग्रामीण रुग्णालय, उमरगा",
            nativeVillage = "गुंजोटी",
            currentCity = "उमरगा",
            phone = "9876543210",
            whatsapp = "9876543210",
            thoughtsAndMemories = "अविस्मरणीय कॉलेजचे दिवस!",
            attendanceStatus = "उपस्थित राहणार"
        )
        val id = repository.insertClassmate(friend)
        assertTrue(id > 0)

        val retrieved = repository.getClassmateById(id).first()
        assertNotNull(retrieved)
        assertEquals("आनंद कुलकर्णी", retrieved?.fullName)
        assertEquals("गुंजोटी", retrieved?.nativeVillage)
        assertEquals("अविस्मरणीय कॉलेजचे दिवस!", retrieved?.thoughtsAndMemories)
    }

    @Test
    fun testExportToCsv() = runBlocking {
        val friend = ClassmateEntity(
            fullName = "सुरेश जाधव",
            nativeVillage = "मुरुम",
            currentCity = "पुणे",
            phone = "9123456789",
            jobTitle = "उद्योजक",
            thoughtsAndMemories = "सस्नेह आठवणी"
        )
        val csv = repository.exportToCsv(listOf(friend))
        assertTrue(csv.contains("सुरेश जाधव"))
        assertTrue(csv.contains("मुरुम"))
        assertTrue(csv.contains("सस्नेह आठवणी"))
    }
}
