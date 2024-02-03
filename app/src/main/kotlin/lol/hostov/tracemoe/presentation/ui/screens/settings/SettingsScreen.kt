package lol.hostov.tracemoe.presentation.ui.screens.settings

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import core.Constants
import core.utils.generateColorFromSource
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.components.Cell
import lol.hostov.tracemoe.presentation.components.SquigglyDivider
import lol.hostov.tracemoe.presentation.theme.LocalAccount
import lol.hostov.tracemoe.presentation.ui.shapes.MaterialStarShape

@Composable
fun SettingsScreen() {
    val scrollState = rememberScrollState()
    val account = LocalAccount.current
    val context = LocalContext.current
    val accountColor = generateColorFromSource(
        source = "${account?.id}",
        saturation = 0.75f,
        lightness = 0.4f
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(Modifier.statusBarsPadding())

        for (redirectItem in redirectItems) {
            key(redirectItem.id) {
                Cell(
                    title = stringResource(redirectItem.title),
                    caption = stringResource(redirectItem.caption),
                    leadingIcon = {
                        Box(modifier = Modifier
                            .size(48.dp)
                            .clip(MaterialStarShape)
                            .background(accountColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(redirectItem.icon),
                                contentDescription = "icon",
                                modifier = Modifier.size(20.dp),
                                tint = accountColor
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = accountColor),
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirectItem.url))
                                context.startActivity(intent)
                            }
                        )
                        .padding(horizontal = 16.dp)
                )

                if (redirectItem != redirectItems.last()) {
                    SquigglyDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 48.dp + 16.dp + 16.dp)
                            .height(2.dp)
                    )
                }
            }
        }
    }
}

private val redirectItems = listOf(
    RedirectItem(
        id = 1,
        icon = R.drawable.ic_github,
        title = R.string.screen_settings_githubTitle,
        caption = R.string.screen_settings_githubCaption,
        url = Constants.GITHUB_PROJECT_URL
    ),
    RedirectItem(
        id = 2,
        icon = R.drawable.ic_app,
        title = R.string.screen_settings_tracemoeTitle,
        caption = R.string.screen_settings_tracemoeCaption,
        url = Constants.DEFAULT_URL
    )
)

private data class RedirectItem(
    val id: Int,
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    @StringRes val caption: Int,
    val url: String,
)