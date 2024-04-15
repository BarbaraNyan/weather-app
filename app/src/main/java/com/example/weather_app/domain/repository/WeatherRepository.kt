package com.example.weather_app.domain.repository

import com.example.weather_app.network.dto.CurrentWeatherDTO
import com.example.weather_app.network.dto.DailyWeatherDTO

/**
 * Репозиторий для получения погоды
 */
interface WeatherRepository {
    /**
     * Получить текущую погоду, используя координаты [lat] и [lon]
     */
    suspend fun getCurrentWeather(lat: Double, lon: Double, api_key: String): CurrentWeatherDTO

    /**
     * Получить текущую погоду, используя запрос [q]
     */
    suspend fun getCurrentWeatherByCity(q: String, api_key: String): CurrentWeatherDTO

    /**
     * Получить недельную погоду, используя координаты [lat] и [lon]
     */
    suspend fun getDailyWeatherByCoordinates(
        lat: Double,
        lon: Double,
        api_key: String
    ): DailyWeatherDTO

    /**
     * Получить недельную погоду, используя запрос [q]
     */
    suspend fun getDailyWeatherByCity(q: String, api_key: String): DailyWeatherDTO
}