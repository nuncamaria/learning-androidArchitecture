package com.nuncamaria.learningandroidarchitecture.crypto.domain.datasource

import com.nuncamaria.learningandroidarchitecture.core.domain.util.NetworkError
import com.nuncamaria.learningandroidarchitecture.core.domain.util.Result
import com.nuncamaria.learningandroidarchitecture.crypto.domain.Coin
import com.nuncamaria.learningandroidarchitecture.crypto.domain.CoinPrice
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>

    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime //Object that contains info about current time and the time zone
    ): Result<List<CoinPrice>, NetworkError>
}