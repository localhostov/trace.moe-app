package lol.hostov.tracemoe.presentation.ui.screens.details

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import lol.hostov.tracemoe.presentation.ui.screens.details.views.BannerView
import lol.hostov.tracemoe.presentation.ui.screens.search.SearchViewModel

@Composable
fun DetailsScreen(anilistId: Int) {
    val scrollState = rememberScrollState()
    val searchViewModel: SearchViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
    val item = remember(anilistId) {
        searchViewModel.details?.data?.page?.media?.find { it.id == anilistId }!!
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        BannerView(item)
    }
}

//@Composable
//private inline fun WithPadding(content: @Composable () -> Unit) {
//    Box(Modifier.padding(horizontal = 16.dp)) {
//        content()
//    }
//}