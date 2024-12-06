package com.nuncamaria.learningandroidarchitecture.core.data.network

import com.nuncamaria.learningandroidarchitecture.BuildConfig

fun buildUrl(url: String): String =
    when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }
