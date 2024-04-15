package com.example.weather_app.network.dto

/**
 * Класс, описывающий основные характеристики погоды
 * @param temperature температура
 * @param feelsLike ощущается как
 * @param tempMin минимальная температура
 * @param tempMax максимальная температура
 * @param pressure давление
 * @param humidity туман
 */
data class MainCharacteristics(
    var temperature: Double,
    var feelsLike: Double,
    var tempMin: Double,
    var tempMax: Double,
    var pressure: Int,
    var humidity: Int
)
