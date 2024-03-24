package nexters.hyomk.dontforget.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nexters.hyomk.dontforget.presentation.utils.pixelsToDp
import nexters.hyomk.dontforget.ui.theme.Gray500
import nexters.hyomk.dontforget.ui.theme.Gray600
import nexters.hyomk.dontforget.ui.theme.Gray800
import nexters.hyomk.dontforget.ui.theme.Primary500
import java.time.LocalDateTime
import java.time.YearMonth

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Picker(
    items: List<Int>,
    unit: String,
    state: PickerState,
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    textModifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    dividerColor: Color = Gray800,
    isDayPicker: Boolean = false,
    initState: Int = items.indexOf(state.selectedItem),
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    var listStartIndex by remember { mutableIntStateOf(listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex) }

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    var isLaunched by remember { mutableStateOf(false) }

    val haptic = LocalHapticFeedback.current

    suspend fun initListState() {
        snapshotFlow { Triple(listState.firstVisibleItemIndex, startIndex, items.size) }.map { (index, offset) ->
            getItem((index + visibleItemsMiddle) % items.size)
        }.distinctUntilChanged().collect { item ->
            state.selectedItem = item
        }
    }

    suspend fun scrollToSelectItem() {
        val newStartIndex = listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex
        if (newStartIndex != listStartIndex) {
            listStartIndex = newStartIndex
            listState.scrollToItem(newStartIndex)
        }
    }

    if (isDayPicker) {
        LaunchedEffect(items.size, initState) {
            isLaunched = false
            scrollToSelectItem()
        }
        LaunchedEffect(listState, items.size) {
            initListState()
        }
    } else {
        LaunchedEffect(initState) {
            isLaunched = false
            scrollToSelectItem()
        }

        LaunchedEffect(listState) {
            initListState()
        }
    }

    LaunchedEffect(state.selectedItem) {
        if (isLaunched) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        } else {
            isLaunched = true
        }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(itemHeightDp * visibleItemsCount).offset(x = 20.dp)
                .width(50.dp),
        ) {
            items(listScrollCount) { index ->
                val isSelectedItem = state.selectedItem == getItem(index)

                val isSelectedColor = if (isSelectedItem) Primary500 else Gray600
                val item = getItem(index).toString()

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = if (unit == "년") item.substring(2, 4) else item,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = textStyle.plus(
                            TextStyle(color = isSelectedColor),
                        ),
                        modifier = Modifier
                            .onSizeChanged { size ->
                                if (size.height != itemHeightPixels.value) itemHeightPixels.value = size.height
                            }
                            .then(textModifier),

                    )
                }
            }
        }

        Divider(
            color = dividerColor,
            modifier = Modifier.offset(y = itemHeightDp * visibleItemsMiddle),
        )

        Text(
            text = unit,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(color = Gray500),
            modifier = Modifier
                .align(Alignment.Center)
                .heightIn(textStyle.fontSize.value.dp, itemHeightDp)
                .offset(x = textStyle.fontSize.value.dp),
        )

        Divider(
            color = dividerColor,
            modifier = Modifier.offset(y = itemHeightDp * (visibleItemsMiddle + 1)),
        )
    }
}

@Composable
fun rememberPickerState(value: Int) = remember { PickerState(value) }

class PickerState(value: Int) {
    var selectedItem by mutableStateOf(value)
}

@Preview
@Composable
fun PickerExample() {
    fun getLastDay(month: Int, year: Int): Int {
        return YearMonth.of(year, month).lengthOfMonth()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp),
        ) {
            val today = LocalDateTime.now()
            var lastDay by remember { mutableIntStateOf(getLastDay(today.monthValue, today.year)) }
            val dayPickerState = rememberPickerState(today.dayOfMonth)
            val years = remember { (1900..2050).toList() }
            val yearPickerState = rememberPickerState(today.year)
            val months = remember { (1..12).toList() }
            val monthPickerState = rememberPickerState(today.monthValue)

            var debounceJob by remember { mutableStateOf<Job?>(null) }
            val debouncePeriod = 200L

            LaunchedEffect(yearPickerState.selectedItem, monthPickerState.selectedItem) {
                debounceJob?.cancel()
                debounceJob = launch {
                    delay(debouncePeriod)
                    val newLastDay = getLastDay(monthPickerState.selectedItem, yearPickerState.selectedItem)
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
                    state = yearPickerState,
                    items = years,
                    unit = "년",
                    modifier = Modifier.weight(0.1f),
                    visibleItemsCount = 3,
                    startIndex = years.indexOf(today.year),
                    textModifier = Modifier.padding(vertical = 17.dp),
                    textStyle = MaterialTheme.typography.bodyLarge,
                )
                Picker(
                    state = monthPickerState,
                    items = months,
                    unit = "월",
                    modifier = Modifier.weight(0.1f),
                    visibleItemsCount = 3,
                    startIndex = months.indexOf(today.monthValue),
                    textModifier = Modifier.padding(vertical = 17.dp),
                    textStyle = MaterialTheme.typography.bodyLarge,
                )
                Picker(
                    state = dayPickerState,
                    items = (1..lastDay).toList(),
                    unit = "일",
                    modifier = Modifier.weight(0.1f),
                    visibleItemsCount = 3,
                    startIndex = (1..lastDay).indexOf(dayPickerState.selectedItem),
                    textModifier = Modifier.padding(vertical = 17.dp),
                    textStyle = MaterialTheme.typography.bodyLarge,
                )
            }

            Text(
                text = "${yearPickerState.selectedItem}.${monthPickerState.selectedItem}. ${dayPickerState.selectedItem}",
                modifier = Modifier.padding(vertical = 16.dp),
            )
        }
    }
}
