package nexters.hyomk.dontforget.presentation.feature.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nexters.hyomk.domain.model.AlarmSchedule
import nexters.hyomk.domain.model.AnniversaryDateType
import timber.log.Timber
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor() : ViewModel() {
    private val today = LocalDateTime.now()
    private val _uiState = MutableStateFlow<EditUiState>(
        EditUiState.Loading,
    )
    val uiState get() = _uiState

    private val _events = MutableSharedFlow<CreateEvent>()
    val events get() = _events

    var _year = 0
    var _day = 0
    var _month = 0

    suspend fun getAnniversaryDetail() {
        _uiState.emit(
            EditUiState.Success(
                eventId = 1L,
                title = "내 싱일인데",
                solarDate = Calendar.getInstance().apply { set(2024, 1, 1) },
                lunarDate = Calendar.getInstance().apply { set(2024, 2, 4) },
                content = "선물주세요",
                type = AnniversaryDateType.Solar,
                alarmSchedule = listOf(AlarmSchedule.DDay),
            ),
        )
    }

    fun updateName(s: String) {
        if (uiState.value is EditUiState.Success) {
            viewModelScope.launch {
                _uiState.emit((uiState.value as EditUiState.Success).copy(title = s))
            }
        }
    }

    fun updateMemo(s: String) {
        if (uiState.value is EditUiState.Success) {
            viewModelScope.launch {
                _uiState.emit((uiState.value as EditUiState.Success).copy(content = s))
            }
        }
    }

    fun updateDate(year: Int, month: Int, day: Int) {
        _year = year
        _month = month
        _day = day
    }

    fun updateAlarmSchedule(schedules: List<AlarmSchedule>) {
        if (uiState.value is EditUiState.Success) {
            viewModelScope.launch {
                _uiState.emit((uiState.value as EditUiState.Success).copy(alarmSchedule = schedules))
            }
        }
    }

    fun updateDateType(type: AnniversaryDateType) {
        if (uiState.value is EditUiState.Success) {
            viewModelScope.launch {
                _uiState.emit((uiState.value as EditUiState.Success).copy(type = type))
            }
        }
    }

    fun onClickSubmit(year: Int, month: Int, day: Int) {
        // updateDate(year, month, day)
        Timber.d(uiState.value.toString())
    }
}

data class EditContent(
    val eventId: Long,
    val title: String,
    val lunarDate: Calendar,
    val solarDate: Calendar,
    val alarmSchedule: List<AlarmSchedule>,
    val content: String,
    val type: AnniversaryDateType,
)

sealed class EditUiState {
    object Loading : EditUiState()
    data class Success(
        val eventId: Long,
        val title: String,
        val lunarDate: Calendar,
        val solarDate: Calendar,
        val alarmSchedule: List<AlarmSchedule>,
        val content: String,
        val type: AnniversaryDateType,
    ) : EditUiState()

    object Fail : EditUiState()
}

sealed class CreateEvent {
    object OnSubmitSuccess : CreateEvent()
    object OnSubmitFail : CreateEvent()
}
