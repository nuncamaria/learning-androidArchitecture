package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list

import androidx.compose.runtime.Immutable
import com.nuncamaria.learningandroidarchitecture.crypto.ui.models.CoinUi

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)
