package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuncamaria.learningandroidarchitecture.core.domain.util.onError
import com.nuncamaria.learningandroidarchitecture.core.domain.util.onSuccess
import com.nuncamaria.learningandroidarchitecture.crypto.domain.datasource.CoinDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart { getCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            CoinListState()
        )

    private val _event = Channel<CoinListEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnRefresh -> getCoins()
            is CoinListAction.OnCoinClick -> _state.update {
                it.copy(selectedCoin = action.coinUi)
            }
        }
    }

    private fun getCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource.getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { coin -> coin.toUi() })
                    }
                }
                .onError {
                    _state.update { it.copy(isLoading = false) }
                    _event.send(CoinListEvent.Error(it))
                }
        }
    }
}