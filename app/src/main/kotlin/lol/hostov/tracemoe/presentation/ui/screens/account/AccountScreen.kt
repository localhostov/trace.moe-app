package lol.hostov.tracemoe.presentation.ui.screens.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import lol.hostov.tracemoe.presentation.ui.screens.account.views.*

@Composable
fun AccountScreen() {
    val viewModel: AccountViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getMe()
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        IdView()
        QuotaProgressView()
        CellsView()
    }
}