package core.utils

import androidx.compose.ui.graphics.Color
import java.security.MessageDigest
import kotlin.math.abs

fun generateColorFromSource(
    source: String,
    saturation: Float = 0.5f,
    lightness: Float = 0.35f
): Color {
    val md5 = MessageDigest.getInstance("MD5")
    val digest = md5.digest(source.toByteArray())
    val hex = abs(digest.fold("") { str, it -> str + "%02x".format(it) }.hashCode())
    val hue = (hex % 360).toFloat()

    return Color.hsl(hue, saturation, lightness)
}