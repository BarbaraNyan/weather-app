package com.example.weather_app.domain.model

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
    val icon: String? = "",
    val dayOfWeek: String? = ""
)
