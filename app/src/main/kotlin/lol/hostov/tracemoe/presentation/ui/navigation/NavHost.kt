package lol.hostov.tracemoe.presentation.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import lol.hostov.tracemoe.presentation.theme.LocalNavController
import lol.hostov.tracemoe.presentation.theme.Theme
import lol.hostov.tracemoe.presentation.ui.screens.account.AccountScreen
import lol.hostov.tracemoe.presentation.ui.screens.details.DetailsScreen
import lol.hostov.tracemoe.presentation.ui.screens.result.ResultScreen
import lol.hostov.tracemoe.presentation.ui.screens.search.SearchScreen
import lol.hostov.tracemoe.presentation.ui.screens.settings.SettingsScreen

@Composable
fun NavHost(contentPadding: PaddingValues) {
    androidx.navigation.compose.NavHost(
        navController = LocalNavController.current,
        startDestination = Routing.Tab.ACCOUNT,
        route = Routing.ROOT,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = contentPadding.calculateBottomPadding())
            .background(Theme.colors.background),
    ) {
        setupAccountTab()
        setupSearchTab()
        setupSettingsTab()
    }
}

private fun NavGraphBuilder.setupAccountTab() {
    navigation(
        route = Routing.Tab.ACCOUNT,
        startDestination = Routing.Screen.ACCOUNT,
    ) {
        composable(Routing.Screen.ACCOUNT) {
            AccountScreen()
        }
    }
}

private fun NavGraphBuilder.setupSearchTab() {
    navigation(
        route = Routing.Tab.SEARCH,
        startDestination = Routing.Screen.SEARCH,
    ) {
        composable(Routing.Screen.SEARCH) {
            SearchScreen()
        }

        composable(Routing.Screen.RESULT) {
            ResultScreen()
        }

        composable(
            route = Routing.Screen.DETAILS + "/{${NavArg.ANILIST_ID}}",
            arguments = listOf(
                navArgument(NavArg.ANILIST_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(NavArg.ANILIST_ID) ?: 0

            DetailsScreen(id)
        }
    }
}

private fun NavGraphBuilder.setupSettingsTab() {
    navigation(
        route = Routing.Tab.SETTINGS,
        startDestination = Routing.Screen.SETTINGS
    ) {
        composable(Routing.Screen.SETTINGS) {
            SettingsScreen()
        }
    }
}