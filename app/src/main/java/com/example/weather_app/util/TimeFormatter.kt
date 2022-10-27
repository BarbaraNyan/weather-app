package com.example.weather_app.util

import android.icu.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TimeFormatter {
    private val dayHourFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val simpleDateFormat = SimpleDateFormat("HH:mm")

    fun toDayHour(time: ZonedDateTime): String = dayHourFormatter.format(time)
    fun toString(time: Long): String = simpleDateFormat.format(time * 1000L)
}