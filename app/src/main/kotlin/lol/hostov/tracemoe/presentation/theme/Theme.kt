package lol.hostov.tracemoe.presentation.theme

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import lol.hostov.tracemoe.data.remote.model.MeResponse

val LocalColorScheme = staticCompositionLocalOf<ColorScheme> {
    error("No default colors provided")
}

val LocalAccount = compositionLocalOf<MeResponse?> { null }

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No default nav controller provided")
}

object Theme {
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        LocalColorScheme provides darkColorScheme,
        LocalNavController provides rememberNavController()
    ) {
        ProvideTextStyle(
            value = TextStyle.Default.copy(color = Theme.colors.text),
            content = content
        )
    }
}