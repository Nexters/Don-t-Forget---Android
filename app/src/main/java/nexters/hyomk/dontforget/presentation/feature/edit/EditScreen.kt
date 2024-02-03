package nexters.hyomk.dontforget.presentation.feature.edit

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import nexters.hyomk.domain.model.AlarmSchedule
import nexters.hyomk.domain.model.AnniversaryDateType
import nexters.hyomk.dontforget.presentation.component.BaseAlertDialog
import nexters.hyomk.dontforget.presentation.component.BaseButton
import nexters.hyomk.dontforget.presentation.component.BaseChip
import nexters.hyomk.dontforget.presentation.component.BaseTextField
import nexters.hyomk.dontforget.presentation.component.CustomDatePicker
import nexters.hyomk.dontforget.presentation.component.CustomDateTab
import nexters.hyomk.dontforget.presentation.component.PickerState
import nexters.hyomk.dontforget.presentation.compositionlocal.GuideCompositionLocal
import nexters.hyomk.dontforget.presentation.utils.LunarCalendarUtil
import nexters.hyomk.dontforget.presentation.utils.addFocusCleaner
import nexters.hyomk.dontforget.presentation.utils.isKeyboardVisible
import nexters.hyomk.dontforget.ui.language.TransGuide
import nexters.hyomk.dontforget.ui.theme.Gray600
import nexters.hyomk.dontforget.ui.theme.Gray900
import nexters.hyomk.dontforget.ui.theme.Pink500
import nexters.hyomk.dontforget.ui.theme.White
import timber.log.Timber
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditScreen(
    navHostController: NavHostController = rememberNavController(),
    viewModel: EditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val today = LocalDateTime.now()

    val year by remember { mutableStateOf(PickerState(today.year)) }

    val month by remember { mutableStateOf(PickerState(today.monthValue)) }

    val day by remember { mutableStateOf(PickerState(today.dayOfMonth)) }

    val guide = GuideCompositionLocal.current

    var showDialog by remember { mutableStateOf(false) }

    val scrollState = rememberLazyListState()

    val (scrollEnabled, setScrollEnabled) = remember {
        mutableStateOf(true)
    }
    val focusManager = LocalFocusManager.current

    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.events.collectLatest {
                when (it) {
                    is ModifyEvent.Fail -> {
                    }

                    is ModifyEvent.Success -> {
                        navHostController.popBackStack()
                    }
                }
            }
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = {}) {
            BaseAlertDialog(
                title = guide.createDialogTitle,
                content = guide.createDialogContent,
                left = guide.close,
                right = guide.cancel,
                onClickLeft = { showDialog = false },
                onClickRight = {
                    showDialog = false
                    navHostController.popBackStack()
                },
            )
        }
    }

    BackHandler {
        showDialog = true
    }

    Scaffold(
        modifier = Modifier
            .addFocusCleaner(focusManager)
            .imePadding(),
        containerColor = Gray900,
        contentWindowInsets = WindowInsets(0, 0, 0, 20),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Gray900),
                title = { Text(text = guide.createTitle, style = MaterialTheme.typography.titleMedium, color = White) },

                navigationIcon = {
                    TextButton(
                        onClick = {
                            showDialog = true
                        },
                    ) {
                        Text(
                            text = guide.cancel,
                            style = MaterialTheme.typography.bodySmall,
                            color = Gray600,
                        )
                    }
                },

            )
        },
        bottomBar = {
            if (isKeyboardVisible() && uiState is EditUiState.Success) {
                BaseButton(
                    enabled = (uiState as EditUiState.Success).title.isNotBlank(),
                    text = guide.next,
                    onClick = {
                        focusManager.clearFocus()
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                Column {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 16.dp),
                    ) {
                        BaseButton(
                            enabled = (uiState is EditUiState.Success) && (uiState as EditUiState.Success).title.isNotBlank(),
                            text = guide.complete,
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                viewModel.onClickSubmit(year = year.selectedItem, month = month.selectedItem, day = day.selectedItem)
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                    Spacer(modifier = Modifier.navigationBarsPadding())
                }
            }
        },
    ) { it ->

        when (uiState) {
            is EditUiState.Loading -> {
            }

            is EditUiState.Fail -> {
            }

            is EditUiState.Success -> {
                with(uiState as EditUiState.Success) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(it)
                            .padding(20.dp)
                            .consumeWindowInsets(it)
                            .navigationBarsPadding()
                            .imePadding(),

                    ) {
                        LazyColumn(
                            state = scrollState,
                            userScrollEnabled = scrollEnabled,
                            modifier = Modifier.weight(9f),
                        ) {
                            item {
                                AnniversaryNameTextField(guide = guide, text = this@with.title, onValueChange = viewModel::updateName)

                                AnniversaryDatePicker(
                                    day = day,
                                    month = month,
                                    year = year,
                                    type = this@with.type,
                                    setType = viewModel::updateDateType,
                                    Modifier,
                                    guide,
                                    setScrollEnabled,
                                )

                                nexters.hyomk.dontforget.presentation.feature.create.AnniversaryNotification(
                                    modifier = Modifier.addFocusCleaner(focusManager),
                                    guide = guide,
                                    focusManager = focusManager,
                                    alarms = this@with.alarmSchedule,
                                    onClickChip = {
                                        val temp = ArrayList(this@with.alarmSchedule)
                                        if (temp.contains(it)) {
                                            temp.remove(it)
                                        } else {
                                            temp.add(it)
                                        }
                                        viewModel.updateAlarmSchedule(temp)
                                    },

                                )
                                AnniversaryMemoTextField(
                                    guide = guide,
                                    text = this@with.content,
                                    onValueChange = viewModel::updateMemo,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(bottom = 80.dp),
                                )
                            }

                            //
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnniversaryMemoTextField(
    guide: TransGuide,
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = guide.memoTitle,
            style = MaterialTheme.typography.titleSmall,
            color = White,
            modifier = Modifier.padding(top = 48.dp, bottom = 32.dp),
        )
        BaseTextField(value = text, onValueChange = onValueChange, hint = guide.memoHint)
    }
}

@Composable
fun AnniversaryDatePicker(
    day: PickerState,
    month: PickerState,
    year: PickerState,
    type: AnniversaryDateType,
    setType: (AnniversaryDateType) -> Unit,
    modifier: Modifier,
    guide: TransGuide,
    setScrollEnabled: (Boolean) -> Unit,
) {
    val (selected, setSelected) = remember { mutableStateOf(0) }

    val today = LocalDateTime.now()

    var yInit by remember { mutableIntStateOf(today.year) }

    var mInit by remember { mutableIntStateOf(today.monthValue) }

    var dInit by remember { mutableIntStateOf(today.dayOfMonth) }

    fun convertDate(type: AnniversaryDateType) {
        try {
            val _day = day.selectedItem.toString().padStart(2, '0')
            val _month = month.selectedItem.toString().padStart(2, '0')

            Timber.d("[convert before ${type.name}] ${year.selectedItem}-${month.selectedItem}-${day.selectedItem} : ")

            val newDate = if (type == AnniversaryDateType.Lunar) {
                LunarCalendarUtil.solar2Lunar(
                    "${year.selectedItem}$_month$_day",
                )
            } else {
                LunarCalendarUtil.lunar2Solar(
                    "${year.selectedItem}$_month$_day",
                    true,
                )
            }

            Timber.d("[convert ${type.name}] ${year.selectedItem}-${month.selectedItem}-${day.selectedItem} : $newDate")

            val newYear = newDate.substring(0, 4).toInt()
            val newMonth = newDate.substring(4, 6).toInt()
            val newDay = newDate.substring(6, 8).toInt()

            year.selectedItem = newYear
            month.selectedItem = newMonth
            day.selectedItem = newDay

            yInit = newYear
            mInit = newMonth
            dInit = newDay
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    LaunchedEffect(type.value) {
        Timber.d("$type")
        convertDate(type)
    }

    Row(modifier = Modifier.padding(top = 48.dp)) {
        Text(text = guide.dateTitle, style = MaterialTheme.typography.titleSmall, color = White)
        Text(text = " *", style = MaterialTheme.typography.titleSmall, color = Pink500)
    }

    Column(
        modifier = Modifier.pointerInput(Unit) {
            detectVerticalDragGestures(
                onVerticalDrag = { change, dragAmount -> },
                onDragStart = {
                    setScrollEnabled(false)
                },
                onDragEnd = {
                    setScrollEnabled(true)
                },
                onDragCancel = {},
            )
        },
    ) {
        Box(modifier = modifier.padding(vertical = 32.dp)) {
            CustomDateTab(
                items = listOf(guide.solarTabTitle, guide.lunarTabTitle),
                selectedItemIndex = selected,
                onClick = { index ->
                    if (index == 0) {
                        setType(AnniversaryDateType.Solar)
                    } else {
                        setType(AnniversaryDateType.Lunar)
                    }
                    setSelected(index)
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        CustomDatePicker(
            yearPickerState = year,
            monthPickerState = month,
            dayPickerState = day,
            yInit = yInit,
            mInit = mInit,
            dInit = dInit,
        )
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun AnniversaryNameTextField(
    guide: TransGuide,
    text: String,
    onValueChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Row(modifier = Modifier.padding(bottom = 32.dp)) {
        Text(text = guide.anniversaryTitle, style = MaterialTheme.typography.titleSmall, color = White)
        Text(text = " *", style = MaterialTheme.typography.titleSmall, color = Pink500)
    }
    BaseTextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = text,
        onValueChange = onValueChange,
        hint = guide.createHint,

    )
}

@Composable
fun AnniversaryNotification(
    modifier: Modifier,
    focusManager: FocusManager,
    guide: TransGuide,
    alarms: List<AlarmSchedule>,
    onClickChip: (AlarmSchedule) -> Unit,
) {
    Text(text = guide.notificationTitle, style = MaterialTheme.typography.titleSmall, color = White)

    LazyRow(modifier = modifier.padding(vertical = 30.dp)) {
        item {
            AlarmSchedule.values().map {
                BaseChip(
                    text = guide.transNotificationPeriod(it),
                    onClick = { _ ->
                        onClickChip(it)
                        focusManager.clearFocus()
                    },
                    isSelected = alarms.contains(it),
                    modifier = modifier.padding
                    (end = 8.dp),
                )
            }
        }
    }
}
