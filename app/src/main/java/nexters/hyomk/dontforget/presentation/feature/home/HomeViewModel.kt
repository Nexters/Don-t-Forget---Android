package nexters.hyomk.dontforget.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nexters.hyomk.domain.model.AnniversaryItem
import nexters.hyomk.domain.model.DetailAnniversary
import nexters.hyomk.domain.usecase.GetAnniversaryListUseCase
import nexters.hyomk.domain.usecase.GetDetailAnniversaryUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAnniversaryListUseCase: GetAnniversaryListUseCase,
    private val getDetailAnniversaryUseCase: GetDetailAnniversaryUseCase,

) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState get() = _uiState

    suspend fun getAnniversaryList() {
        viewModelScope.launch {
            getAnniversaryListUseCase.invoke().catch {
                Timber.e("api result fail $it")
                _uiState.emit(HomeUiState.Fail)
            }.collectLatest { list ->
                if (list.isEmpty()) {
                    _uiState.emit(HomeUiState.Empty)
                } else {
                    getDetailAnniversaryUseCase(list.first().eventId).catch {
                        Timber.e("api result fail detail $it")

                        _uiState.emit(HomeUiState.Fail)
                    }.collectLatest {
                        _uiState.emit(HomeUiState.Success(list, it))
                    }
                }
            }
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val list: List<AnniversaryItem>, val main: DetailAnniversary) : HomeUiState()
    object Fail : HomeUiState()

    object Empty : HomeUiState()
}
