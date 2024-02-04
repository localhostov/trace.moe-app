package lol.hostov.tracemoe.presentation.ui.screens.details.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.data.remote.model.AnilistResponse
import lol.hostov.tracemoe.presentation.theme.Theme

private fun String.capitalized() = this.lowercase().replaceFirstChar { it.uppercase() }

@Composable
fun OtherInfoView(item: AnilistResponse.MediaItem) {
    val context = LocalContext.current
    val date = { (year, month, day): AnilistResponse.MediaItem.Date ->
        if (day == 0 && month == 0 && year == 0) context.getString(R.string.screen_result_unknown) else {
            val months = context.resources.getStringArray(R.array.months)

            "$day ${months[month - 1]} $year"
        }
    }

    Column {
        InfoItem(
            title = stringResource(R.string.screen_result_startDate),
            value = date(item.startDate)
        )
        InfoItem(
            title = stringResource(R.string.screen_result_endDate),
            value = date(item.endDate)
        )
        InfoItem(
            title = stringResource(R.string.screen_result_season),
            value = item.season.capitalized()
        )
        InfoItem(
            title = stringResource(R.string.screen_result_status),
            value = item.status.capitalized()
        )
        InfoItem(
            title = stringResource(R.string.screen_result_format),
            value = item.format.capitalized()
        )
        InfoItem(
            title = stringResource(R.string.screen_result_duration),
            value = "${item.duration} min"
        )
        InfoItem(
            title = stringResource(R.string.screen_result_source),
            value = item.source.capitalized()
        )
    }
}

@Composable
fun InfoItem(title: String, value: String) {
    Row(modifier = Modifier
        .padding(vertical = 6.dp)
        .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Theme.colors.textSecondary,
            modifier = Modifier.fillMaxWidth(0.5f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        SelectionContainer {
            Text(
                text = value,
                fontSize = 16.sp,
                color = Theme.colors.text,
            )
        }
    }
}