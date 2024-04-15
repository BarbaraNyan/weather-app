package com.example.weather_app.domain.repository

import com.example.weather_app.network.OpenWeatherMapApi
import com.example.weather_app.network.dto.CurrentWeatherDTO
import com.example.weather_app.network.dto.DailyWeatherDTO
import javax.inject.Inject

/**
 * Реализация репозитория [WeatherRepository]
 * @param openWeatherMapApi апи для получения погоды
 */
class WeatherRepositoryImpl @Inject constructor(
    private val openWeatherMapApi: OpenWeatherMapApi
) : WeatherRepository {
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        api_key: String
    ): CurrentWeatherDTO {
        return openWeatherMapApi.getCurrentWeatherByCoordinates(lat, lon, api_key)
    }

    override suspend fun getCurrentWeatherByCity(q: String, api_key: String): CurrentWeatherDTO {
        return openWeatherMapApi.getCurrentWeatherByCity(q, api_key)
    }

    override suspend fun getDailyWeatherByCoordinates(
        lat: Double,
        lon: Double,
        api_key: String
    ): DailyWeatherDTO {
        return openWeatherMapApi.getDailyWeatherByCoordinates(lat, lon, api_key)
    }

    override suspend fun getDailyWeatherByCity(
        q: String,
        api_key: String
    ): DailyWeatherDTO {
        return openWeatherMapApi.getDailyWeatherByCity(q, api_key)
    }
}