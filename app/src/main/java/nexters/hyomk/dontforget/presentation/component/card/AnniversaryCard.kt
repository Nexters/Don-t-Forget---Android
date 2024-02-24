package nexters.hyomk.dontforget.presentation.component.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nexters.hyomk.domain.model.AnniversaryCardType
import nexters.hyomk.domain.utils.toFormatString
import nexters.hyomk.dontforget.presentation.utils.conditional
import nexters.hyomk.dontforget.presentation.utils.createGradientBrush
import nexters.hyomk.dontforget.ui.theme.Gray900
import java.util.Calendar

@Composable
fun AnniversaryCard(
    properties: CardProperties,
    modifier: Modifier = Modifier,
    title: String = "",
    date: Calendar = Calendar.getInstance(),
    onClick: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val today = Calendar.getInstance()
    val dday = (today.time.time - date.time.time) / (60 * 60 * 24 * 1000)

    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = animateFloatAsState(if (isPressed) 0.96f else 1f, label = "")

    Surface(
        interactionSource = interactionSource,
        onClick = onClick,
        modifier = modifier
            .background(Gray900)
            .aspectRatio(ratio = 1f)
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value,
            ),
        color = Gray900,
        contentColor = Gray900,
        shape = RoundedCornerShape(16.dp),

    ) {
        Image(
            painter = painterResource(id = properties.background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(properties.backgroundColor)
                .conditional(
                    properties.type == AnniversaryCardType.LUNAR,
                ) {
                    background(createGradientBrush(listOf(Color(0xFF181E23), Color(0xFF1E2830)), true))
                }.clip(
                    shape = RoundedCornerShape(16.dp),
                ),
        )
        Column(
            modifier.padding(end = 19.dp, start = 19.dp, bottom = 20.dp, top = 22.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title,
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall.copy(color = properties.titleColor),
            )
            Text(
                text = if (dday == 365L || dday == 0L) "D-DAY" else "D$dday",
                style = MaterialTheme.typography.headlineMedium.copy(color = properties.dDayColor, fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = date.toFormatString().substring(2),
                modifier = Modifier.padding(top = 12.dp),
                style = MaterialTheme.typography.titleSmall.copy(color = properties.dateColor.copy(alpha = 0.5f), fontWeight = FontWeight.Medium),
            )
        }
    }
}

@Preview
@Composable
fun PreviewAnniversaryCard() {
    val calendar = Calendar.getInstance()
    calendar.set(2024, 1, 24)

    val calendar2 = Calendar.getInstance()
    calendar2.set(2024, 1, 24)

    AnniversaryCard(
        properties = BTypeCard(),
        title = "생일이다",
    )
}
