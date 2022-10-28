package com.example.weather_app.domain.repository

import com.example.weather_app.network.OpenWeatherMapApi
import com.example.weather_app.network.dto.CurrentWeatherDTO
import com.example.weather_app.network.dto.DailyWeatherDTO
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val openWeatherMapApi: OpenWeatherMapApi
): CurrentWeatherRepository {
    override suspend fun getCurrentWeather(lat: Double, lon: Double, api_key: String): CurrentWeatherDTO {
        return openWeatherMapApi.getWeatherByCoordinates(lat, lon, api_key)
    }

    override suspend fun getCurrentWeatherByCity(q: String, api_key: String): CurrentWeatherDTO {
        return openWeatherMapApi.getWeatherByCity(q, api_key)
    }

    override suspend fun getDailyWeatherByCoordinates(
        lat: Double,
        lon: Double,
        api_key: String
    ): DailyWeatherDTO {
        return openWeatherMapApi.getDailyWeatherByCoordinates(lat, lon, api_key)
    }
}