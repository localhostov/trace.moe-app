package lol.hostov.tracemoe.presentation.ui.screens.search

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import core.utils.generateColorFromSource
import lol.hostov.tracemoe.presentation.theme.LocalAccount
import lol.hostov.tracemoe.presentation.theme.LocalNavController
import lol.hostov.tracemoe.presentation.ui.navigation.Routing
import lol.hostov.tracemoe.presentation.ui.screens.search.views.*

@Composable
fun SearchScreen() {
    val account = LocalAccount.current
    val navController = LocalNavController.current
    val viewModel: SearchViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
    var pickerIsBlocked by remember { mutableStateOf(false) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            pickerIsBlocked = false

            if (uri != null) {
                viewModel.searchByImage(uri, onSuccess = {
                    navController.navigate(Routing.Screen.RESULT)
                })
            }
        }
    )
    val accountColor = generateColorFromSource(
        source = "${account?.id}",
        saturation = 0.75f,
        lightness = 0.4f
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = accountColor),
                onClick = {
                    if (pickerIsBlocked) return@clickable

                    pickerIsBlocked = true
                    imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            WithPadding {
                CallToActionView(
                    accountColor = accountColor,
                    viewModel = viewModel
                )
            }

            LastImagesView(viewModel)

            WithPadding {
                OrView()
            }

            WithPadding {
                SearchByUrlTextField(
                    accountColor = accountColor,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
private inline fun WithPadding(content: @Composable () -> Unit) {
    Box(Modifier.padding(horizontal = 16.dp)) {
        content()
    }
}