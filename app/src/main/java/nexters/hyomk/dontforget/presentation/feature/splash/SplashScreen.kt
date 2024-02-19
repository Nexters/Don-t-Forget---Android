package nexters.hyomk.dontforget.presentation.feature.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nexters.hyomk.dontforget.R
import nexters.hyomk.dontforget.navigation.NavigationItem
import nexters.hyomk.dontforget.presentation.component.BaseAlertDialog
import nexters.hyomk.dontforget.ui.theme.Gray900

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(
    navHostController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var visible by remember { mutableStateOf(true) }

    val deviceId by splashViewModel.deviceId.collectAsStateWithLifecycle()

    val animationTime = 600
    val animationDelayTime = 5

    val startLocation = Offset(0F, 100F)
    val endLocation = Offset(0F, 0F)
    val coroutine = rememberCoroutineScope()

    val permissionRequestState = rememberRequestPermissionsState(
        permissions = android.Manifest.permission.POST_NOTIFICATIONS,
    )

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash_lottie),
    )
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(Unit) {
        lottieAnimatable.animate(
            composition,
        )
    }

    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (permissionRequestState.requestPermission) {
                    if (deviceId.isNotBlank()) {
                        navHostController.navigate(
                            NavigationItem.Home.route,
                        ) {
                            launchSingleTop = true
                            popUpTo(NavigationItem.Splash.route) { inclusive = true }
                        }
                    }
                }
            }

            else -> {}
        }
    }

    RequestPermission(
        context = context,
        requestState = permissionRequestState,
        granted = {
            showDialog = false
            coroutine.launch {
                delay(
                    4500,
                )
                visible = permissionRequestState.requestPermission
                if (deviceId.isNotBlank()) {
                    navHostController.navigate(
                        NavigationItem.Home.route,
                    ) {
                        launchSingleTop = true
                        popUpTo(NavigationItem.Splash.route) { inclusive = true }
                    }
                }
            }
        },
        showRational = {
            showDialog = true
        },
        permanentlyDenied = {
            showDialog = true
        },
    )

    val splashLocation by animateOffsetAsState(
        targetValue = if (visible) endLocation else startLocation,
        animationSpec = tween(animationTime, animationDelayTime, easing = LinearOutSlowInEasing),
        label = "scrollAnimation",
    )

    if (showDialog) {
        Dialog(onDismissRequest = {}) {
            BaseAlertDialog(
                title = "알림 권한 허용",
                content = "기념일 리마인드 알림을 받기 위해 \n 알림 권한을 허용해주세요.",
                left = "앱 종료",
                right = "허용",
                onClickLeft = {
                    showDialog = false
                    (context as Activity).finish()
                },
                onClickRight = {
                    context.navigateToAppSettings()
                },
            )
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),

    ) {
        Scaffold(
            containerColor = Gray900,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(it),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_splash),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(splashLocation.x.dp, splashLocation.y.dp),
                        alignment = BiasAlignment(0f, 1f),
                        contentScale = ContentScale.FillWidth,
                    )

                    LottieAnimation(
                        composition,
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(splashLocation.x.dp, splashLocation.y.dp),
                        contentScale = ContentScale.FillWidth,
                        alignment = BiasAlignment(0f, 1f),

                    )
                }
            }
        }
    }
}

fun Context.navigateToAppSettings() {
    this.startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", this.packageName, null),
        ),
    )
}

class RequestPermissionState(initRequest: Boolean, val permission: String) {
    var requestPermission by mutableStateOf(initRequest)
}

@Composable
fun rememberRequestPermissionsState(
    initRequest: Boolean = true,
    permissions: String,
): RequestPermissionState {
    return remember {
        RequestPermissionState(initRequest, permissions)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    context: Context,
    requestState: RequestPermissionState,
    granted: () -> Unit,
    showRational: () -> Unit,
    permanentlyDenied: () -> Unit,
) {
    val permissionState = rememberPermissionState(permission = requestState.permission) { isGranted ->
        val permissionPermanentlyDenied = !ActivityCompat.shouldShowRequestPermissionRationale(
            context as Activity,
            requestState.permission,
        ) && !isGranted

        if (permissionPermanentlyDenied) {
            permanentlyDenied()
        } else if (!isGranted) {
            showRational()
        }
    }

    if (requestState.requestPermission) {
        requestState.requestPermission = false
        if (permissionState.status.isGranted) {
            granted()
        } else {
            LaunchedEffect(key1 = Unit) {
                permissionState.launchPermissionRequest()
            }
        }
    }

    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (permissionState.status.isGranted) {
                    granted()
                }
            }

            else -> {}
        }
    }
}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}
