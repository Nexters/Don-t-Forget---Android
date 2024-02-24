package nexters.hyomk.dontforget.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nexters.hyomk.dontforget.presentation.utils.NoRippleTheme
import nexters.hyomk.dontforget.ui.theme.White

@Composable
fun BaseIconButton(
    modifier: Modifier = Modifier,
    icon: Int,
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = modifier.wrapContentSize(),
            color = (if (isPressed) White.copy(alpha = 0.1f) else Color.Transparent),
            interactionSource = interactionSource,
            onClick = onClick,
        ) {
            Box(modifier = modifier.padding(12.dp).background(Color.Transparent), contentAlignment = Alignment.Center) {
                Icon(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(24.dp), tint = White)
            }
        }
    }
}
