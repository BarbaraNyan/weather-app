package com.example.weather_app.view_models

import com.example.weather_app.domain.model.CurrentWeather

data class CurrentWeatherState (
    val isLoading: Boolean = false,
    val currentWeather: CurrentWeather? = null,
    val error: String = ""
)