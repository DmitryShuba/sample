package com.dmitryshuba.sample

import com.dmitryshuba.sample.service.database.model.SectionEntity
import com.dmitryshuba.sample.service.database.util.SectionPageConverter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class ConverterUnitTest {
    @Spy
    private val SUT = Mockito.spy(SectionPageConverter::class.java)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun entityListToStringTest() {
        val expectedResult = "[{\"href\":\"https://testwebsite\",\"title\":\"test\"}]"
        val testList = listOf(SectionEntity("https://testwebsite", "test"))
        val actualResult = SUT.entityListToString(testList)
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun sectionListStringToEntityListTest() {
        val expectedResult = listOf(SectionEntity("https://testwebsite", "test"))
        val testJson = "[{\"href\":\"https://testwebsite\",\"title\":\"test\"}]"
        val actualResult = SUT.sectionListStringToEntityList(testJson)
        assertEquals(expectedResult, actualResult)
    }
}
