package com.example.weather_app.domain.use_cases

import com.example.weather_app.domain.model.CurrentWeather
import com.example.weather_app.domain.repository.WeatherRepository
import com.example.weather_app.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DailyWeatherUseCase@Inject constructor(
    private val repository: WeatherRepository
)  {
    operator fun invoke(q: String, api_key: String): Flow<ResponseState<List<CurrentWeather>>> = flow {
        try {
            emit(ResponseState.Loading())
            val weather = repository.getDailyWeatherByCity(q, api_key).toDailyWeatherList()
            emit(ResponseState.Success(weather))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ResponseState.Error("Couldn't reach server. Check your internet connection"))
        }
    }

    operator fun invoke(lat: Double, lon: Double, api_key: String): Flow<ResponseState<List<CurrentWeather>>> = flow {
        try {
            emit(ResponseState.Loading())
            val weather = repository.getDailyWeatherByCoordinates(lat, lon, api_key).toDailyWeatherList()
            emit(ResponseState.Success(weather))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ResponseState.Error("Couldn't reach server. Check your internet connection"))
        }
    }
}