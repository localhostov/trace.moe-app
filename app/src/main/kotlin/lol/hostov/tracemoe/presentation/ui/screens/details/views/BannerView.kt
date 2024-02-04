package lol.hostov.tracemoe.presentation.ui.screens.details.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.data.remote.model.AnilistResponse
import lol.hostov.tracemoe.presentation.modifier.shimmer
import lol.hostov.tracemoe.presentation.theme.LocalNavController
import lol.hostov.tracemoe.presentation.theme.Theme

@Composable
fun BannerView(item: AnilistResponse.MediaItem) {
    Box {
        Column(verticalArrangement = Arrangement.spacedBy(-POSTER_OFFSET)) {
            Banner(item.bannerImage)

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Poster(item.coverImage.large, item.isAdult)

                Title(item.title)
            }
        }

        TopControls(item.id)
    }
}

@Composable
private fun Banner(url: String?) {
    var imageIsLoading by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(BANNER_HEIGHT)
            .shimmer(imageIsLoading)
    ) {
        if (url == null) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Theme.colors.card,
                            Color.Transparent
                        )
                    )
                )
            )
        } else {
            AsyncImage(
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                onSuccess = { imageIsLoading = false }
            )

            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                ))
        }
    }
}

@Composable
private fun Poster(url: String, isAdult: Boolean) {
    var imageIsLoading by remember { mutableStateOf(true) }

    Box(contentAlignment = Alignment.TopEnd) {
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier
                .border(4.dp, Theme.colors.background, RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .height(POSTER_HEIGHT)
                .width(120.dp)
                .shimmer(imageIsLoading),
            contentScale = ContentScale.Crop,
            onSuccess = { imageIsLoading = false }
        )

        if (isAdult) {
            Text(
                text = "18+",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Red)
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Title(title: AnilistResponse.MediaItem.Title) {
    Column(
        modifier = Modifier.padding(top = 8.dp + POSTER_OFFSET)
    ) {
        SelectionContainer {
            Text(
                text = title.native,
                color = Theme.colors.textSecondary,
                fontSize = 14.sp,
                modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
                maxLines = 1
            )
        }

        SelectionContainer {
            Text(
                text = title.english ?: title.romaji ?: title.native,
                color = Theme.colors.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 4
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TopControls(id: Int) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val openMAL = {
        val url = "https://myanimelist.net/anime/$id"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        context.startActivity(intent)
    }

    val copyLink = {
        val url = "https://myanimelist.net/anime/$id"
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("url", url)

        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(context, "Link copied", Toast.LENGTH_SHORT).show()
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .statusBarsPadding()
            .padding(vertical = 2.dp, horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Theme.colors.background.copy(alpha = 0.6f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = Theme.colors.text),
                    onClick = { navController.popBackStack() },
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = null,
                tint = Theme.colors.text,
                modifier = Modifier.size(20.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Theme.colors.background.copy(alpha = 0.6f))
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = Theme.colors.text),
                    onClick = { openMAL() },
                    onLongClick = { copyLink() }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_myanimelist),
                contentDescription = null,
                tint = Theme.colors.text,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

private val POSTER_HEIGHT = 160.dp
private val BANNER_HEIGHT = 148.dp
private val POSTER_OFFSET = 60.dp