package com.example.weather_app.domain.use_cases

import com.example.weather_app.domain.model.CurrentWeather
import com.example.weather_app.domain.repository.WeatherRepository
import com.example.weather_app.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use-case для получения текущей погоды
 * @param repository репозиторий погоды
 */
class CurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(q: String, api_key: String): Flow<ResponseState<CurrentWeather>> = flow {
        try {
            emit(ResponseState.Loading())
            val weather = repository.getCurrentWeatherByCity(q, api_key).toCurrentWeather()
            emit(ResponseState.Success(weather))
        } catch (e: HttpException) {
            emit(
                ResponseState.Error(
                    e.localizedMessage ?: UNEXPECTED_ERROR
                )
            )
        } catch (e: IOException) {
            emit(ResponseState.Error(CHECK_INTERNET_CONNECTION))
        }
    }

    operator fun invoke(
        lat: Double,
        lon: Double,
        api_key: String
    ): Flow<ResponseState<CurrentWeather>> = flow {
        try {
            emit(ResponseState.Loading())
            val weather = repository.getCurrentWeather(lat, lon, api_key).toCurrentWeather()
            emit(ResponseState.Success(weather))
        } catch (e: HttpException) {
            emit(
                ResponseState.Error(
                    e.localizedMessage ?: UNEXPECTED_ERROR
                )
            )
        } catch (e: IOException) {
            emit(ResponseState.Error(CHECK_INTERNET_CONNECTION))
        }
    }

    private companion object {
        const val UNEXPECTED_ERROR = "An unexpected error occurred"
        const val CHECK_INTERNET_CONNECTION =
            "Couldn't reach server. Check your internet connection"
    }
}