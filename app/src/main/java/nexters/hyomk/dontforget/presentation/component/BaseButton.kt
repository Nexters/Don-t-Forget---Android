package nexters.hyomk.dontforget.presentation.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nexters.hyomk.dontforget.presentation.utils.NoRippleTheme
import nexters.hyomk.dontforget.ui.theme.Gray400
import nexters.hyomk.dontforget.ui.theme.Primary500
import nexters.hyomk.dontforget.ui.theme.Primary600
import nexters.hyomk.dontforget.ui.theme.White

@Composable
fun BaseButton(
    text: String,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: () -> Unit,
    shape: Shape = RectangleShape,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (!enabled) {
        Gray400
    } else if (isPressed) {
        Primary600
    } else {
        Primary500
    }

    CompositionLocalProvider(
        LocalRippleTheme provides NoRippleTheme, // 버튼의 리플 이펙트 제거
    ) {
        Surface(
            modifier = modifier,
            onClick = { if (enabled) onClick() },
            shape = shape,
            color = color,
            interactionSource = interactionSource,
        ) {
            Row(
                modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = White,
                    style = textStyle.copy(fontSize = 20.sp),
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewBaseButton() {
    var enabled by remember { mutableStateOf(false) }
    BaseButton("text", onClick = { enabled = !enabled }, enabled = true, modifier = Modifier)
}
