package lol.hostov.tracemoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import dagger.hilt.android.AndroidEntryPoint
import lol.hostov.tracemoe.presentation.theme.LocalAccount
import lol.hostov.tracemoe.presentation.theme.MainTheme
import lol.hostov.tracemoe.presentation.ui.screens.main.Navigation
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.Transparent.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            )
        )

        viewModel.getMeInfo()

        setContent {
            MainTheme {
                CompositionLocalProvider(
                    LocalAccount provides viewModel.account
                ) {
                    Navigation()
                }
            }
        }
    }
}