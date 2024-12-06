package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.components.CoinListItem

@Composable
fun CoinListScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        CoinListContent(state, modifier)
    }
}

@Composable
private fun CoinListContent(state: CoinListState, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(state.coins) { coin ->
            CoinListItem(coinUi = coin, onClick = { /* TODO */ })
            HorizontalDivider()
        }
    }
}