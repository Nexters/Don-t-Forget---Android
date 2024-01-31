package nexters.hyomk.dontforget.presentation.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import nexters.hyomk.domain.model.AnniversaryDateType
import nexters.hyomk.domain.model.AnniversaryItem
import nexters.hyomk.domain.model.DetailAnniversary
import nexters.hyomk.domain.utils.calculateDDay
import nexters.hyomk.domain.utils.toFormatString
import nexters.hyomk.dontforget.R
import nexters.hyomk.dontforget.navigation.NavigationItem
import nexters.hyomk.dontforget.presentation.component.BaseIconButton
import nexters.hyomk.dontforget.presentation.component.card.ATypeCard
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailContent(
    navHostController: NavHostController,
    selectedAnniversaryItem: AnniversaryItem?,
    offset: Int = 0,

) {
    val type = ATypeCard()
    val anniversaryItem = DetailAnniversary(
        eventId = 1L,
        title = "음력으로 내 생일",
        content = "가족여행미리",
        lunarDate = Calendar.getInstance().apply { set(2024, 5, 6) },
        solarDate = Calendar.getInstance().apply { set(2024, 6, 7) },
        alarmSchedule = listOf(),
        type = AnniversaryDateType.Lunar,
    )

    fun getDDay(): Long {
        if (selectedAnniversaryItem == null) return 0L
        return if (anniversaryItem.type == AnniversaryDateType.Lunar) {
            calculateDDay(selectedAnniversaryItem!!.lunarDate.time)
        } else {
            calculateDDay(selectedAnniversaryItem!!.solarDate.time)
        }
    }

    val dday by remember { mutableStateOf(getDDay()) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_full),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        if (selectedAnniversaryItem != null) {
            Box(modifier = Modifier, contentAlignment = Alignment.TopCenter) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .padding(horizontal = 36.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight().offset(y = -200.dp),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Text(
                            text = "${selectedAnniversaryItem?.lunarDate?.toFormatString()}",
                            style = MaterialTheme.typography.titleSmall,
                            color = type.dateColor,
                        )

                        Text(text = "D$dday", style = MaterialTheme.typography.headlineLarge, color = type.dDayColor)
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(Modifier.wrapContentHeight(), verticalAlignment = Alignment.CenterVertically) {
                            Divider(
                                modifier = Modifier
                                    .heightIn(min = 51.dp)
                                    .width(2.5.dp),
                                color = type.dDayColor,
                            )
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(
                                    text = selectedAnniversaryItem.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = type.titleColor,
                                    modifier = Modifier
                                        .padding(bottom = 8.dp),
                                )

                                Text(text = "sample", style = MaterialTheme.typography.titleSmall, color = type.dateColor)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.fillMaxHeight().weight(0.4f))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),

                ) {
                    BaseIconButton(icon = R.drawable.ic_back, onClick = { })

                    Row() {
                        BaseIconButton(
                            icon = R.drawable.ic_edit,
                            onClick = {
                                navHostController.navigate(NavigationItem.Create.route)
                            },
                        )
                        BaseIconButton(icon = R.drawable.ic_delete, onClick = { })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDetailScreen() {
    DetailContent(
        rememberNavController(),
        AnniversaryItem(
            eventId = 1L,
            title = "something",
            lunarDate = Calendar.getInstance().apply { set(1999, 1, 3) },
            solarDate = Calendar.getInstance().apply { set(1999, 3, 3) },
        ),
    )
}
