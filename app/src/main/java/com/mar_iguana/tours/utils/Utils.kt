package com.mar_iguana.tours.utils

import java.text.NumberFormat
import java.util.*

class Utils {

    companion object {

        fun localCurrencyFormat(number: Int) : String {
            val format: NumberFormat = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("MXN")
            return format.format(number)
        }
    }
}