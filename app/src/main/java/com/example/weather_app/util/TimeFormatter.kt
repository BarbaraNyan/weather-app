package com.example.weather_app.util

import android.icu.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object TimeFormatter {
    private val simpleDateFormatHour = SimpleDateFormat("HH:mm")
    private val simpleDateFormatDayOfWeek = SimpleDateFormat("EEEE", Locale.ENGLISH)

    fun toHour(time: Long): String = simpleDateFormatHour.format(time * 1000L)
    fun toDayOfWeek(time: Long): String = simpleDateFormatDayOfWeek.format(time * 1000L)
}