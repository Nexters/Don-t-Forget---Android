package nexters.hyomk.dontforget.presentation.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nexters.hyomk.data.util.HttpException
import nexters.hyomk.domain.model.DetailAnniversary
import nexters.hyomk.domain.usecase.DeleteAnniversaryUseCase
import nexters.hyomk.domain.usecase.GetDetailAnniversaryUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val deleteAnniversaryUseCase: DeleteAnniversaryUseCase,
    private val getDetailAnniversaryUseCase: GetDetailAnniversaryUseCase,
) : ViewModel() {
    private val _uiState = MutableSharedFlow<DetailUiState>()
    val uiState get() = _uiState

    private val _events = MutableSharedFlow<DetailEvents>()
    val events get() = _events

    fun getDetailAnniversary(eventId: Long) {
        viewModelScope.launch {
            _uiState.emit(DetailUiState.Loading)
            getDetailAnniversaryUseCase(eventId).catch {
                if (it is HttpException) {
                    Timber.e(it.message)
                }
                Timber.e(it.message)
                _uiState.emit(DetailUiState.Fail)
            }.collectLatest {
                Timber.d(it.toString())
                _uiState.emit(
                    DetailUiState.Success(
                        data =
                        DetailAnniversary(
                            eventId = it.eventId,
                            title = it.title,
                            content = it.content,
                            lunarDate = it.lunarDate,
                            solarDate = it.solarDate,
                            alarmSchedule = it.alarmSchedule,
                        ),
                    ),
                )
            }
        }
    }

    fun deleteAnniversary(eventId: Long) {
        viewModelScope.launch {
            _uiState.emit(DetailUiState.Loading)
            runCatching {
                deleteAnniversaryUseCase.invoke(eventId)
            }.onFailure {
                _uiState.emit(DetailUiState.Fail)
            }.onSuccess {
                _events.emit(DetailEvents.OnDeleteSuccess)
            }
        }
    }
}

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val data: DetailAnniversary) : DetailUiState()
    object Fail : DetailUiState()
}

sealed class DetailEvents {
    data class onClicDelete(val eventId: Long) : DetailEvents()
    object OnDeleteSuccess : DetailEvents()
}
