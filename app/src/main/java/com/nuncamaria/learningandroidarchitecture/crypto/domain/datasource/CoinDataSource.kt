package com.nuncamaria.learningandroidarchitecture.crypto.domain.datasource

import com.nuncamaria.learningandroidarchitecture.core.domain.util.NetworkError
import com.nuncamaria.learningandroidarchitecture.core.domain.util.Result
import com.nuncamaria.learningandroidarchitecture.crypto.domain.Coin

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}