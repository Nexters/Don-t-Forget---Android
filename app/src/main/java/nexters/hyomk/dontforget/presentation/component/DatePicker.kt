package nexters.hyomk.dontforget.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.YearMonth

@Composable
fun CustomDatePicker(
    year: PickerState,
    month: PickerState,
    day: PickerState,

) {
    val today = LocalDateTime.now()
    var lastDay by remember { mutableIntStateOf(getLastDay(today.monthValue, today.year)) }
    val years = remember { (1900..2050).toList() }
    val months = remember { (1..12).toList() }

    var debounceJob by remember { mutableStateOf<Job?>(null) }
    val debouncePeriod = 200L

    LaunchedEffect(year.selectedItem, month.selectedItem) {
        debounceJob?.cancel()
        debounceJob = launch {
            delay(debouncePeriod)
            val newLastDay = getLastDay(month.selectedItem, year.selectedItem)
            if (lastDay != newLastDay) {
                lastDay = newLastDay
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Picker(
            state = year,
            items = years,
            unit = "년",
            modifier = Modifier.weight(0.1f),
            visibleItemsCount = 3,
            startIndex = years.indexOf(today.year),
            textModifier = Modifier.padding(17.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
        )
        Picker(
            state = month,
            items = months,
            unit = "월",
            modifier = Modifier.weight(0.1f),
            visibleItemsCount = 3,
            startIndex = months.indexOf(today.monthValue),
            textModifier = Modifier.padding(17.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
        )
        Picker(
            state = day,
            items = (1..lastDay).toList(),
            unit = "일",
            modifier = Modifier.weight(0.1f),
            visibleItemsCount = 3,
            startIndex = (1..lastDay).indexOf(day.selectedItem),
            textModifier = Modifier.padding(17.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
        )
    }
}

fun getLastDay(month: Int, year: Int): Int {
    return YearMonth.of(year, month).lengthOfMonth()
}

@Preview
@Composable
fun PreviewDatePicker() {
    val year by remember {
        mutableStateOf(PickerState(2025))
    }
    val month by remember {
        mutableStateOf(PickerState(1))
    }
    val day by remember {
        mutableStateOf(PickerState(20))
    }
    Column {
        CustomDatePicker(
            year = year,
            month = month,
            day = day,
        )
        Text(text = "${year.selectedItem} / ${month.selectedItem} / ${day.selectedItem}")
    }
}
