package com.mobven.extension

import org.junit.Assert
import org.junit.Test

class StringUnitTest {

    @Test
    fun tcknTests() {
        Assert.assertFalse(null.isValidTCKN())
        Assert.assertFalse("".isValidTCKN())
        Assert.assertFalse("234".isValidTCKN())
        Assert.assertFalse("00719505186".isValidTCKN())
        Assert.assertTrue("20519505186".isValidTCKN())
    }
}