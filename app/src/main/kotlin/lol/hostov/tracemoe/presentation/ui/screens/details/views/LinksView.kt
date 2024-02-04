package lol.hostov.tracemoe.presentation.ui.screens.details.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.data.remote.model.AnilistResponse
import lol.hostov.tracemoe.presentation.theme.Theme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LinksView(links: List<AnilistResponse.MediaItem.ExternalLink>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.screen_result_links),
            color = Theme.colors.textSecondary,
            fontSize = 14.sp,
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (genre in links) {
                LinkItem(genre)
            }
        }
    }
}

@Composable
private fun LinkItem(link: AnilistResponse.MediaItem.ExternalLink) {
    val context = LocalContext.current
    val openSite = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
        context.startActivity(intent)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .clip(CircleShape)
            .background(Theme.colors.card)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Theme.colors.text),
                onClick = { openSite() }
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Text(
            text = link.site,
            color = Theme.colors.text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_outward),
            contentDescription = null,
            tint = Theme.colors.textSecondary,
            modifier = Modifier.size(16.dp)
        )
    }
}