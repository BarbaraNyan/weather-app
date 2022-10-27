//package com.example.weather_app.domain.use_cases
//
//import com.example.weather_app.domain.model.CurrentWeather
//import com.example.weather_app.domain.repository.CurrentWeatherRepository
//import com.example.weather_app.util.ResponseState
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import retrofit2.HttpException
//import java.io.IOException
//import javax.inject.Inject
//
//class CurrentWeatherByCityUseCase @Inject constructor(
//    private val repository: CurrentWeatherRepository
//) {
//    operator fun invoke(): Flow<ResponseState<CurrentWeather>> = flow {
//        try {
//            emit(ResponseState.Loading<CurrentWeather>())
//            val weather = repository.getCurrentWeatherByCity().toCurrentWeather()
//            emit(ResponseState.Success(weather))
//        } catch (e: HttpException) {
//            emit(ResponseState.Error<CurrentWeather>(e.localizedMessage ?: "An unexpected error occurred"))
//        } catch (e: IOException) {
//            emit(ResponseState.Error<CurrentWeather>("Couldn't reach server. Check your internet connection"))
//        }
//    }
//}