package com.example.weather_app.ui.state

import com.example.weather_app.domain.model.CurrentWeather

data class DailyWeatherState(
    val isLoading: Boolean = false,
    val dailyWeather: List<CurrentWeather> = emptyList(),
    val error: String = ""
)