package nexters.hyomk.dontforget.presentation.feature.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nexters.hyomk.domain.model.AlarmSchedule
import nexters.hyomk.domain.model.AnniversaryDateType
import nexters.hyomk.domain.model.DetailAnniversary
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

    suspend fun initAnniversaryDetail(anniversary: DetailAnniversary) {
        viewModelScope.launch {
            _uiState.emit(
                EditUiState.Success(
                    eventId = anniversary.eventId,
                    title = anniversary.title,
                    solarDate = anniversary.solarDate,
                    lunarDate = anniversary.lunarDate,
                    content = anniversary.content,
                    type = AnniversaryDateType.SOLAR,
                    alarmSchedule = anniversary.alarmSchedule,
                ),
            )
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
                val state = (uiState.value as EditUiState.Success)
                runCatching {
                    updateAnniversaryUseCase.invoke(
                        eventId = state.eventId,
                        request = ModifyAnniversary(
                            title = state.title,
                            date = Calendar.getInstance().apply {
                                set(year, month, day)
                            },
                            type = state.type,
                            alarmSchedule = state.alarmSchedule,
                            content = state.content,

                        ),
                    )
                }.onFailure {
                    _events.emit(ModifyEvent.Fail)
                    Timber.e(it)
                }.onSuccess {
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
