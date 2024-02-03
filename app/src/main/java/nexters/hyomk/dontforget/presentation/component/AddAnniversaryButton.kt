package nexters.hyomk.dontforget.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nexters.hyomk.dontforget.R
import nexters.hyomk.dontforget.ui.theme.Gray400
import nexters.hyomk.dontforget.ui.theme.Gray700
import nexters.hyomk.dontforget.ui.theme.Gray900

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddAnniversaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale = animateFloatAsState(if (isPressed) 0.96f else 1f, label = "")

    Surface(
        interactionSource = interactionSource,
        onClick = onClick,
        modifier = modifier
            .background(Gray900)
            .clip(RoundedCornerShape(16.dp))
            .aspectRatio(ratio = 1f)
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value,
            ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, color = Gray700),

    ) {
        Column(
            modifier
                .background(Gray900),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar_add_on),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .alpha(0.5f),
                tint = Gray400,
            )
            Text(
                text = text,
                modifier = Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.titleSmall.copy(color = Gray400, fontWeight = FontWeight.Normal),
            )
        }
    }
}

@Preview
@Composable
fun PreviewAddButton() {
    AddAnniversaryButton(text = "기념일 만들기", modifier = Modifier.size(300.dp))
}
