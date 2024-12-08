package com.nuncamaria.learningandroidarchitecture.crypto.ui.models

import androidx.annotation.DrawableRes
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail.chart.DataPoint

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val coinPriceHistory: List<DataPoint> = emptyList(),
    @DrawableRes val iconRes: Int
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String
)