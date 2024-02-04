package lol.hostov.tracemoe.presentation.ui.screens.details.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.theme.Theme

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Stable
fun GenresView(genres: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.screen_result_genres),
            color = Theme.colors.textSecondary,
            fontSize = 14.sp,
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (genre in genres) {
                GenreItem(genre)
            }
        }
    }
}

@Composable
private fun GenreItem(genre: String) {
    Text(
        text = genre,
        color = Theme.colors.text,
        modifier = Modifier
            .clip(CircleShape)
            .background(Theme.colors.card)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}