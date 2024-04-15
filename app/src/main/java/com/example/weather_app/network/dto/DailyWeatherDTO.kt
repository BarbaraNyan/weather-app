package com.example.weather_app.network.dto

import com.example.weather_app.domain.model.CurrentWeather
import com.example.weather_app.util.TimeFormatter

/**
 * DTO для описания погоды по дням
 * @param list список с текущей погодой
 */
class DailyWeatherDTO(
    private val list: List<CurrentWeatherDTO>
) {
    fun toDailyWeatherList(): List<CurrentWeather> {
        val dailyWeatherList = mutableListOf<CurrentWeather>()
        list.forEach { item ->
            dailyWeatherList.add(
                CurrentWeather(
                    temperature = item.main.temperature,
                    feelsLike = item.main.feelsLike,
                    description = item.weather[0].description,
                    wind = item.wind.speed,
                    humidity = item.main.humidity,
                    pressure = item.main.pressure,
                    dt_txt = TimeFormatter.toHour(item.dt),
                    icon = item.weather[0].icon,
                    dayOfWeek = TimeFormatter.toDayOfWeek(item.dt)))
        }
        return dailyWeatherList
    }
}