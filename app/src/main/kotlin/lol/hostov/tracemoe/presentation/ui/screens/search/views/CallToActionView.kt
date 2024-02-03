package lol.hostov.tracemoe.presentation.ui.screens.search.views

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.ui.screens.search.SearchViewModel
import lol.hostov.tracemoe.presentation.ui.shapes.MaterialStarShape

@Composable
fun CallToActionView(
    accountColor: Color,
    viewModel: SearchViewModel
) {
    val transition = rememberInfiniteTransition("infinite transition")
    val screenState by viewModel.screenState.collectAsState()
    val searchBoxDegrees by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                easing = LinearEasing,
                durationMillis = 5000
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "search box degrees"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(modifier = Modifier
                .size(64.dp)
                .rotate(searchBoxDegrees)
                .clip(MaterialStarShape)
                .background(accountColor))

            AnimatedContent(
                targetState = screenState.searchImageInProgress,
                label = ""
            ) { searchImageInProgress ->
                if (searchImageInProgress) {
                    CircularProgressIndicator(
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(40.dp),
                        strokeCap = StrokeCap.Round,
                        color = Color.White
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Text(
            text = stringResource(R.string.screen_account_tapToPickImage),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
    }
}