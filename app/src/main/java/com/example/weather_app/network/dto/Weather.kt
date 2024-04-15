package com.example.weather_app.network.dto

/**
 * Класс, описывающий погоду
 * @param main название
 * @param description описание
 * @param icon иконка
 */
data class Weather(
    var main: String,
    var description: String,
    var icon: String
)
