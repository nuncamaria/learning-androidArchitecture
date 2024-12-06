package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListState
import com.nuncamaria.learningandroidarchitecture.crypto.ui.models.CoinUi
import com.nuncamaria.learningandroidarchitecture.ui.theme.Spacing

@Composable
fun CoinDetailScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedCoin != null) {
        val coin = state.selectedCoin

        CoinDetailContent(coin, modifier)
    }
}

@Composable
private fun CoinDetailContent(coin: CoinUi, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(Spacing.MEGA.dp),
            imageVector = ImageVector.vectorResource(coin.iconRes),
            contentDescription = coin.name,
            tint = MaterialTheme.colorScheme.primary,
        )

        Text(
            text = coin.name,
            style = MaterialTheme.typography.displayLarge
        )
        Text(text = coin.symbol)
    }
}