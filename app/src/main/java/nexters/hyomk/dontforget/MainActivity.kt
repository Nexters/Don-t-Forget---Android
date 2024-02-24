package nexters.hyomk.dontforget

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import nexters.hyomk.dontforget.navigation.AppNavHost
import nexters.hyomk.dontforget.presentation.compositionlocal.GuideCompositionLocal
import nexters.hyomk.dontforget.presentation.feature.splash.SplashViewModel
import nexters.hyomk.dontforget.ui.language.SupportLanguage
import nexters.hyomk.dontforget.ui.language.getSupportGuide
import nexters.hyomk.dontforget.ui.theme.DontForgetTheme
import nexters.hyomk.dontforget.ui.theme.Gray800
import nexters.hyomk.dontforget.ui.theme.Gray900
import nexters.hyomk.dontforget.utils.enumValueOrNull
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viwModel by viewModels<SplashViewModel>()

    private val lan: String = Locale.getDefault().language
    private val guide = getSupportGuide(lan.enumValueOrNull<SupportLanguage>())
    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.w("Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                Timber.d("[fcm] : $token")
            },
        )
    }

    override fun onResume() {
        super.onResume()
        getFcmToken()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )

        setContent {
            DontForgetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(color = Gray900),
                    color = Gray900,
                    contentColor = Gray900,
                ) {
                    CompositionLocalProvider(GuideCompositionLocal.provides(guide)) {
                        AppNavHost(modifier = Modifier.fillMaxSize().background(Gray800), navController = rememberNavController())
                    }
                }
            }
        }
    }
}
