package com.nuncamaria.learningandroidarchitecture.crypto.data.dto

import com.nuncamaria.learningandroidarchitecture.crypto.domain.Coin
import kotlinx.serialization.Serializable

/**
 * DTO means Data Transfer Object. It's a class that represents the data that comes from the API.
 */

@Serializable
data class CoinResponseDto(
    val data: List<CoinDto>
)

@Serializable
data class CoinDto(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double,
) {
    fun toCoin() = Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}
