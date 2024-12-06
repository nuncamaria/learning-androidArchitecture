package com.nuncamaria.learningandroidarchitecture.crypto.data.network

import com.nuncamaria.learningandroidarchitecture.core.data.network.buildUrl
import com.nuncamaria.learningandroidarchitecture.core.data.network.safeCall
import com.nuncamaria.learningandroidarchitecture.core.domain.util.NetworkError
import com.nuncamaria.learningandroidarchitecture.core.domain.util.Result
import com.nuncamaria.learningandroidarchitecture.core.domain.util.map
import com.nuncamaria.learningandroidarchitecture.crypto.data.dto.CoinResponseDto
import com.nuncamaria.learningandroidarchitecture.crypto.domain.Coin
import com.nuncamaria.learningandroidarchitecture.crypto.domain.datasource.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class CoinDataSourceImpl(private val httpClient: HttpClient) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> =
        safeCall<CoinResponseDto> {
            httpClient.get(urlString = buildUrl("/assets"))
        }.map { response ->
            response.data.map { it.toCoin() }
        }
}