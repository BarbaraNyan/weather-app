package com.example.weather_app.network.dto

data class main(
    var temp: Double,
    var feels_like: Double,
    var temp_min: Double,
    var temp_max: Double,
    var pressure: Int,
    var humidity: Int
)
