package com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nuncamaria.learningandroidarchitecture.crypto.domain.CoinPrice
import com.nuncamaria.learningandroidarchitecture.ui.theme.CryptoTrackerTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun LineChart(
    dataPoints: List<DataPoint>,
    style: ChartStyle,
    visibleDataPointsIndices: IntRange,
    unit: String,
    selectedDataPoint: DataPoint? = null,
    onSelectedDataPoint: (DataPoint) -> Unit = {},
    onXLabelWidthChange: (Float) -> Unit = {},
    showHelperLines: Boolean = true,
    modifier: Modifier = Modifier,
) {

    val textStyle = LocalTextStyle.current.copy(
        fontSize = style.labelFontSize,
    )

    val visibleDataPoints = remember(dataPoints, visibleDataPointsIndices) {
        dataPoints.slice(visibleDataPointsIndices)
    }

    val maxYValue = remember(visibleDataPoints) {
        visibleDataPoints.maxOfOrNull { it.y } ?: 0f
    }

    val minYValue = remember(visibleDataPoints) {
        visibleDataPoints.minOfOrNull { it.y } ?: 0f
    }

    val measurer = rememberTextMeasurer()

    var xLabelWidth by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(xLabelWidth) {
        onXLabelWidthChange(xLabelWidth)
    }

    val selectedDataPointIndex = remember(selectedDataPoint) {
        dataPoints.indexOf(selectedDataPoint)
    }

    var drawPoints by remember { mutableStateOf(listOf<DataPoint>()) }

    var isShowingDataPoints by remember {
        mutableStateOf(selectedDataPoint != null)
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(drawPoints, xLabelWidth) {
                detectHorizontalDragGestures { change, _ ->
                    val newSelectedDataPointIndex = getSelectedDataPointIndex(
                        touchOffsetX = change.position.x,
                        triggerWidth = xLabelWidth,
                        drawPoints = drawPoints
                    )
                    isShowingDataPoints =
                        (newSelectedDataPointIndex + visibleDataPointsIndices.first) in visibleDataPointsIndices

                    if (isShowingDataPoints) {
                        onSelectedDataPoint(dataPoints[newSelectedDataPointIndex])
                    }
                }
            }
    ) {
        // In a Canvas everything works in px, so we need to convert the dp values to px
        val minLabelSpacingY = style.minYLabelSpacing.toPx()
        val verticalPaddingPx = style.verticalPadding.roundToPx()
        val horizontalPaddingPx = style.horizontalPadding.toPx()
        val xAxisLabelSpacingPx = style.xAxisLabelSpacing.toPx()

        val xLabelTextLayoutResults = visibleDataPoints.map {
            measurer.measure(
                text = it.xLabel,
                style = textStyle.copy(textAlign = TextAlign.Center)
            )
        }

        val maxXLabelWidth = xLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0
        val maxXLabelHeight = xLabelTextLayoutResults.maxOfOrNull { it.size.height } ?: 0
        val maxXLabelLineCount = xLabelTextLayoutResults.maxOfOrNull { it.lineCount } ?: 0
        val xLabelLineHeight = if (maxXLabelLineCount > 0) maxXLabelHeight / maxXLabelLineCount else 0

        val viewportHeightPx =
            size.height - (maxXLabelHeight + 2 * verticalPaddingPx + xLabelLineHeight + xAxisLabelSpacingPx)

        // Y label calculation
        val labelViewportHeightPx = viewportHeightPx + xLabelLineHeight
        val labelCountExcludingLastLabel =
            (labelViewportHeightPx / (xLabelLineHeight + minLabelSpacingY)).toInt()

        val valueIncrement = (maxYValue - minYValue) / labelCountExcludingLastLabel

        val yLabels = (0..labelCountExcludingLastLabel).map {
            ValueLabel(
                value = maxYValue - (valueIncrement * it),
                unit = unit,
            )
        }

        val yLabelTextLayoutResults = yLabels.map {
            measurer.measure(
                text = it.formatted(),
                style = textStyle
            )
        }

        val viewportTopY = verticalPaddingPx + xLabelLineHeight + 10f
        val viewportRightX = size.width
        val viewportBottomY = viewportTopY + viewportHeightPx
        val viewportLeftX = 2f * horizontalPaddingPx + maxXLabelWidth
        val viewport = Rect(
            left = viewportLeftX,
            top = viewportTopY,
            right = viewportRightX,
            bottom = viewportBottomY
        )

        drawRect(
            color = Color.Green,
            topLeft = viewport.topLeft,
            size = viewport.size
        )

        xLabelWidth = maxXLabelWidth + xAxisLabelSpacingPx
        xLabelTextLayoutResults.forEachIndexed { index, result ->
            val x = viewportLeftX + xAxisLabelSpacingPx / 2f + xLabelWidth * index
            drawText(
                textLayoutResult = result,
                topLeft = Offset(
                    x = viewportLeftX + xAxisLabelSpacingPx / 2f + xLabelWidth * index,
                    y = viewportBottomY + xAxisLabelSpacingPx
                ),
                color = if (index == selectedDataPointIndex) style.selectedColor else style.unselectedColor
            )

            if (showHelperLines) {
                drawLine(
                    color = if (index == selectedDataPointIndex) style.selectedColor else style.unselectedColor,
                    start = Offset(
                        x = x + result.size.width / 2f,
                        y = viewportBottomY
                    ),
                    end = Offset(
                        x = x + result.size.width / 2f,
                        y = viewportTopY
                    ),
                    strokeWidth = style.helperLineThickness
                )
            }

            if (selectedDataPointIndex == index) {
                val valueLabel = ValueLabel(
                    value = visibleDataPoints[index].y,
                    unit = unit
                )
                val valueResult = measurer.measure(
                    text = valueLabel.formatted(),
                    style = textStyle.copy(
                        color = style.selectedColor
                    ),
                    maxLines = 1
                )
                val textPositionX = if (selectedDataPointIndex == visibleDataPointsIndices.last) {
                    x - valueResult.size.width
                } else {
                    x - valueResult.size.width / 2f
                } + result.size.width / 2f

                val isTextInVisibleRange =
                    (size.width - textPositionX).roundToInt() in 0..size.width.roundToInt()
                if (isTextInVisibleRange) {
                    drawText(
                        textLayoutResult = valueResult,
                        topLeft = Offset(
                            x = textPositionX,
                            y = viewportTopY - valueResult.size.height - 10f
                        )
                    )
                }
            }
        }

        val heightRequiredForLabels = xLabelLineHeight * (labelCountExcludingLastLabel + 1)
        val remainingHeightForLabels = labelViewportHeightPx - heightRequiredForLabels
        val spaceBetweenLabels = remainingHeightForLabels / labelCountExcludingLastLabel

        yLabelTextLayoutResults.forEachIndexed { index, result ->
            val y =
                viewportTopY + index * (xLabelLineHeight + spaceBetweenLabels) - xLabelLineHeight / 2f
            drawText(
                textLayoutResult = result,
                topLeft = Offset(
                    x = horizontalPaddingPx,
                    y = y
                ),
                color = if (index == selectedDataPointIndex) style.selectedColor else style.unselectedColor
            )

            if (showHelperLines) {
                drawLine(
                    color = if (index == selectedDataPointIndex) style.selectedColor else style.unselectedColor,
                    start = Offset(
                        x = viewportLeftX,
                        y = y + result.size.height / 2f
                    ),
                    end = Offset(
                        x = viewportRightX,
                        y = y + result.size.height / 2f
                    ),
                    strokeWidth = style.helperLineThickness
                )
            }
        }

        drawPoints = visibleDataPointsIndices.map {
            val x =
                viewportLeftX + (it - visibleDataPointsIndices.first) * xLabelWidth + xLabelWidth / 2.2f
            val ratio = (dataPoints[it].y - minYValue) / (maxYValue - minYValue)
            val y = viewportBottomY - (ratio * viewportHeightPx)

            DataPoint(
                x = x,
                y = y,
                xLabel = dataPoints[it].xLabel
            )
        }

        // Lines
        val connectionPoints1 = mutableListOf<DataPoint>()
        val connectionPoints2 = mutableListOf<DataPoint>()

        for (i in 1 until drawPoints.size) {
            val previousPoint = drawPoints[i - 1]
            val currentPoint = drawPoints[i]

            val x = (previousPoint.x + currentPoint.x) / 2
            val y1 = previousPoint.y
            val y2 = currentPoint.y

            connectionPoints1.add(DataPoint(x = x, y = y1, xLabel = ""))
            connectionPoints2.add(DataPoint(x = x, y = y2, xLabel = ""))
        }

        val linePath = Path().apply {
            if (drawPoints.isNotEmpty()) {
                moveTo(drawPoints.first().x, drawPoints.first().y)

                for (i in 1 until drawPoints.size) {
                    cubicTo(
                        connectionPoints1[i - 1].x,
                        connectionPoints1[i - 1].y,
                        connectionPoints2[i - 1].x,
                        connectionPoints2[i - 1].y,
                        drawPoints[i].x,
                        drawPoints[i].y
                    )
                }
            }
        }

        drawPath(
            path = linePath,
            color = style.chartLineColor,
            style = Stroke(
                width = 5f,
                cap = StrokeCap.Round,
            )
        )


        drawPoints.forEachIndexed { index, point ->
            if (isShowingDataPoints) {

                val circleOffset = Offset(point.x, point.y)

                drawCircle(
                    color = style.selectedColor,
                    radius = 10f,
                    center = circleOffset
                )

                if (index == selectedDataPointIndex) {
                    drawCircle(
                        color = Color.White,
                        radius = 15f,
                        center = circleOffset
                    )
                    drawCircle(
                        color = style.selectedColor,
                        radius = 15f,
                        center = circleOffset,
                        style = Stroke(width = 3f)
                    )
                }
            }
        }
    }
}

