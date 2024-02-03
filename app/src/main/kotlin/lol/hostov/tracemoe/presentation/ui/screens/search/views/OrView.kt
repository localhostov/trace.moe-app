package lol.hostov.tracemoe.presentation.ui.screens.search.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.components.SquigglyDivider
import lol.hostov.tracemoe.presentation.theme.Theme

@Composable
fun OrView() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        SquigglyDivider(modifier = Modifier.size(width = 48.dp, height = 2.dp))

        Text(
            text = stringResource(R.string.screen_account_or),
            color = Theme.colors.textSecondary,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Light
        )

        SquigglyDivider(modifier = Modifier.size(width = 48.dp, height = 2.dp))
    }
}