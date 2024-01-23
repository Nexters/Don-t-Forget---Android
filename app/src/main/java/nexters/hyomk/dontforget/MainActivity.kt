package nexters.hyomk.dontforget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nexters.hyomk.dontforget.navigation.AppNavHost
import nexters.hyomk.dontforget.presentation.compositionlocal.GuideCompositionLocal
import nexters.hyomk.dontforget.ui.language.SupportLanguage
import nexters.hyomk.dontforget.ui.language.getSupportGuide
import nexters.hyomk.dontforget.ui.theme.DontForgetTheme
import nexters.hyomk.dontforget.utils.enumValueOrNull
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val lan: String = Locale.getDefault().language
    private val guide = getSupportGuide(lan.enumValueOrNull<SupportLanguage>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DontForgetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CompositionLocalProvider(GuideCompositionLocal.provides(guide)) {
                        AppNavHost(navController = rememberNavController())
                    }
                }
            }
        }
    }
}
