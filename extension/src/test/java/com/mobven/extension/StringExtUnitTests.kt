package com.mobven.extension

import org.junit.Assert
import org.junit.Test

class StringExtUnitTests {

    @Test
    fun tcknTests() {
        Assert.assertFalse(null.isValidTCKN())
        Assert.assertFalse("".isValidTCKN())
        Assert.assertFalse("234".isValidTCKN())
        Assert.assertFalse("00719505186".isValidTCKN())
        Assert.assertTrue("20519505186".isValidTCKN())
    }

    @Test
    fun toPriceAmountTests() {
        Assert.assertNull(null.toPriceAmount())
        Assert.assertEquals("".toPriceAmount(), "")
        Assert.assertEquals("asdf".toPriceAmount(), "asdf")
        Assert.assertEquals("0.12".toPriceAmount(), "0.12")
        Assert.assertEquals("0.1289".toPriceAmount(), "0.13")
        Assert.assertEquals("0.1213".toPriceAmount(), "0.12")
        Assert.assertEquals("0.1f213".toPriceAmount(), "0.1f213")
        Assert.assertEquals("1234567.1213".toPriceAmount(), "1,234,567.12")
        Assert.assertEquals("    123456.1213         ".toPriceAmount(), "123,456.12")
    }
}