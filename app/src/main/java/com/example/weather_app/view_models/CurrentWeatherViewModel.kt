package com.example.weather_app.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.domain.use_cases.CurrentWeatherUseCase
import com.example.weather_app.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase
): ViewModel(){
    private val curWeatherValue = MutableStateFlow(CurrentWeatherState())
    var _curWeatherValue: StateFlow<CurrentWeatherState> = curWeatherValue

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
}