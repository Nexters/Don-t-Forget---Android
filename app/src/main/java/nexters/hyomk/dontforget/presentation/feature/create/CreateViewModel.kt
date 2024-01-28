package nexters.hyomk.dontforget.presentation.feature.create

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
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor() : ViewModel() {
    private val today = LocalDateTime.now()
    private val _uiState = MutableStateFlow(
        CreateUiState(
            year = today.year,
            month = today.monthValue,
            day = today.dayOfMonth,
        ),
    )
    val uiState get() = _uiState

    private val _events = MutableSharedFlow<CreateEvent>()
    val events get() = _events

    fun updateName(s: String) {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(name = s))
        }
    }

    fun updateMemo(s: String) {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(memo = s))
        }
    }

    fun updateDate(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(year = year, month = month, day = day))
        }
    }

    fun updateAlarmSchedule(schedules: List<AlarmSchedule>) {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(alarms = schedules))
        }
    }

    fun updateDateType(type: AnniversaryDateType) {
        viewModelScope.launch {
            _uiState.emit(uiState.value.copy(dateType = type))
        }
    }

    fun onClickSubmit(year: Int, month: Int, day: Int) {
        // updateDate(year, month, day)
        Timber.d(uiState.value.toString())
    }
}

data class CreateUiState(
    val name: String = "",
    val memo: String = "",
    val year: Int,
    val month: Int,
    val day: Int,
    val alarms: List<AlarmSchedule> = listOf(),
    val dateType: AnniversaryDateType = AnniversaryDateType.Solar,
)

sealed class CreateEvent {
    object Success : CreateEvent()
    object Fail : CreateEvent()
    object Loading : CreateEvent()
}
