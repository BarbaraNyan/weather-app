package com.example.weather_app.network.dto

/**
 * Класс, описывающий рассвет и закат
 * @param sunrise рассвет
 * @param sunset закат
 */
data class Sys(
    var sunrise: Long,
    var sunset: Long
)
