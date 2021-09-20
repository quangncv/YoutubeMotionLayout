package com.example.youtubemotionlayout.utils

import java.text.SimpleDateFormat
import java.util.*

class DatetimeUtils {
    companion object {
        fun parseLocalTimeToString(timeString: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val date = dateFormat.parse(timeString)
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ROOT)
            return formatter.format(date)
        }
    }
}