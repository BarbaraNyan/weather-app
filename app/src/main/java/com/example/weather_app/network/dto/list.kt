package com.example.weather_app.network.dto

data class list (
    var main: main,
    var weather: weather,
    var wind: wind,
    var dt_txt: String
)