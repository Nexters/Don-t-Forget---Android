package nexters.hyomk.dontforget.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nexters.hyomk.dontforget.R
import nexters.hyomk.dontforget.ui.theme.Gray600
import nexters.hyomk.dontforget.ui.theme.Gray800
import nexters.hyomk.dontforget.ui.theme.Gray900
import nexters.hyomk.dontforget.ui.theme.Primary500
import nexters.hyomk.dontforget.ui.theme.Red500
import nexters.hyomk.dontforget.ui.theme.White

@Composable
fun BaseAlertDialog(
    title: String,
    content: String,
    icon: Int? = null,
    left: String,
    right: String,
    isWarning: Boolean = false,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(
                color = Gray900,
                shape = RoundedCornerShape(12.dp),
            )
            .width(300.dp)
            .clip(RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 32.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = icon ?: R.drawable.baseline_error_24),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp),
            )
            Text(
                modifier = Modifier.padding(vertical = 24.dp),
                text = title,
                textAlign = TextAlign.Center,

                style = MaterialTheme.typography.titleSmall.copy(color = White),
            )
            Text(
                text = content,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall.copy(color = Gray600),
            )
        }

        Divider(color = Gray800)
        Row(
            modifier = Modifier,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(150.dp)
                    .height(51.dp)
                    .clickable {
                        onClickLeft()
                    },
            ) {
                Text(
                    text = left,
                    style = MaterialTheme.typography.bodySmall.copy(color = Gray600),
                )
            }
            Divider(
                color = Gray800,
                modifier = Modifier
                    .height(51.dp)
                    .width(1.dp),
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(150.dp)
                    .height(51.dp)
                    .clickable {
                        onClickRight()
                    },
            ) {
                Text(
                    text = right,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (isWarning) Red500 else Primary500,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialog() {
    BaseAlertDialog(
        title = "기념일 만들기 취소?",
        content = "ㅇㅇㅇㅇㅇㅇ",
        left = "닫기",
        right = "취소",
        onClickLeft = { /*TODO*/ },
        onClickRight = {},
        icon = null,
    )
}
