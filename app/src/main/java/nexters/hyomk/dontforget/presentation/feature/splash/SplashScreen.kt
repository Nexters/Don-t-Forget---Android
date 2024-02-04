package nexters.hyomk.dontforget.presentation.feature.splash

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nexters.hyomk.dontforget.R
import nexters.hyomk.dontforget.navigation.NavigationItem
import nexters.hyomk.dontforget.ui.theme.Gray900
import timber.log.Timber

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    var visible by remember { mutableStateOf(false) }

    val deviceId by splashViewModel.deviceId.collectAsStateWithLifecycle()

    val animationTime = 600
    val animationDelayTime = 5

    val startLocation = Offset(0F, 100F)
    val endLocation = Offset(0F, 0F)
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(deviceId) {
        visible = true
        coroutine.launch {
            delay(2000)
            visible = false
            Timber.d("device $deviceId")
            if (deviceId.isNotBlank()) {
                navHostController.navigate(
                    NavigationItem.Home.route,
                ) {
                    popUpTo(NavigationItem.Splash.route)
                }
            }
        }
    }
    val splashLocation by animateOffsetAsState(
        targetValue = if (visible) endLocation else startLocation,
        animationSpec = tween(animationTime, animationDelayTime, easing = LinearOutSlowInEasing),
        label = "scrollAnimation",
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),

    ) {
        Scaffold(
            containerColor = Gray900,
        ) {
            Column(modifier = Modifier.fillMaxSize().consumeWindowInsets(it)) {
                Image(
                    painter = painterResource(id = R.drawable.bg_full),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize().offset(splashLocation.x.dp, splashLocation.y.dp),
                    alignment = BiasAlignment(0f, 1f),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSplash() {
    SplashScreen(navHostController = rememberNavController())
}
