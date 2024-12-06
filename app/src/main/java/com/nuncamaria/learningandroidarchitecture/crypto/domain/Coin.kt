package com.nuncamaria.learningandroidarchitecture.crypto.domain

import android.icu.text.NumberFormat
import com.nuncamaria.learningandroidarchitecture.crypto.ui.models.CoinUi
import com.nuncamaria.learningandroidarchitecture.crypto.ui.models.DisplayableNumber
import com.nuncamaria.learningandroidarchitecture.core.ui.util.getDrawableIdForCoin
import java.util.Locale

data class Coin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double
) {
    fun toUi(): CoinUi =
        CoinUi(
            id = id,
            rank = rank,
            name = name,
            symbol = symbol,
            marketCapUsd = marketCapUsd.toDisplayableNumber(),
            priceUsd = priceUsd.toDisplayableNumber(),
            changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
            iconRes = getDrawableIdForCoin(symbol)
        )
}

internal fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 2
        maximumFractionDigits = 2
    }

    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}