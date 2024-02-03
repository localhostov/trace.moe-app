package lol.hostov.tracemoe.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lol.hostov.tracemoe.presentation.theme.Theme

@SuppressLint("ModifierParameter")
@Composable
fun Cell(
    title: String,
    caption: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = Theme.colors.text
    ),
    captionStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        color = Theme.colors.textSecondary
    )
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        leadingIcon?.invoke()

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = titleStyle
            )

            caption?.let { text ->
                Text(
                    text = text,
                    style = captionStyle
                )
            }
        }

        trailingIcon?.invoke()
    }
}