package lol.hostov.tracemoe.presentation.ui.screens.result.views

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import core.utils.convertSecondsToTime
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.data.remote.model.SearchResponseItem
import lol.hostov.tracemoe.presentation.modifier.shimmer
import lol.hostov.tracemoe.presentation.theme.LocalNavController
import lol.hostov.tracemoe.presentation.theme.Theme
import lol.hostov.tracemoe.presentation.ui.navigation.Routing
import lol.hostov.tracemoe.presentation.ui.screens.search.SearchViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResultItem(searchViewModel: SearchViewModel, data: SearchResponseItem) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    var imageIsLoading by remember { mutableStateOf(true) }
    val dec = DecimalFormat("#.##")

    val detailsItems = remember { searchViewModel.details?.data?.page?.media }
    val duration = detailsItems?.find { it.id == data.anilist.id }?.duration?.times(60)
    val progress = (data.from * 100f / (duration ?: 0)) / 100f

    fun openDetails() {
        if (detailsItems?.find { it.id == data.anilist.id } != null) {
            val anilistId = data.anilist.id

            navController.navigate(Routing.Screen.DETAILS + "/$anilistId")
        } else {
            Toast.makeText(context, "Can't find details for this anime", Toast.LENGTH_SHORT).show()
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Theme.colors.text),
                onClick = ::openDetails
            )
            .padding(horizontal = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .width(120.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmer(imageIsLoading)
        ) {
            AsyncImage(
                model = data.image,
                contentDescription = data.filename,
                contentScale = ContentScale.Crop,
                onSuccess = { imageIsLoading = false },
                modifier = Modifier.fillMaxSize()
            )

            if (data.anilist.isAdult) {
                Text(
                    text = "18+",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Red)
                        .padding(horizontal = 4.dp)
                )
            }

            if (duration != null) {
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f)
                            )
                        ))
                        .padding(4.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .height(4.dp)
                            .fillMaxWidth()
                            .clip(CircleShape),
                        strokeCap = StrokeCap.Round,
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.3f)
                    )
                }
            }
        }

        Column {
            Text(
                text = data.anilist.title.english ?: data.anilist.title.native,
                color = Theme.colors.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE)
            )

            Text(
                text = stringResource(
                    R.string.screen_result_similarity,
                    dec.format(100f * data.similarity)
                ),
                color = Theme.colors.textSecondary,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (data.episode != null) {
                Text(
                    text = stringResource(R.string.screen_result_episode, data.episode),
                    color = Theme.colors.textSecondary,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Text(
                text = "${convertSecondsToTime(data.from.toInt())} — ${convertSecondsToTime(data.to.toInt())}",
                color = Theme.colors.textSecondary,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}