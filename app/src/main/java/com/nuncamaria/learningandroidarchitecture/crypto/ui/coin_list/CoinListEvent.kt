package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list

import com.nuncamaria.learningandroidarchitecture.core.domain.util.NetworkError

/**
 * This is for one time information se send from the ViewModel to the UI.
 */
sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}