package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list

import com.nuncamaria.learningandroidarchitecture.crypto.ui.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
    data object OnRefresh : CoinListAction
}