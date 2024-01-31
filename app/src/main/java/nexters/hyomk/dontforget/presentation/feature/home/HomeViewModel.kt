package nexters.hyomk.dontforget.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nexters.hyomk.domain.model.AnniversaryItem
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState get() = _uiState

    private val _events = MutableSharedFlow<HomeEvents>()
    val events get() = _events

    suspend fun getAnniversaryList() {
        viewModelScope.launch {
            val list = List<AnniversaryItem>(20) {
                AnniversaryItem(
                    eventId = 1L,
                    title = "something",
                    lunarDate = Calendar.getInstance().apply { set(1999, 1, 3) },
                    solarDate = Calendar.getInstance().apply { set(1999, 3, 3) },
                )
            }.mapIndexed { idx, v ->
                v.copy(title = v.title + "$idx")
            }

            _uiState.emit(HomeUiState.Success(list))
        }
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val list: List<AnniversaryItem>) : HomeUiState()
    object Fail : HomeUiState()
}

sealed class HomeEvents {
    data class onClickItem(val eventId: Long) : HomeEvents()
    data class onClickEdit(val eventId: Long) : HomeEvents()
}
