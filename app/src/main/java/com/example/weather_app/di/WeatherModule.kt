package com.example.weather_app.di

import com.example.weather_app.network.OpenWeatherMapApi
import com.example.weather_app.domain.repository.WeatherRepository
import com.example.weather_app.domain.repository.WeatherRepositoryImpl
import com.example.weather_app.util.URLs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideOpenWeatherMapApi(): OpenWeatherMapApi {
        return Retrofit.Builder()
            .baseUrl(URLs.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherMapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: OpenWeatherMapApi):WeatherRepository{
        return WeatherRepositoryImpl(api)
    }
}