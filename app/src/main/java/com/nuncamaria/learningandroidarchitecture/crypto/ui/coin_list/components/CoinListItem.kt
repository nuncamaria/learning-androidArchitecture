package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.nuncamaria.learningandroidarchitecture.crypto.domain.Coin
import com.nuncamaria.learningandroidarchitecture.crypto.ui.models.CoinUi
import com.nuncamaria.learningandroidarchitecture.ui.theme.Spacing


@Composable
fun CoinListItem(
    coinUi: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.S.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(coinUi.iconRes),
            contentDescription = coinUi.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(Spacing.JUMBO.dp)
        )

        Column {
            Text(text = coinUi.symbol)
            Text(text = coinUi.name)
        }

        Column {
            Text(text = "$ ${coinUi.marketCapUsd.formatted}")
            Text(text = coinUi.priceUsd.formatted)
        }
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
fun CoinListItemPreview() {
    CoinListItem(
        coinUi = previewCoin.toUi(),
        onClick = {}
    )
}

internal val previewCoin = Coin(
    id = "bitcoin",
    name = "Bitcoin",
    rank = 1,
    symbol = "BTC",
    marketCapUsd = 12341234.234,
    priceUsd = 50_000.0,
    changePercent24Hr = 0.1
)