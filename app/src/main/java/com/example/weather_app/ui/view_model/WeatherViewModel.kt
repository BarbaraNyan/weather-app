package com.example.weather_app.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.domain.use_cases.CurrentWeatherUseCase
import com.example.weather_app.domain.use_cases.DailyWeatherUseCase
import com.example.weather_app.ui.state.CurrentWeatherState
import com.example.weather_app.ui.state.DailyWeatherState
import com.example.weather_app.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Вью-модель для погоды
 * @param currentWeatherUseCase
 * @param dailyWeatherUseCase
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val dailyWeatherUseCase: DailyWeatherUseCase
) : ViewModel() {
    private val _curWeatherValue = MutableStateFlow(CurrentWeatherState())
    var curWeatherValue: StateFlow<CurrentWeatherState> = _curWeatherValue

    private val _dailyWeatherValue = MutableStateFlow(DailyWeatherState())
    var dailyWeatherValue: StateFlow<DailyWeatherState> = _dailyWeatherValue

    fun getCurrentWeatherByCity(q: String, api_key: String) =
        viewModelScope.launch(Dispatchers.IO) {
            currentWeatherUseCase(q, api_key).collect {
                when (it) {
                    is ResponseState.Success -> {
                        CurrentWeatherState(currentWeather = it.data)
                        _curWeatherValue.value = CurrentWeatherState(currentWeather = it.data)
                    }
                    is ResponseState.Loading -> {
                        _curWeatherValue.value = CurrentWeatherState(isLoading = true)
                    }
                    is ResponseState.Error -> {
                        _curWeatherValue.value =
                            CurrentWeatherState(error = it.message ?: "Unexpected")
                    }
                }
            }
        }

    fun getCurrentWeatherByCoordinates(lat: Double, lon: Double, api_key: String) =
        viewModelScope.launch(Dispatchers.IO) {
            currentWeatherUseCase(lat, lon, api_key).collect {
                when (it) {
                    is ResponseState.Success -> {
                        _curWeatherValue.value = CurrentWeatherState(currentWeather = it.data)
                    }
                    is ResponseState.Loading -> {
                        _curWeatherValue.value = CurrentWeatherState(isLoading = true)
                    }
                    is ResponseState.Error -> {
                        _curWeatherValue.value =
                            CurrentWeatherState(error = it.message ?: "Unexpected")
                    }
                }
            }
        }

    fun getDailyWeatherByCoordinates(lat: Double, lon: Double, api_key: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dailyWeatherUseCase(lat, lon, api_key).collect {
                when (it) {
                    is ResponseState.Success -> {
                        _dailyWeatherValue.value =
                            DailyWeatherState(dailyWeather = it.data ?: emptyList())
                    }
                    is ResponseState.Loading -> {
                        _dailyWeatherValue.value = DailyWeatherState(isLoading = true)
                    }
                    is ResponseState.Error -> {
                        _dailyWeatherValue.value =
                            DailyWeatherState(error = it.message ?: "Unexpected")
                    }
                }
            }
        }

    fun getDailyWeatherByCity(q: String, api_key: String) = viewModelScope.launch(Dispatchers.IO) {
        dailyWeatherUseCase(q, api_key).collect {
            when (it) {
                is ResponseState.Success -> {
                    _dailyWeatherValue.value =
                        DailyWeatherState(dailyWeather = it.data ?: emptyList())
                }
                is ResponseState.Loading -> {
                    _dailyWeatherValue.value = DailyWeatherState(isLoading = true)
                }
                is ResponseState.Error -> {
                    _dailyWeatherValue.value = DailyWeatherState(error = it.message ?: "Unexpected")
                }
            }
        }
    }
}