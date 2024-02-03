package nexters.hyomk.dontforget.presentation.feature.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nexters.hyomk.domain.usecase.GetDeviceInfoUseCase
import nexters.hyomk.domain.usecase.UpdateDeviceInfoUseCase
import nexters.hyomk.dontforget.utils.DeviceIdProvider
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val application: Application,
    private val updateDeviceInfoUseCase: UpdateDeviceInfoUseCase,
    private val getDeviceInfoUseCase: GetDeviceInfoUseCase,
) : AndroidViewModel(application) {
    private val _deviceId = MutableStateFlow("")
    val deviceId get() = _deviceId

    init {
        viewModelScope.launch {
            getDeviceInfoUseCase().collectLatest { deviceId ->
                if (deviceId.isNullOrBlank()) {
                    DeviceIdProvider(application.applicationContext).getUniqueDeviceIdentifier().also {
                        updateDeviceInfoUseCase(it ?: "")
                        _deviceId.emit(it ?: "")
                    }
                }
            }
        }
    }
}
