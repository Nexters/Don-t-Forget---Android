package nexters.hyomk.dontforget.presentation.feature.create

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import nexters.hyomk.domain.model.AlarmSchedule
import nexters.hyomk.domain.model.AnniversaryDateType
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState", "UnrememberedMutableState")
@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: CreateViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    val year by remember { mutableStateOf(PickerState(uiState.year)) }

    val month by remember { mutableStateOf(PickerState(uiState.month)) }

    val day by remember { mutableStateOf(PickerState(uiState.day)) }

    val guide = GuideCompositionLocal.current

    LaunchedEffect(uiState.dateType) {
        viewModel.updateDate(year.selectedItem, month.selectedItem, day.selectedItem)
    }

    Scaffold(
        modifier = modifier.imePadding(),
        containerColor = Gray900,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
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
            Column {
                Box(modifier = Modifier.padding(20.dp).padding(bottom = 16.dp)) {
                    BaseButton(
                        text = guide.complete,
                        onClick = {
                            viewModel.onClickSubmit(year.selectedItem, month.selectedItem, day.selectedItem)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        },
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(20.dp)
                .consumeWindowInsets(it)
                .systemBarsPadding(),
        ) {
            item {
                AnniversaryNameTextField(guide = guide, text = uiState.name, onValueChange = viewModel::updateName)

                AnniversaryDatePicker(guide, year, month, day, viewModel::updateDateType)

                AnniversaryNotification(
                    guide = guide,
                    alarms = uiState.alarms,
                    onClickChip = {
                        val temp = ArrayList(uiState.alarms)
                        if (temp.contains(it)) {
                            temp.remove(it)
                        } else {
                            temp.add(it)
                        }
                        viewModel.updateAlarmSchedule(temp)
                    },
                )

                AnniversaryMemoTextField(guide = guide, text = uiState.memo, onValueChange = viewModel::updateMemo)
            }
        }
    }
}

@Composable
fun AnniversaryMemoTextField(
    guide: TransGuide,
    text: String,
    onValueChange: (String) -> Unit,
) {
    Text(text = guide.memoTitle, style = MaterialTheme.typography.titleSmall, color = White, modifier = Modifier.padding(top = 48.dp, bottom = 32.dp))
    BaseTextField(value = text, onValueChange = onValueChange, hint = guide.memoHint)
}

@Composable
fun AnniversaryDatePicker(
    guide: TransGuide,
    year: PickerState,
    month: PickerState,
    day: PickerState,
    onValueChange: (AnniversaryDateType) -> Unit,
) {
    Row(modifier = Modifier.padding(top = 48.dp)) {
        Text(text = guide.dateTitle, style = MaterialTheme.typography.titleSmall, color = White)
        Text(text = " *", style = MaterialTheme.typography.titleSmall, color = Pink500)
    }

    val (selected, setSelected) = remember { mutableStateOf(0) }

    Box(modifier = Modifier.padding(vertical = 32.dp)) {
        CustomDateTab(
            items = listOf(guide.solarTabTitle, guide.lunarTabTitle),
            selectedItemIndex = selected,
            onClick = { index ->
                if (index == 0) {
                    onValueChange(AnniversaryDateType.Solar)
                } else {
                    onValueChange(AnniversaryDateType.Lunar)
                }
                setSelected(index)
            },
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
    text: String,
    onValueChange: (String) -> Unit,
) {
    Row(modifier = Modifier.padding(bottom = 32.dp)) {
        Text(text = guide.anniversaryTitle, style = MaterialTheme.typography.titleSmall, color = White)
        Text(text = " *", style = MaterialTheme.typography.titleSmall, color = Pink500)
    }
    BaseTextField(value = text, onValueChange = onValueChange, hint = guide.createHint)
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
