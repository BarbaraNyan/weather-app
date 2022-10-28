package com.example.weather_app.domain.repository

import com.example.weather_app.network.dto.CurrentWeatherDTO
import com.example.weather_app.network.dto.DailyWeatherDTO

interface CurrentWeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double, api_key: String): CurrentWeatherDTO

    suspend fun getCurrentWeatherByCity(q: String, api_key: String): CurrentWeatherDTO

    suspend fun getDailyWeatherByCoordinates(lat: Double, lon: Double, api_key: String): DailyWeatherDTO
}