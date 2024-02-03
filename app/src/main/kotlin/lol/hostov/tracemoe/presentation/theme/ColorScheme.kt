package lol.hostov.tracemoe.presentation.theme

import androidx.compose.ui.graphics.Color

data class ColorScheme(
    val background: Color,
    val text: Color,
    val textSecondary: Color,
    val bottomBarBackground: Color,
    val bottomBarActive: Color,
    val bottomBarInactive: Color,
    val bottomBarIndicator: Color,
    val shimmerBackground: Color,
    val shimmerWave: Color,
    val linearProgress: Color,
    val divider: Color,
    val card: Color,
)

val darkColorScheme = ColorScheme(
    background = Color.Black,
    text = Color.White,
    bottomBarBackground = Color(0xFF131313),
    bottomBarActive = Color.White,
    bottomBarInactive = Color(0xFF8A8A8A),
    bottomBarIndicator = Color(0xFF1F1F1F),
    shimmerBackground = Color(0xFF121213),
    shimmerWave = Color(0xFF1F1F1F),
    textSecondary = Color(0xFF777777),
    linearProgress = Color.White,
    divider = Color(0xFF313131),
    card = Color(0xFF141414)
)

