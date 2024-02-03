package lol.hostov.tracemoe.presentation.ui.screens.account.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import core.utils.generateColorFromSource
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.components.Cell
import lol.hostov.tracemoe.presentation.components.SquigglyDivider
import lol.hostov.tracemoe.presentation.theme.LocalAccount
import lol.hostov.tracemoe.presentation.ui.shapes.MaterialStarShape

@Composable
fun CellsView() {
    val account = LocalAccount.current
    val accountColor = generateColorFromSource(
        source = "${account?.id}",
        saturation = 0.75f,
        lightness = 0.4f
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Cell(
            title = stringResource(R.string.screen_account_priority, "${account?.priority}"),
            caption = stringResource(R.string.screen_account_priorityCaption),
            leadingIcon = {
                Box(modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialStarShape)
                    .background(accountColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "priority icon",
                        modifier = Modifier.size(20.dp),
                        tint = accountColor
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        SquigglyDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp + 16.dp)
                .height(2.dp)
        )

        Cell(
            title = "${account?.concurrency}",
            caption = stringResource(R.string.screen_account_concurrencyCaption),
            leadingIcon = {
                Box(modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialStarShape)
                    .background(accountColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cloud_upload),
                        contentDescription = "concurrency icon",
                        modifier = Modifier.size(20.dp),
                        tint = accountColor
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}