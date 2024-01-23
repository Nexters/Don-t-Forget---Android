package nexters.hyomk.dontforget.presentation.feature.splash

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import nexters.hyomk.dontforget.presentation.compositionlocal.GuideCompositionLocal

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen() {
    val guide = GuideCompositionLocal.current
    Scaffold {
        Text(text = "splash" + guide.appName)
    }
}
