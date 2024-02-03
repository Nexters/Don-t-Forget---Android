package nexters.hyomk.dontforget.presentation.feature.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nexters.hyomk.domain.model.AlarmSchedule
import nexters.hyomk.domain.model.AnniversaryDateType
import nexters.hyomk.domain.model.CreateAnniversary
import nexters.hyomk.domain.usecase.AddAnniversaryUseCase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val addAnniversaryUseCase: AddAnniversaryUseCase,
) : ViewModel() {
    private val today = LocalDateTime.now()
    private val _uiState = MutableStateFlow(
        CreateUiState(),
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
        viewModelScope.launch {
            _events.emit(CreateEvent.Loading)

            with(uiState.value) {
                addAnniversaryUseCase(
                    CreateAnniversary(
                        title = this.name,
                        date = Calendar.getInstance().apply { set(year, month, day) },
                        type = this.dateType,
                        alarmSchedule = this.alarms,
                        content = this.memo,
                    ),
                ).catch {
                    _events.emit(CreateEvent.Fail)
                    Timber.e(it)
                }.collectLatest { res ->
                    _events.emit(CreateEvent.Fail)
                    Timber.d("[create Anniversary] $res")
                }
            }
        }
    }
}

data class CreateUiState(
    val name: String = "",
    val memo: String = "",
    val alarms: List<AlarmSchedule> = listOf(AlarmSchedule.DDay),
    val dateType: AnniversaryDateType = AnniversaryDateType.Solar,
)

sealed class CreateEvent {
    object Success : CreateEvent()
    object Fail : CreateEvent()
    object Loading : CreateEvent()
}
