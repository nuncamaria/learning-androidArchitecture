package com.nuncamaria.learningandroidarchitecture.crypto.data.dto

import com.nuncamaria.learningandroidarchitecture.crypto.domain.CoinPrice
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.ZoneId

/**
 * DTO means Data Transfer Object. It's a class that represents the data that comes from the API.
 */

@Serializable
data class CoinHistoryResponseDto(
    val data: List<CoinPriceDto>
)

@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val time: Long
) {
    fun toCoinPrice() = CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant.ofEpochMilli(time).atZone(ZoneId.of("UTC"))
    )
}
