package lol.hostov.tracemoe.presentation.ui.screens.account.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.utils.generateColorFromSource
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.theme.LocalAccount
import lol.hostov.tracemoe.presentation.theme.Theme
import java.text.DecimalFormat

@Composable
fun QuotaProgressView() {
    val account = LocalAccount.current
    val dec = DecimalFormat("#,###")
    val progress = (account?.quotaUsed?.times(100f)?.div(account.quota.toFloat()) ?: 0f) / 100f
    val accountColor = generateColorFromSource(
        source = "${account?.id}",
        saturation = 0.75f,
        lightness = 0.4f
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.screen_account_quotaProgressTitle),
            fontWeight = FontWeight.SemiBold
        )

        LinearProgressIndicator(
            progress = { progress },
            color = accountColor,
            trackColor = accountColor.copy(alpha = 0.2f),
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            strokeCap = StrokeCap.Round
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(
                    R.string.screen_account_usedQuota,
                    dec.format(account?.quotaUsed ?: 0)
                ),
                fontSize = 14.sp,
                color = Theme.colors.textSecondary
            )

            Text(
                text = stringResource(
                    R.string.screen_account_totalQuota,
                    dec.format(account?.quota ?: 0)
                ),
                fontSize = 14.sp,
                color = Theme.colors.textSecondary
            )
        }
    }
}