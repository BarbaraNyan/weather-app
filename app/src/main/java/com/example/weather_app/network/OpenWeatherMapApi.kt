package com.example.weather_app.network

import com.example.weather_app.network.dto.CurrentWeatherDTO
import com.example.weather_app.network.dto.DailyWeatherDTO
import com.example.weather_app.util.URLs
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Апи для получения данных о погоде
 */
interface OpenWeatherMapApi {

    @GET(URLs.CURRENT_WEATHER_URL)
    suspend fun getCurrentWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") api_key: String = URLs.APP_ID,
        @Query("units") units: String = "metric"
    ): CurrentWeatherDTO

    @GET(URLs.CURRENT_WEATHER_URL)
    suspend fun getCurrentWeatherByCity(
        @Query("q") q: String,
        @Query("appid") api_key: String = URLs.APP_ID,
        @Query("units") units: String = "metric"
    ): CurrentWeatherDTO

    @GET(URLs.DAILY_FORECAST_URL)
    suspend fun getDailyWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") api_key: String = URLs.APP_ID,
        @Query("units") units: String = "metric"
    ): DailyWeatherDTO

    @GET(URLs.DAILY_FORECAST_URL)
    suspend fun getDailyWeatherByCity(
        @Query("q") q: String,
        @Query("appid") api_key: String = URLs.APP_ID,
        @Query("units") units: String = "metric"
    ): DailyWeatherDTO
}