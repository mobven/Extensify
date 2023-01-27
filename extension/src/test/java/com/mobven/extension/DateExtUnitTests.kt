package com.mobven.extension

import org.junit.Test

import org.junit.Assert
import java.util.Calendar
import java.util.Date

class DateExtUnitTests {

    @Test
    fun addTest() {
        // given
        val cal = Calendar.getInstance()
        cal.set(2022, 1, 31)
        val date: Date = cal.time

        // when
        val newDate = date.add(Calendar.YEAR, 1)
        newDate.add(Calendar.MONTH, 3)
        newDate.add(Calendar.DAY_OF_YEAR, 3)

        // then
        Assert.assertEquals(newDate.formatToViewTime("yyyy"), "2023")
        Assert.assertEquals(newDate.formatToViewTime("MM"), "03")
        Assert.assertEquals(newDate.formatToViewTime("dd"), "03")
    }
}