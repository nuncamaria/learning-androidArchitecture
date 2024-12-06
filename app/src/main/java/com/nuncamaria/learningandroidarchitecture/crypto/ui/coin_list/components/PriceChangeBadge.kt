package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.nuncamaria.learningandroidarchitecture.crypto.ui.models.DisplayableNumber
import com.nuncamaria.learningandroidarchitecture.ui.theme.Spacing
import com.nuncamaria.learningandroidarchitecture.ui.theme.greenBackground

@Composable
fun PriceChangeBadge(
    change: DisplayableNumber,
    modifier: Modifier = Modifier
) {
    val textColor =
        if (change.value < 0.0) MaterialTheme.colorScheme.onError
        else Color.Green

    val badgeColor =
        if (change.value < 0.0) MaterialTheme.colorScheme.error
        else greenBackground

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Spacing.MD.dp))
            .background(badgeColor)
            .padding(Spacing.XXXS.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (change.value < 0.0) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            tint = textColor
        )

        Text(
            modifier = Modifier.padding(Spacing.XXS.dp),
            color = textColor,
            text = change.formatted,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }

}

@PreviewLightDark
@Composable
fun PriceChangeBadgePreview() {
    Column {
        PriceChangeBadge(
            change = DisplayableNumber(
                value = 2.35,
                formatted = "2.35 %"
            )
        )

        PriceChangeBadge(
            change = DisplayableNumber(
                value = -1.35,
                formatted = "-1.35 %"
            )
        )
    }
}