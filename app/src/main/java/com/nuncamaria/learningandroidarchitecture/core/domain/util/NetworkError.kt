package com.nuncamaria.learningandroidarchitecture.core.domain.util

import com.nuncamaria.learningandroidarchitecture.R

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET_CONNECTION,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN
}

fun NetworkError.getStringRes(): Int {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.too_many_requests
        NetworkError.NO_INTERNET_CONNECTION -> R.string.no_internet_connection
        NetworkError.SERVER_ERROR -> R.string.server_error
        NetworkError.SERIALIZATION -> R.string.serialization
        NetworkError.UNKNOWN -> R.string.unknown
    }

    return resId
}