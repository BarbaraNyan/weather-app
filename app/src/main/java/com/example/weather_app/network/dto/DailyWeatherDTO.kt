package com.example.weather_app.network.dto

import com.example.weather_app.domain.model.CurrentWeather
import com.example.weather_app.util.TimeFormatter

class DailyWeatherDTO(
    val list: List<CurrentWeatherDTO>
){
    fun toDailyWeatherList(): ArrayList<CurrentWeather> {
        val dailyWeatherList = ArrayList<CurrentWeather>()
        for (item in list){
            dailyWeatherList.add(
                CurrentWeather(temp = item.main.temp,
                    feels_like = item.main.feels_like,
                    weather_descr = item.weather[0].description,
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