package com.example

import com.example.data.local.ClassmateEntity
import com.example.data.local.InitialDataLoader
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testDataLoaderDefaults() {
        val classmates = InitialDataLoader.getSampleClassmates()
        assertTrue(classmates.isNotEmpty())
        assertEquals(10, classmates.size)

        val events = InitialDataLoader.getSampleEvents()
        assertTrue(events.isNotEmpty())
        assertEquals(9, events.size)

        val committee = InitialDataLoader.getCommitteeMembers()
        assertTrue(committee.isNotEmpty())
        assertEquals(6, committee.size)
    }

    @Test
    fun testClassmateEntityDefaults() {
        val c = ClassmateEntity(
            fullName = "संजय कोरे",
            nativeVillage = "मुरुम",
            currentCity = "उमरगा"
        )
        assertEquals("संजय कोरे", c.fullName)
        assertEquals("मुरुम", c.nativeVillage)
        assertEquals("उमरगा", c.currentCity)
        assertEquals("उपस्थित राहणार", c.attendanceStatus)
    }
}
