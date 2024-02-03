package nexters.hyomk.dontforget.presentation.feature.edit

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
import nexters.hyomk.domain.model.ModifyAnniversary
import nexters.hyomk.domain.usecase.GetDetailAnniversaryUseCase
import nexters.hyomk.domain.usecase.ModifyAnniversaryUserCase
import timber.log.Timber
import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getDetailAnniversaryUseCase: GetDetailAnniversaryUseCase,
    private val updateAnniversaryUseCase: ModifyAnniversaryUserCase,
) : ViewModel() {
    private val today = LocalDateTime.now()
    private val _uiState = MutableStateFlow<EditUiState>(
        EditUiState.Loading,
    )
    val uiState get() = _uiState

    private val _events = MutableSharedFlow<ModifyEvent>()
    val events get() = _events

    var _year = 0
    var _day = 0
    var _month = 0

    suspend fun getAnniversaryDetail(eventId: Long) {
        viewModelScope.launch {
            getDetailAnniversaryUseCase.invoke(eventId).catch {
            }.collectLatest {
                _uiState.emit(
                    EditUiState.Success(
                        eventId = eventId,
                        title = it.title,
                        solarDate = Calendar.getInstance().apply { set(2024, 1, 1) },
                        lunarDate = Calendar.getInstance().apply { set(2024, 2, 4) },
                        content = "선물주세요",
                        type = AnniversaryDateType.Solar,
                        alarmSchedule = listOf(AlarmSchedule.DDay),
                    ),
                )
            }
        }
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
        Timber.d(uiState.value.toString())
        if (uiState.value is EditUiState.Success) {
            viewModelScope.launch {
                _uiState.emit(EditUiState.Loading)
                val state = (uiState.value as EditUiState.Success)
                updateAnniversaryUseCase.invoke(
                    ModifyAnniversary(
                        title = state.title,
                        date = Calendar.getInstance().apply {
                            set(year, month, day)
                        },
                        type = state.type,
                        alarmSchedule = state.alarmSchedule,
                        content = state.content,

                    ),
                ).catch {
                    _events.emit(ModifyEvent.Fail)
                    Timber.e(it)
                }.collectLatest {
                    _uiState.emit(state)
                    _events.emit(ModifyEvent.Success)
                }
            }
        }
    }
}

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

sealed class ModifyEvent {
    object Success : ModifyEvent()
    object Fail : ModifyEvent()
}
