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
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viwModel by viewModels<SplashViewModel>()

    private val lan: String = Locale.getDefault().language
    private val guide = getSupportGuide(lan.enumValueOrNull<SupportLanguage>())

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
