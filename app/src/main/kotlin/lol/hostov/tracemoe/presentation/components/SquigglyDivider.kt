package lol.hostov.tracemoe.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import lol.hostov.tracemoe.presentation.theme.Theme
import kotlin.math.PI
import kotlin.math.sin

@Composable
@SuppressLint("ModifierParameter")
fun SquigglyDivider(
    modifier: Modifier = Modifier,
    color: Color = Theme.colors.divider,
    amplitude: Int = 16,
    strokeWidth: Float = 3f,
) {
    SquigglyDivider(
        modifier = modifier,
        brush = Brush.horizontalGradient(
            colors = listOf(color, color)
        ),
        amplitude = amplitude,
        strokeWidth = strokeWidth,
    )
}

@Composable
@SuppressLint("ModifierParameter")
fun SquigglyDivider(
    modifier: Modifier = Modifier,
    brush: Brush,
    amplitude: Int = 16,
    strokeWidth: Float = 3f,
) {
    Box(modifier = modifier.drawWithCache {
        val height = size.height
        val width = size.width
        val points = mutableListOf<Offset>()

        for (x in 0..width.toInt()) {
            val y = sin(x * ((width / amplitude) * PI / width)) * (height / 2) + (height / 2)

            points.add(
                Offset(
                    x = x.toFloat(),
                    y = y.toFloat()
                )
            )
        }

        onDrawBehind {
            drawPoints(
                points = points,
                strokeWidth = strokeWidth,
                pointMode = PointMode.Points,
                brush = brush,
                cap = StrokeCap.Round
            )
        }
    })
}