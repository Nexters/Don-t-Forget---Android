package nexters.hyomk.dontforget.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nexters.hyomk.domain.model.AnniversaryDateType
import nexters.hyomk.dontforget.ui.theme.Gray900
import timber.log.Timber
import java.time.YearMonth

@Composable
fun CustomDatePicker(
    dateType: AnniversaryDateType,
    yearPickerState: PickerState,
    monthPickerState: PickerState,
    dayPickerState: PickerState,
    yInit: Int,
    mInit: Int,
    dInit: Int,
) {
    fun getLastDay(month: Int, year: Int): Int {
        val lastDay = YearMonth.of(year, month).lengthOfMonth()
        return if (dateType == AnniversaryDateType.LUNAR && lastDay > 30) {
            30
        } else {
            YearMonth.of(year, month).lengthOfMonth()
        }
    }

    val years = (1900..2050).toList()
    val months = (1..12).toList()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray900),
    ) {
        var lastDay by remember {
            mutableIntStateOf(
                getLastDay(monthPickerState.selectedItem, yearPickerState.selectedItem),
            )
        }

        LaunchedEffect(yearPickerState.selectedItem, monthPickerState.selectedItem) {
            val newLastDay = getLastDay(monthPickerState.selectedItem, yearPickerState.selectedItem)
            Timber.d("lastDay $newLastDay , ${yearPickerState.selectedItem} - ${monthPickerState.selectedItem} / $yInit - $mInit - $dInit")
            if (lastDay != newLastDay) {
                lastDay = newLastDay
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Picker(
                state = yearPickerState,
                items = years,
                unit = "년",
                modifier = Modifier.weight(1f),
                visibleItemsCount = 3,
                startIndex = years.indexOf(yearPickerState.selectedItem),
                textModifier = Modifier.padding(vertical = 17.dp),
                textStyle = MaterialTheme.typography.bodyLarge,
                initState = yInit,
            )
            Picker(
                state = monthPickerState,
                items = months,
                unit = "월",
                modifier = Modifier.weight(1f),
                visibleItemsCount = 3,
                startIndex = months.indexOf(monthPickerState.selectedItem),
                textModifier = Modifier.padding(vertical = 17.dp),
                textStyle = MaterialTheme.typography.bodyLarge,
                initState = mInit,
            )
            Picker(
                state = dayPickerState,
                items = (1..lastDay).toList(),
                unit = "일",
                modifier = Modifier.weight(1f),
                visibleItemsCount = 3,
                startIndex = (1..lastDay).indexOf(dayPickerState.selectedItem),
                textModifier = Modifier.padding(vertical = 17.dp),
                textStyle = MaterialTheme.typography.bodyLarge,
                isDayPicker = true,
                initState = dInit,
            )
        }
    }
}

@Composable
@Preview
fun PreviewDatePicker() {
    val year by remember {
        mutableStateOf(PickerState(1980))
    }
    val month by remember {
        mutableStateOf(PickerState(1))
    }
    val day by remember {
        mutableStateOf(PickerState(1))
    }
    CustomDatePicker(
        dateType = AnniversaryDateType.LUNAR,
        yearPickerState = year,
        monthPickerState = month,
        dayPickerState = day,
        yInit = 2025,
        mInit = 1,
        dInit = 1,
    )
}
