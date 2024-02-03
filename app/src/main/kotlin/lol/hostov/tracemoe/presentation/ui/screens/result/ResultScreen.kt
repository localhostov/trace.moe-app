package lol.hostov.tracemoe.presentation.ui.screens.result

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.ui.screens.result.views.*
import lol.hostov.tracemoe.presentation.ui.screens.search.SearchViewModel
import java.text.DecimalFormat

@Composable
fun ResultScreen() {
    val scrollState = rememberScrollState()
    val dec = DecimalFormat("#,###")
    val searchViewModel: SearchViewModel = hiltViewModel(LocalContext.current as ComponentActivity)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ToolbarView(
            title = stringResource(R.string.screen_result_title),
            caption = stringResource(
                R.string.screen_result_caption,
                dec.format(searchViewModel.result?.frameCount ?: 0)
            )
        )

        if (searchViewModel.result != null) {
            for (item in searchViewModel.result!!.result) {
                key(item.anilist.id) {
                    ResultItem(searchViewModel, item)

                    if (item == searchViewModel.result!!.result.first()) {
                        BestMatchDividerView()
                    }
                }
            }
        }

        Box(Modifier) // addon space
    }
}
