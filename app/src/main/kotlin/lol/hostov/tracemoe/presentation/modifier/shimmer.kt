package lol.hostov.tracemoe.presentation.modifier

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import lol.hostov.tracemoe.presentation.theme.Theme

fun Modifier.shimmer(enabled: Boolean = true): Modifier = composed {
    if (!enabled) return@composed this

    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmer")
    val startOffsetX = transition.animateFloat(
        initialValue = -(3 * size.width.toFloat()),
        targetValue = 3 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(tween(1500)),
        label = "shimmer"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Theme.colors.shimmerBackground,
                Theme.colors.shimmerWave,
                Theme.colors.shimmerBackground,
            ),
            start = Offset(startOffsetX.value, 0f),
            end = Offset(startOffsetX.value + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}