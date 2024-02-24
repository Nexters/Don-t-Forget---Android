package nexters.hyomk.dontforget.fcm

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.compose.ui.input.key.Key.Companion.J
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nexters.hyomk.domain.model.AlarmStatus
import nexters.hyomk.domain.model.FcmInfo
import nexters.hyomk.domain.usecase.GetDeviceInfoUseCase
import nexters.hyomk.domain.usecase.UpdateAnniversaryAlarmStateUseCase
import nexters.hyomk.dontforget.MainActivity
import nexters.hyomk.dontforget.R
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MyFirebaseMessageService : FirebaseMessagingService() {
    // 새로운 토큰이 생성될 때 마다 해당 콜백이 호출된다.

    @Inject
    lateinit var updateTokenUseCase: UpdateAnniversaryAlarmStateUseCase

    @Inject
    lateinit var deviceInfoUseCase: GetDeviceInfoUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("onNewToken: $token")
        // TODO 새로운 토큰 수신 시 서버로 전송
        CoroutineScope(Dispatchers.IO).launch {
            deviceInfoUseCase.invoke().catch {
                Timber.e("fcm token refresh fail : $it")
            }.collectLatest {
                if (!it.isNullOrBlank()) {
                    updateTokenUseCase.invoke(FcmInfo(token = token, deviceId = it, AlarmStatus.ON)).catch {
                        Timber.e("fcm token refresh fail : $it")
                    }.collectLatest {
                        Timber.d(it.toString())
                    }
                }
            }
        }
    }

    // Foreground에서 Push Service를 받기 위해 Notification 설정
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        J
        Timber.d("From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: ${remoteMessage.data}")

            // Check if data needs to be processed by long running job
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Timber.d("Message Notification Body: ${it.body}")
            sendNotification(it.title ?: getString(R.string.default_notification_channel_id), it.body ?: "")
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun sendNotification(title: String, body: String) {
        val notifyId = (System.currentTimeMillis() / 7).toInt()
        Timber.d("get noti$title / $body")

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent =
            PendingIntent.getActivity(this, notifyId, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val channelId = getString(R.string.default_notification_channel_id)
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_push)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)
            .setColor(0x3478F6)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelId,
                NotificationManager.IMPORTANCE_HIGH,
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notifyId, notificationBuilder.build())
    }
}
