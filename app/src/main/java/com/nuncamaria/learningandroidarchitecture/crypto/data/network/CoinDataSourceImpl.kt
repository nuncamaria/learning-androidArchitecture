package com.nuncamaria.learningandroidarchitecture.crypto.data.network

import com.nuncamaria.learningandroidarchitecture.core.data.network.buildUrl
import com.nuncamaria.learningandroidarchitecture.core.data.network.safeCall
import com.nuncamaria.learningandroidarchitecture.core.domain.util.NetworkError
import com.nuncamaria.learningandroidarchitecture.core.domain.util.Result
import com.nuncamaria.learningandroidarchitecture.core.domain.util.map
import com.nuncamaria.learningandroidarchitecture.crypto.data.dto.CoinHistoryResponseDto
import com.nuncamaria.learningandroidarchitecture.crypto.data.dto.CoinResponseDto
import com.nuncamaria.learningandroidarchitecture.crypto.domain.Coin
import com.nuncamaria.learningandroidarchitecture.crypto.domain.CoinPrice
import com.nuncamaria.learningandroidarchitecture.crypto.domain.datasource.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class CoinDataSourceImpl(private val httpClient: HttpClient) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> =
        safeCall<CoinResponseDto> {
            httpClient.get(urlString = buildUrl("/assets"))
        }.map { response ->
            response.data.map { it.toCoin() }
        }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endMillis = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()

        return safeCall<CoinHistoryResponseDto> {
            httpClient.get(urlString = buildUrl("/assets/$coinId/history")) {
                parameter("interval", "h6") //6 hours interval
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}