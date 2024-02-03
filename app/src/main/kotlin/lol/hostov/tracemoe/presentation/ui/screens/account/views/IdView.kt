package lol.hostov.tracemoe.presentation.ui.screens.account.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.utils.generateColorFromSource
import lol.hostov.tracemoe.R
import lol.hostov.tracemoe.presentation.theme.LocalAccount

@Composable
fun IdView() {
    val context = LocalContext.current
    val account = LocalAccount.current
    var idIsHidden by rememberSaveable { mutableStateOf(false) }
    val accountColorStart by animateColorAsState(
        targetValue = generateColorFromSource(
            source = "${account?.id}",
            saturation = 0.75f,
            lightness = 0.4f
        ),
        label = "accountColorStart"
    )
    val accountColorEnd by animateColorAsState(
        targetValue = generateColorFromSource(
            source = "${account?.id}",
            saturation = 1f,
            lightness = 0.3f
        ),
        label = "accountColorEnd"
    )

    fun copyId() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("ID", "${account?.id}")

        clipboard.setPrimaryClip(clipData)
        Toast.makeText(context, context.getString(R.string.screen_account_idCopied), Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        accountColorStart,
                        accountColorEnd
                    )
                )
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color.White),
                onClick = ::copyId
            )
            .statusBarsPadding()
            .padding(16.dp)
    ) {


        Text(
            text = if (idIsHidden) {
                numRegex.replace("${account?.id}", "X")
            } else {
                "${account?.id}"
            },
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { idIsHidden = !idIsHidden }
            )
        )

        Text(
            text = stringResource(R.string.screen_account_yourId),
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )
    }
}

private val numRegex = Regex("[0-9]")