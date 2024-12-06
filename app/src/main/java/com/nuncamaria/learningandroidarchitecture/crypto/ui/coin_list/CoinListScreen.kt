package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.components.CoinListItem

@Composable
fun CoinListScreen(
    state: CoinListState
) {
    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        CoinListContent(state)
    }
}

@Composable
private fun CoinListContent(state: CoinListState) {
    LazyColumn {
        items(state.coins) { coin ->
            CoinListItem(coinUi = coin, onClick = { /* TODO */ })
            HorizontalDivider()
        }
    }
}