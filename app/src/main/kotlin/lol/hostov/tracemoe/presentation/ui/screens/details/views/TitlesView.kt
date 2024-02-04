package lol.hostov.tracemoe.presentation.ui.screens.details.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.data.remote.model.AnilistResponse

@Composable
@Stable
fun TitlesView(
    title: AnilistResponse.MediaItem.Title,
    synonyms: List<String>
) {
    Column {
        InfoItem(
            title = stringResource(R.string.screen_result_synonyms),
            value = synonyms.joinToString(", ")
        )

        InfoItem(
            title = stringResource(R.string.screen_result_native),
            value = title.native
        )

        InfoItem(
            title = stringResource(R.string.screen_result_romaji),
            value = title.romaji ?: stringResource(R.string.screen_result_unknown)
        )

        InfoItem(
            title = stringResource(R.string.screen_result_english),
            value = title.english ?: stringResource(R.string.screen_result_unknown)
        )

        InfoItem(
            title = stringResource(R.string.screen_result_chinese),
            value = title.chinese ?: stringResource(R.string.screen_result_unknown)
        )
    }
}