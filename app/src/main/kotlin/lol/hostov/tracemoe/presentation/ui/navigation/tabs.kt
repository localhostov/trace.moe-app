package lol.hostov.tracemoe.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import lol.hostov.tracemoe.R

val accountTab = Tab(
    route = Routing.Tab.ACCOUNT,
    title = R.string.tab_account_title,
    icon = R.drawable.ic_user,
)

val searchTab = Tab(
    route = Routing.Tab.SEARCH,
    title = R.string.tab_search_title,
    icon = R.drawable.ic_search
)

val settingsTab = Tab(
    route = Routing.Tab.SETTINGS,
    title = R.string.tab_settings_title,
    icon = R.drawable.ic_settings
)

val tabs = listOf(
    accountTab,
    searchTab,
    settingsTab
)

data class Tab(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
)