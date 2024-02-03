package lol.hostov.tracemoe.presentation.ui.screens.result.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.components.SquigglyDivider
import lol.hostov.tracemoe.presentation.theme.Theme

@Composable
fun BestMatchDividerView() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SquigglyDivider(
            modifier = Modifier
                .weight(1f)
                .height(2.dp)
        )

        Text(
            text = stringResource(R.string.screen_result_bestMatch),
            color = Theme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )

        SquigglyDivider(
            modifier = Modifier
                .weight(1f)
                .height(2.dp)
        )
    }
}