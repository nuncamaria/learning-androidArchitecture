package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nuncamaria.learningandroidarchitecture.R
import com.nuncamaria.learningandroidarchitecture.crypto.domain.toDisplayableNumber
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail.components.InfoCard
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListState
import com.nuncamaria.learningandroidarchitecture.crypto.ui.models.CoinUi
import com.nuncamaria.learningandroidarchitecture.ui.theme.Spacing
import com.nuncamaria.learningandroidarchitecture.ui.theme.greenBackground

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
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.MD.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoinDetailHeader(coin)
        CoinDetailBody(coin)
    }
}

@Composable
private fun CoinDetailHeader(coin: CoinUi) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.LG.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.size(Spacing.MEGA.dp),
            imageVector = ImageVector.vectorResource(coin.iconRes),
            contentDescription = coin.name,
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = coin.name,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(text = coin.symbol)
    }
}

@Composable
private fun CoinDetailBody(coin: CoinUi) {
    val absoluteChangeFormatted =
        (coin.priceUsd.value * coin.changePercent24Hr.value / 100).toDisplayableNumber()

    val isPositive = coin.changePercent24Hr.value >= 0.0
    val iconColor = if (isPositive) {
        if (isSystemInDarkTheme()) Color.Green else greenBackground
    } else {
        MaterialTheme.colorScheme.error
    }

    Column(verticalArrangement = Arrangement.spacedBy(Spacing.MD.dp)) {
        InfoCard(
            modifier = Modifier.fillMaxWidth(),
            iconRes = R.drawable.stock,
            title = "Market Cap",
            value = "$ ${coin.marketCapUsd.formatted}"
        )

        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.MD.dp)) {

            InfoCard(
                modifier = Modifier.weight(1f),
                iconRes = R.drawable.dollar,
                title = "Price",
                value = "$ ${coin.priceUsd.formatted}"
            )

            InfoCard(
                modifier = Modifier.weight(1f),
                iconRes = if (isPositive) R.drawable.trending else R.drawable.trending_down,
                color = iconColor,
                title = "Change last 24h",
                value = absoluteChangeFormatted.formatted
            )
        }
    }
}