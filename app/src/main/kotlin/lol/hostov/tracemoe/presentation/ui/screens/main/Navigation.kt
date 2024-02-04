package lol.hostov.tracemoe.presentation.ui.screens.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import lol.hostov.tracemoe.presentation.theme.LocalNavController
import lol.hostov.tracemoe.presentation.theme.Theme
import lol.hostov.tracemoe.presentation.ui.navigation.NavHost
import lol.hostov.tracemoe.presentation.ui.navigation.tabs

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Navigation() {
    val navController = LocalNavController.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentTab = backStackEntry?.destination?.parent?.route

    val bottomBarInsets = WindowInsets.systemBars.only(
        WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
    )

    Scaffold(
        contentColor = Theme.colors.background,
        content = { contentPadding -> NavHost(contentPadding) },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colors.bottomBarBackground)
                    .windowInsetsPadding(bottomBarInsets)
                    .selectableGroup()
                    .background(Theme.colors.bottomBarBackground)
                    .imePadding()
                    .animateContentSize()
                    .then(if (WindowInsets.isImeVisible) Modifier.height(0.dp) else Modifier)
            ) {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        modifier = Modifier.heightIn(max = 74.dp),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Theme.colors.bottomBarActive,
                            unselectedIconColor = Theme.colors.bottomBarInactive,
                            indicatorColor = Theme.colors.bottomBarIndicator
                        ),
                        selected = currentTab == tab.route,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(tab.icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(tab.title),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                color = animateColorAsState(
                                    targetValue = if (currentTab == tab.route) {
                                        Theme.colors.bottomBarActive
                                    } else {
                                        Theme.colors.bottomBarInactive
                                    },
                                    label = "bottom bar label"
                                ).value
                            )
                        }
                    )
                }
            }
        }
    )
}
