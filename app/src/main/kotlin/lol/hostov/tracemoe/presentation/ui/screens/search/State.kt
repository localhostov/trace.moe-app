package lol.hostov.tracemoe.presentation.ui.screens.search

data class SearchScreenState(
    val searchQuery: String = "",
    val searchByUrlInProgress: Boolean = false,
    val searchImageInProgress: Boolean = false,
)