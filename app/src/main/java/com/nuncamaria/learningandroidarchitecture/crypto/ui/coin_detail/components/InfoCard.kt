package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import com.nuncamaria.learningandroidarchitecture.ui.theme.Spacing

@Composable
internal fun InfoCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    color: Color = MaterialTheme.colorScheme.onSurface,
    title: String,
    value: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            contentColor = color,
            containerColor = Color.White,
        ),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.S.dp),
            verticalArrangement = Arrangement.spacedBy(Spacing.XS.dp)
        ) {
            Icon(
                modifier = Modifier
                    .border(
                        width = Dp.Hairline,
                        color = color,
                        shape = CircleShape
                    )
                    .size(Spacing.XXXL.dp)
                    .padding(Spacing.XS.dp),
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = title,
                tint = color
            )

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}