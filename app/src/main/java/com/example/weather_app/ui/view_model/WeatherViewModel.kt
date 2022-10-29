package com.example.weather_app.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.domain.use_cases.CurrentWeatherUseCase
import com.example.weather_app.domain.use_cases.DailyWeatherUseCase
import com.example.weather_app.util.ResponseState
import com.example.weather_app.ui.state.CurrentWeatherState
import com.example.weather_app.ui.state.DailyWeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val dailyWeatherUseCase: DailyWeatherUseCase
): ViewModel(){
    private val curWeatherValue = MutableStateFlow(CurrentWeatherState())
    var _curWeatherValue: StateFlow<CurrentWeatherState> = curWeatherValue

    //!!!!!!!!!!change
    private val dailyWeatherValue = MutableStateFlow(DailyWeatherState())
    var _dailyWeatherValue: StateFlow<DailyWeatherState> = dailyWeatherValue

    fun getCurrentWeatherByCity(q: String, api_key: String) = viewModelScope.launch(Dispatchers.IO){
        currentWeatherUseCase(q, api_key).collect{
            when(it){
                is ResponseState.Success -> {
                    curWeatherValue.value = CurrentWeatherState(currentWeather = it.data)
                }
                is ResponseState.Loading -> {
                    curWeatherValue.value = CurrentWeatherState(isLoading = true)
                }
                is ResponseState.Error -> {
                    curWeatherValue.value = CurrentWeatherState(error = it.message?:"Unexpected")
                }
            }
        }
    }

    fun getCurrentWeatherByCoordinates(lat: Double, lon: Double, api_key: String) = viewModelScope.launch(Dispatchers.IO){
        currentWeatherUseCase(lat, lon, api_key).collect{
            when(it){
                is ResponseState.Success -> {
                    curWeatherValue.value = CurrentWeatherState(currentWeather = it.data)
                }
                is ResponseState.Loading -> {
                    curWeatherValue.value = CurrentWeatherState(isLoading = true)
                }
                is ResponseState.Error -> {
                    curWeatherValue.value = CurrentWeatherState(error = it.message?:"Unexpected")
                }
            }
        }
    }

    fun getDailyWeatherByCoordinates(lat: Double, lon: Double, api_key: String) = viewModelScope.launch(Dispatchers.IO){
        dailyWeatherUseCase(lat, lon, api_key).collect{
            when(it){
                is ResponseState.Success -> {
                    dailyWeatherValue.value = DailyWeatherState(dailyWeather = it.data?: emptyList())
                }
                is ResponseState.Loading -> {
                    dailyWeatherValue.value = DailyWeatherState(isLoading = true)
                }
                is ResponseState.Error -> {
                    dailyWeatherValue.value = DailyWeatherState(error = it.message?:"Unexpected")
                }
            }
        }
    }

    fun getDailyWeatherByCity(q: String, api_key: String) = viewModelScope.launch(Dispatchers.IO){
        dailyWeatherUseCase(q, api_key).collect{
            when(it){
                is ResponseState.Success -> {
                    dailyWeatherValue.value = DailyWeatherState(dailyWeather = it.data?: emptyList())
                }
                is ResponseState.Loading -> {
                    dailyWeatherValue.value = DailyWeatherState(isLoading = true)
                }
                is ResponseState.Error -> {
                    dailyWeatherValue.value = DailyWeatherState(error = it.message?:"Unexpected")
                }
            }
        }
    }
}