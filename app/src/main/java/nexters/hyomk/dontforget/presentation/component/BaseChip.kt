package nexters.hyomk.dontforget.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nexters.hyomk.dontforget.presentation.utils.noRippleClickable
import nexters.hyomk.dontforget.ui.theme.Gray400
import nexters.hyomk.dontforget.ui.theme.Gray700
import nexters.hyomk.dontforget.ui.theme.Primary500
import nexters.hyomk.dontforget.ui.theme.White

@Composable
fun BaseChip(
    isSelected: Boolean,
    onClick: (String) -> Unit = { text -> },
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(fontSize = 15.sp),
    modifier: Modifier = Modifier,
) {
    val selectModifier = modifier
        .clip(shape = RoundedCornerShape(200.dp))
        .background(if (isSelected) Primary500 else Gray700)
        .padding(horizontal = 15.dp)
        .height(38.dp)

    Column {
        Box(
            modifier = selectModifier.noRippleClickable {
                onClick(text)
            },
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text, maxLines = 1, style = textStyle, color = if (isSelected) White else Gray400)
        }
    }
}

@Preview
@Composable
fun PreviewBaseChip() {
    BaseChip(isSelected = false, text = "sample")
}
