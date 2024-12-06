package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
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
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(Spacing.MD.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.weight(2f),
            horizontalArrangement = Arrangement.spacedBy(Spacing.MD.dp)
        ) {
            Icon(
                modifier = Modifier.size(Spacing.JUMBO.dp),
                imageVector = ImageVector.vectorResource(coinUi.iconRes),
                contentDescription = coinUi.name,
                tint = MaterialTheme.colorScheme.primary,
            )

            Column {
                Text(
                    text = coinUi.symbol,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = coinUi.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${coinUi.priceUsd.formatted}",
                style = MaterialTheme.typography.bodyMedium
            )
            PriceChangeBadge(
                change = coinUi.changePercent24Hr,
                modifier = Modifier.align(Alignment.End)
            )
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