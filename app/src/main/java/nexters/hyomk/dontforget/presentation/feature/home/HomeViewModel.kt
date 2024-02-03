package nexters.hyomk.dontforget.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nexters.hyomk.domain.model.AnniversaryItem
import nexters.hyomk.domain.usecase.DeleteAnniversaryUseCase
import nexters.hyomk.domain.usecase.GetAnniversaryListUseCase
import nexters.hyomk.domain.usecase.UpdateDeviceInfoUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAnniversaryListUseCase: GetAnniversaryListUseCase,
    private val updateDeviceUseCase: UpdateDeviceInfoUseCase,
    private val deleteAnniversaryUseCase: DeleteAnniversaryUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val uiState get() = _uiState

    private val _events = MutableSharedFlow<HomeEvents>()
    val events get() = _events

    suspend fun getAnniversaryList() {
        viewModelScope.launch {
//            getAnniversaryListUseCase.invoke().catch {
//                Timber.e("api result fail $it")
//                _uiState.emit(HomeUiState.Fail)
//            }.collectLatest {
//                Timber.d("api result $it")
//                if (it.isEmpty()) {
//                    _uiState.emit(HomeUiState.Empty)
//                } else {
//                    _uiState.emit(HomeUiState.Success(it))
//                }
//            }
        }
    }

    fun deleteAnniversary(eventId: Long) {
        viewModelScope.launch {
//            deleteAnniversaryUseCase.invoke(eventId).catch {
//                _uiState.emit(HomeUiState.Fail)
//            }.collectLatest {
//                getAnniversaryList()
//            }
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val list: List<AnniversaryItem>) : HomeUiState()
    object Fail : HomeUiState()

    object Empty : HomeUiState()
}

sealed class HomeEvents {
    data class onClickItem(val eventId: Long) : HomeEvents()
    data class onClickEdit(val eventId: Long) : HomeEvents()
    data class onClicDelete(val eventId: Long) : HomeEvents()
}
