package com.example.weather_app.util

/**
 * Ответ от апи
 * @param data ответ
 * @param message сообщение ошибки
 */
sealed class ResponseState<T>(val data: T? = null, val message: String? = null) {
    /**
     * Загрузка
     */
    class Loading<T>(data: T? = null) : ResponseState<T>(data)

    /**
     * Успех
     */
    class Success<T>(data: T) : ResponseState<T>(data)

    /**
     * Ошибка
     */
    class Error<T>(message: String, data: T? = null) : ResponseState<T>(data, message)
}