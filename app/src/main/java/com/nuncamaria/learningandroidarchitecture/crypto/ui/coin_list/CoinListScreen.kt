package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.components.CoinListItem

@Composable
fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        CoinListContent(state, onAction, modifier)
    }
}

@Composable
private fun CoinListContent(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(state.coins) { coin ->
            CoinListItem(
                coinUi = coin,
                onClick = { onAction(CoinListAction.OnCoinClick(coin)) }
            )
            HorizontalDivider()
        }
    }
}