private fun getSelectedDataPointIndex(
    touchOffsetX: Float,
    triggerWidth: Float,
    drawPoints: List<DataPoint>
): Int {
    val triggerRangeLeft = touchOffsetX - triggerWidth / 2f
    val triggerRangeRight = touchOffsetX + triggerWidth / 2f

    return drawPoints.indexOfFirst {
        it.x in triggerRangeLeft..triggerRangeRight
    }
}

@Preview(widthDp = 1000)
@Composable
fun LineChartPreview() {
    CryptoTrackerTheme {

        val coinHistoryRandomized = remember {
            (0..20).map {
                CoinPrice(
                    priceUsd = Random.nextFloat() * 1000.0,
                    dateTime = ZonedDateTime.now().plusHours(it.toLong())
                )
            }
        }

        val style = ChartStyle(
            chartLineColor = Color.Black,
            unselectedColor = Color.LightGray,
            selectedColor = Color.Black,
            helperLineThickness = 5f,
            labelFontSize = 12.sp,
            minYLabelSpacing = 20.dp,
            verticalPadding = 20.dp,
            horizontalPadding = 20.dp,
            xAxisLabelSpacing = 20.dp,
            axisLineThickness = 5f,
        )

        val dataPoints = remember {
            coinHistoryRandomized.map {
                DataPoint(
                    x = it.dateTime.hour.toFloat(),
                    y = it.priceUsd.toFloat(),
                    xLabel = DateTimeFormatter.ofPattern("ha\nMM/dd").format(it.dateTime)
                )
            }
        }

        LineChart(
            dataPoints = dataPoints,
            style = style,
            visibleDataPointsIndices = 0..19,
            unit = "$",
            selectedDataPoint = dataPoints[1],
            modifier = Modifier
                .background(Color.White)
                .height(300.dp)
                .width(700.dp),
        )
    }
}