package com.example.weather_app.network.dto

import com.example.weather_app.domain.model.CurrentWeather
import com.example.weather_app.util.TimeFormatter

/**
 * DTO текущей погоды
 * @param weather погода
 * @param main общие характеристики погоды
 * @param wind скорость ветра
 * @param sys описание рассвета и заката
 */
class CurrentWeatherDTO(
    val dt: Long,
    val weather: List<Weather>,
    val main: MainCharacteristics,
    val wind: Wind,
    val sys: Sys,
) {
    fun toCurrentWeather(): CurrentWeather {
        return CurrentWeather(
            temperature = main.temperature,
            feelsLike = main.feelsLike,
            description = weather[0].description,
            wind = wind.speed,
            humidity = main.humidity,
            pressure = main.pressure,
            sunset = TimeFormatter.toHour(sys.sunset),
            sunrise = TimeFormatter.toHour(sys.sunrise),
            icon = weather[0].icon)
    }
}
