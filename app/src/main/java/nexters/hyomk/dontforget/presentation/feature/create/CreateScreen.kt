package nexters.hyomk.dontforget.presentation.feature.create

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import nexters.hyomk.domain.model.AlarmSchedule
import nexters.hyomk.dontforget.presentation.component.BaseButton
import nexters.hyomk.dontforget.presentation.component.BaseChip
import nexters.hyomk.dontforget.presentation.component.BaseTextField
import nexters.hyomk.dontforget.presentation.component.CustomDatePicker
import nexters.hyomk.dontforget.presentation.component.CustomDateTab
import nexters.hyomk.dontforget.presentation.component.PickerState
import nexters.hyomk.dontforget.presentation.compositionlocal.GuideCompositionLocal
import nexters.hyomk.dontforget.ui.language.TransGuide
import nexters.hyomk.dontforget.ui.theme.Gray600
import nexters.hyomk.dontforget.ui.theme.Gray900
import nexters.hyomk.dontforget.ui.theme.Pink500
import nexters.hyomk.dontforget.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState", "UnrememberedMutableState")
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
) {
    val text = remember { mutableStateOf("") }

    val name = remember { mutableStateOf("") }

    val memo = remember { mutableStateOf("") }

    val year by remember { mutableStateOf(PickerState(2025)) }

    val month by remember { mutableStateOf(PickerState(1)) }

    val day by remember { mutableStateOf(PickerState(20)) }

    val guide = GuideCompositionLocal.current

    val alarms = mutableStateListOf<AlarmSchedule>()

    Scaffold(
        containerColor = Gray900,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Gray900),
                title = { Text(text = guide.createTitle, style = MaterialTheme.typography.titleMedium, color = White) },
                actions = {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = guide.save, style = MaterialTheme.typography.titleSmall, color = Pink500)
                    }
                },
                navigationIcon = {
                    TextButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Text(
                            text = guide.cancel,
                            style = MaterialTheme.typography.titleSmall,
                            color = Gray600,
                        )
                    }
                },

            )
        },
        bottomBar = {
            Box(modifier = Modifier.padding(20.dp).padding(bottom = 16.dp)) {
                BaseButton(text = guide.complete, onClick = {}, modifier = Modifier.fillMaxWidth())
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(20.dp)
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
        ) {
            item {
                AnniversaryNameTextField(guide = guide, text = name)

                AnniversaryDatePicker(guide, year, month, day)

                AnniversaryNotification(
                    guide = guide,
                    alarms = alarms.toList(),
                    onClickChip = {
                        if (alarms.contains(it)) {
                            alarms.remove(it)
                        } else {
                            alarms.add(it)
                        }
                    },
                )

                AnniversaryMemoTextField(guide = guide, text = memo)
            }
        }
    }
}

@Composable
fun AnniversaryMemoTextField(
    guide: TransGuide,
    text: MutableState<String>,
) {
    Text(text = guide.memoTitle, style = MaterialTheme.typography.titleSmall, color = White, modifier = Modifier.padding(top = 48.dp, bottom = 32.dp))
    BaseTextField(value = text.value, onValueChange = { s -> text.value = s }, hint = guide.memoHint)
}

@Composable
fun AnniversaryDatePicker(
    guide: TransGuide,
    year: PickerState,
    month: PickerState,
    day: PickerState,
) {
    Row(modifier = Modifier.padding(top = 48.dp)) {
        Text(text = guide.dateTitle, style = MaterialTheme.typography.titleSmall, color = White)
        Text(text = " *", style = MaterialTheme.typography.titleSmall, color = Pink500)
    }

    val (selected, setSelected) = remember {
        mutableStateOf(0)
    }
    Box(modifier = Modifier.padding(vertical = 32.dp)) {
        CustomDateTab(
            items = listOf(guide.solarTabTitle, guide.lunarTabTitle),
            selectedItemIndex = selected,
            onClick = setSelected,
            modifier = Modifier.fillMaxWidth(),
        )
    }

    CustomDatePicker(
        year = year,
        month = month,
        day = day,
    )
}

@Composable
fun AnniversaryNameTextField(
    guide: TransGuide,
    text: MutableState<String>,
) {
    Row(modifier = Modifier.padding(bottom = 32.dp)) {
        Text(text = guide.anniversaryTitle, style = MaterialTheme.typography.titleSmall, color = White)
        Text(text = " *", style = MaterialTheme.typography.titleSmall, color = Pink500)
    }
    BaseTextField(value = text.value, onValueChange = { s -> text.value = s }, hint = guide.createHint)
}

@Composable
fun AnniversaryNotification(
    guide: TransGuide,
    alarms: List<AlarmSchedule>,
    onClickChip: (AlarmSchedule) -> Unit,
) {
    Text(text = guide.notificationTitle, style = MaterialTheme.typography.titleSmall, color = White, modifier = Modifier.padding(top = 48.dp))

    LazyRow(modifier = Modifier.padding(vertical = 30.dp)) {
        item {
            AlarmSchedule.values().map {
                BaseChip(
                    text = guide.transNotificationPeriod(it),
                    onClick = { _ ->
                        onClickChip(it)
                    },
                    isSelected = alarms.contains(it),
                    modifier = Modifier.padding
                        (end = 8.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCreateScreen() {
    CreateScreen(modifier = Modifier.background(color = Gray900), navHostController = rememberNavController())
}
