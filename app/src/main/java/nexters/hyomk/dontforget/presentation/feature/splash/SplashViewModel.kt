package nexters.hyomk.dontforget.presentation.feature.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nexters.hyomk.domain.model.AlarmStatus
import nexters.hyomk.domain.model.FcmInfo
import nexters.hyomk.domain.usecase.GetDeviceInfoUseCase
import nexters.hyomk.domain.usecase.UpdateAnniversaryAlarmStateUseCase
import nexters.hyomk.domain.usecase.UpdateDeviceInfoUseCase
import nexters.hyomk.dontforget.utils.DeviceIdProvider
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: Application,
    private val updateDeviceInfoUseCase: UpdateDeviceInfoUseCase,
    private val updateFcmInfoUseCase: UpdateAnniversaryAlarmStateUseCase,
    private val getDeviceInfoUseCase: GetDeviceInfoUseCase,
) : AndroidViewModel(application) {
    private val _deviceId = MutableStateFlow("")
    val deviceId get() = _deviceId

    private val _events = MutableSharedFlow<SplashEvent>()
    val events get() = _events

    init {
        viewModelScope.launch {
            getDeviceInfoUseCase().collectLatest { deviceId ->
                if (deviceId.isNullOrBlank()) {
                    DeviceIdProvider(application.applicationContext).getUniqueDeviceIdentifier().also {
                        updateDeviceInfoUseCase(it ?: "")
                        _deviceId.emit(it ?: "")
                    }
                } else {
                    _deviceId.emit(deviceId)
                }
            }
        }
    }

    fun updateFcmInfo(fcmToken: String) {
        viewModelScope.launch {
            updateFcmInfoUseCase.invoke(
                FcmInfo(token = fcmToken, deviceId = deviceId.value, status = AlarmStatus.ON),
            ).catch {
                Timber.e(it)
                _events.emit(SplashEvent.Fail)
            }.collectLatest {
                Timber.d(it.toString())
                _events.emit(SplashEvent.Success)
            }
        }
    }

    sealed class SplashEvent {
        object Success : SplashEvent()
        object Fail : SplashEvent()
    }
}
