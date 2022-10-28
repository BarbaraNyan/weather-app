package com.example.weather_app.network

import com.example.weather_app.network.dto.CurrentWeatherDTO
import com.example.weather_app.network.dto.DailyWeatherDTO
import com.example.weather_app.util.URLs
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenWeatherMapApi {

    @GET(URLs.CURRENT_WEATHER_URL)
    suspend fun getWeatherByCoordinates(@Query("lat") lat: Double,
                                        @Query("lon") lon: Double,
                                        @Query("appid") api_key: String = URLs.APP_ID,
                                        @Query("units") units: String = "metric"):
            CurrentWeatherDTO

    @GET(URLs.CURRENT_WEATHER_URL)
    suspend fun getWeatherByCity(@Query("q")q:String,
                                 @Query("appid") api_key: String = URLs.APP_ID,
                                 @Query("units") units: String = "metric"):
            CurrentWeatherDTO

    @GET(URLs.DAILY_FORECAST_URL)
    suspend fun getDailyWeatherByCoordinates(@Query("lat") lat: Double,
                                             @Query("lon") lon: Double,
                                             @Query("appid") api_key: String = URLs.APP_ID,
                                             @Query("units") units: String = "metric"):
            DailyWeatherDTO

}