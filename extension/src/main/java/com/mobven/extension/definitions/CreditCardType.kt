package com.mobven.extension.definitions

import androidx.annotation.StringDef
import com.mobven.extension.definitions.CreditCardType.Companion.AMEX
import com.mobven.extension.definitions.CreditCardType.Companion.DEFAULT
import com.mobven.extension.definitions.CreditCardType.Companion.MAESTRO
import com.mobven.extension.definitions.CreditCardType.Companion.MASTERCARD
import com.mobven.extension.definitions.CreditCardType.Companion.TROY
import com.mobven.extension.definitions.CreditCardType.Companion.VISA

@StringDef(VISA, MASTERCARD, MAESTRO, TROY, AMEX, DEFAULT)
annotation class CreditCardType {
    companion object {
        const val VISA = "^4[0-9]{6,}\$"
        const val MASTERCARD = "^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}\$"
        const val MAESTRO = "^(?:5[0678]\\\\d\\\\d|6304|6390|67\\\\d\\\\d)\\\\d{8,15}\$"
        const val TROY = "^(?:9792|65\\d{2}|36|2205)\\d{12}\$"
        const val AMEX = "^3[47][0-9]{5,}\$"
        const val DEFAULT = "default"
    }
}
