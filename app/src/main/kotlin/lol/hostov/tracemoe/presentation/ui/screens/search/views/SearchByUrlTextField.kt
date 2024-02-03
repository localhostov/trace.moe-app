package lol.hostov.tracemoe.presentation.ui.screens.search.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.theme.LocalNavController
import lol.hostov.tracemoe.presentation.theme.Theme
import lol.hostov.tracemoe.presentation.ui.navigation.Routing
import lol.hostov.tracemoe.presentation.ui.screens.search.SearchViewModel

@Composable
fun SearchByUrlTextField(
    accountColor: Color,
    viewModel: SearchViewModel
) {
    val navController = LocalNavController.current
    val state by viewModel.screenState.collectAsState()

    fun onSearch() {
        viewModel.searchByUrl(onSuccess = {
            navController.navigate(Routing.Screen.RESULT)
        })
    }

    TextField(
        value = state.searchQuery,
        onValueChange = viewModel::updateSearchQuery,
        placeholder = {
            Text(
                text = stringResource(R.string.screen_account_searchFieldPlaceholder),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Theme.colors.textSecondary
            )
        },
        shape = CircleShape,
        singleLine = true,
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Theme.colors.card,
            focusedContainerColor = Theme.colors.card,
            unfocusedLeadingIconColor = Theme.colors.textSecondary,
            focusedLeadingIconColor = Theme.colors.textSecondary,
            unfocusedTrailingIconColor = Theme.colors.textSecondary,
            focusedTrailingIconColor = Theme.colors.textSecondary,
            selectionColors = TextSelectionColors(
                handleColor = accountColor,
                backgroundColor = accountColor
            ),
            cursorColor = accountColor
        ),
        leadingIcon = { LeadingIcon(state.searchByUrlInProgress) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Uri,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        ),
        trailingIcon = {
            TrailingIcon(viewModel, isVisible = state.searchQuery.isNotBlank())
        },
    )
}

@Composable
private fun LeadingIcon(searchByUrlInProgress: Boolean) {
    AnimatedContent(
        targetState = searchByUrlInProgress,
        label = "text field icon",
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { searchInProgress ->
        if (searchInProgress) {
            CircularProgressIndicator(
                strokeWidth = 1.dp,
                color = Theme.colors.textSecondary,
                modifier = Modifier.size(16.dp),
                strokeCap = StrokeCap.Round
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.ic_link),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun TrailingIcon(screenModel: SearchViewModel, isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + scaleIn(),
        exit = scaleOut() + fadeOut()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_cross_small),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .clickable { screenModel.updateSearchQuery("") }
        )
    }
}