package com.example.weather_app.domain.model

import java.time.ZonedDateTime

data class CurrentWeather(
    val temp: Double,
    val feels_like: Double,
    val weather_descr: String,
    val wind: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: String? = "",
    val sunset: String? = "",
    val dt_txt: String? = "",
    val icon: String? = ""
//    val sunrise: ZonedDateTime,
//    val sunset: ZonedDateTime
)
