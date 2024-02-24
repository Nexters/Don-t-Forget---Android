package nexters.hyomk.dontforget.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nexters.hyomk.dontforget.presentation.utils.pixelsToDp
import nexters.hyomk.dontforget.ui.theme.Gray500
import nexters.hyomk.dontforget.ui.theme.Gray800
import nexters.hyomk.dontforget.ui.theme.Gray900
import nexters.hyomk.dontforget.ui.theme.White

@Composable
fun DateTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(indicatorWidth)
            .offset(x = indicatorOffset)
            .clip(RoundedCornerShape(8.dp))
            .background(indicatorColor),
    )
}

@Composable
fun DateTabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            White
        } else {
            Gray500
        },
        animationSpec = tween(easing = LinearEasing),
        label = "",
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable {
                onClick()
            }.fillMaxWidth()
            .padding(18.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 15.sp,
            color = tabTextColor,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CustomDateTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    onClick: (index: Int) -> Unit,
) {
    val density = LocalDensity.current
    var tabWidth by remember { mutableStateOf(0.dp) }
    val tabPadding = 6.dp

    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing),
        label = "",
    )

    Box(
        modifier = modifier.onGloballyPositioned {
            tabWidth = with(density) {
                it.size.width.toDp() / items.size - tabPadding
            }
        }.clip(RoundedCornerShape(12.dp))
            .background(Gray800)
            .height(intrinsicSize = IntrinsicSize.Min),
    ) {
        Box(modifier = Modifier.padding(tabPadding)) {
            DateTabIndicator(
                indicatorWidth = tabWidth,
                indicatorOffset = indicatorOffset,
                indicatorColor = Gray900,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                DateTabItem(
                    modifier = Modifier.weight(1f),
                    isSelected = isSelected,
                    onClick = {
                        onClick(index)
                    },
                    text = text,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDateTab() {
    val (selected, setSelected) = remember {
        mutableStateOf(0)
    }

    CustomDateTab(
        items = listOf("MUSIC", "PODCAST"),
        selectedItemIndex = selected,
        onClick = setSelected,
        modifier = Modifier.fillMaxWidth(),
    )
}
