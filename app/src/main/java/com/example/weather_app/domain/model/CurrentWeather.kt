package com.example.weather_app.domain.model

/**
 * Класс, описывающий текущую погоду
 *
 * @property temperature температура
 * @property feelsLike температура как ощущается
 * @property description описание погоды
 * @property wind скорость ветра
 * @property humidity туман
 * @property pressure давление
 * @property sunrise восход
 * @property sunset закат
 * @property dt_txt
 * @property icon иконка
 * @property dayOfWeek день недели
 */
data class CurrentWeather(
    val temperature: Double,
    val feelsLike: Double,
    val description: String,
    val wind: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: String? = "",
    val sunset: String? = "",
    val dt_txt: String? = "",
    val icon: String? = "",
    val dayOfWeek: String? = ""
)
