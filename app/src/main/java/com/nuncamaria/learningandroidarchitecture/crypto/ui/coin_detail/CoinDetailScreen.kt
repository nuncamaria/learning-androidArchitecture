package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nuncamaria.learningandroidarchitecture.R
import com.nuncamaria.learningandroidarchitecture.crypto.domain.toDisplayableNumber
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail.chart.ChartStyle
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail.chart.DataPoint
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail.chart.LineChart
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

        CoinDetailContent(
            coin = coin,
            modifier = modifier.verticalScroll(rememberScrollState())
        )
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
            modifier = Modifier.size(Spacing.GIGA.dp + Spacing.LG.dp),
            imageVector = ImageVector.vectorResource(coin.iconRes),
            contentDescription = coin.name,
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = coin.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(text = coin.symbol)
    }
}

@OptIn(ExperimentalLayoutApi::class)
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

    FlowRow(
        modifier = Modifier.padding(bottom = Spacing.MD.dp),
        horizontalArrangement = Arrangement.spacedBy(Spacing.MD.dp),
        verticalArrangement = Arrangement.spacedBy(Spacing.MD.dp)
    ) {
        InfoCard(
            modifier = Modifier.fillMaxWidth(),
            iconRes = R.drawable.stock,
            title = "Market Cap",
            value = "$ ${coin.marketCapUsd.formatted}"
        )

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

    if (coin.coinPriceHistory.isNotEmpty()) {

        var selectedDataPoint by remember { mutableStateOf<DataPoint?>(null) }

        var labelWidth by remember { mutableFloatStateOf(0f) }

        var totalChartWidth by remember { mutableFloatStateOf(0f) }

        val amountOfVisibleDataPoints =
            if (labelWidth > 0f) ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt() else 0

        val startIndex =
            (coin.coinPriceHistory.lastIndex - amountOfVisibleDataPoints).coerceAtLeast(0)

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = CardDefaults.outlinedCardBorder()
        ) {
            LineChart(
                dataPoints = coin.coinPriceHistory,
                style = ChartStyle(
                    chartLineColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.secondary,
                    selectedColor = MaterialTheme.colorScheme.primary,
                    helperLineThickness = 2f,
                    labelFontSize = 11.sp,
                    minYLabelSpacing = 20.dp,
                    verticalPadding = 25.dp,
                    horizontalPadding = 25.dp,
                    xAxisLabelSpacing = 10.dp,
                    axisLineThickness = 5f,
                ),
                visibleDataPointsIndices = startIndex..coin.coinPriceHistory.lastIndex,
                unit = "$",
                selectedDataPoint = selectedDataPoint,
                onSelectedDataPoint = { selectedDataPoint = it },
                onXLabelWidthChange = { labelWidth = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.S.dp)
                    .padding(end = Spacing.S.dp)
                    .aspectRatio(4 / 3f)
                    .onSizeChanged { totalChartWidth = it.width.toFloat() }
            )
        }
    }
}