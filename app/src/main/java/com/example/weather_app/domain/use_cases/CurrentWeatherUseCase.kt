package com.example.weather_app.domain.use_cases

import com.example.weather_app.domain.model.CurrentWeather
import com.example.weather_app.domain.repository.CurrentWeatherRepository
import com.example.weather_app.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CurrentWeatherUseCase @Inject constructor(
    private val repository: CurrentWeatherRepository
) {
    operator fun invoke(q: String, api_key: String): Flow<ResponseState<CurrentWeather>> = flow {
        try {
            emit(ResponseState.Loading<CurrentWeather>())
            val weather = repository.getCurrentWeatherByCity(q, api_key).toCurrentWeather()
            emit(ResponseState.Success(weather))
        } catch (e: HttpException) {
            emit(ResponseState.Error<CurrentWeather>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ResponseState.Error<CurrentWeather>("Couldn't reach server. Check your internet connection"))
        }
    }


    operator fun invoke(lat: Double, lon: Double, api_key: String): Flow<ResponseState<CurrentWeather>> = flow {
        try {
            emit(ResponseState.Loading<CurrentWeather>())
            val weather = repository.getCurrentWeather(lat, lon, api_key).toCurrentWeather()
            emit(ResponseState.Success(weather))
        } catch (e: HttpException) {
            emit(ResponseState.Error<CurrentWeather>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ResponseState.Error<CurrentWeather>("Couldn't reach server. Check your internet connection"))
        }
    }
}