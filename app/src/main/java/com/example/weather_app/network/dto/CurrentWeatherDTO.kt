package com.example.weather_app.network.dto

import com.example.weather_app.domain.model.CurrentWeather
import com.example.weather_app.util.TimeFormatter

class CurrentWeatherDTO(
//    val id: Int,
    val dt: Long,
    val weather: List<weather>,
    val main: main,
    val wind: wind,
    val sys: sys,
    val dt_txt: String
){
    fun toCurrentWeather(): CurrentWeather {
        return CurrentWeather(temp = main.temp,
            feels_like = main.feels_like,
            weather_descr = weather[0].description,
            wind = wind.speed,
            humidity = main.humidity,
            pressure = main.pressure,
            sunset = TimeFormatter.toHour(sys.sunset),
            sunrise = TimeFormatter.toHour(sys.sunrise),
            icon = weather[0].icon)
    }
}
