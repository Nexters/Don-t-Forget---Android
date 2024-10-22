package nexters.hyomk.dontforget.presentation.feature.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nexters.hyomk.domain.utils.calculateDDay
import nexters.hyomk.domain.utils.toFormatString
import nexters.hyomk.dontforget.R
import nexters.hyomk.dontforget.navigation.NavigationItem
import nexters.hyomk.dontforget.presentation.component.BaseAlertDialog
import nexters.hyomk.dontforget.presentation.component.BaseIconButton
import nexters.hyomk.dontforget.presentation.component.card.ATypeCard
import nexters.hyomk.dontforget.presentation.compositionlocal.GuideCompositionLocal
import nexters.hyomk.dontforget.ui.theme.Gray900
import nexters.hyomk.dontforget.ui.theme.Primary500

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    navHostController: NavHostController,
    eventId: Long,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val type = ATypeCard()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle(DetailUiState.Loading)

    val lifecycle = LocalLifecycleOwner.current

    var showDialog by remember { mutableStateOf(false) }
    val snackState = remember { SnackbarHostState() }

    val guide = GuideCompositionLocal.current

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.card),
    )
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(Unit) {
        lottieAnimatable.animate(composition)
        viewModel.getDetailAnniversary(eventId)
    }

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.events.collectLatest {
                when (it) {
                    is DetailEvents.OnDeleteSuccess -> {
                        showDialog = false
                        navHostController.popBackStack()
                    }

                    is DetailEvents.onClicDelete -> {
                        showDialog = true
                    }
                }
            }
        }
    }

    if (showDialog) {
        Dialog(onDismissRequest = {}) {
            BaseAlertDialog(
                title = guide.deleteDialogTitle,
                content = guide.deleteDialogContent,
                icon = R.drawable.ic_anniversary_delete,
                left = guide.close,
                right = guide.delete,
                isWarning = true,
                onClickLeft = { showDialog = false },
                onClickRight = {
                    viewModel.deleteAnniversary(eventId)
                },
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_splash),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        LottieAnimation(
            composition,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            alignment = BiasAlignment(0f, 1f),

        )

        when (uiState) {
            is DetailUiState.Loading -> {
            }

            is DetailUiState.Success -> {
                val data = (uiState as DetailUiState.Success).data

                Box(modifier = Modifier.background(color = Color.Transparent), contentAlignment = Alignment.TopCenter) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 18.dp),

                            ) {
                                BaseIconButton(
                                    icon = R.drawable.ic_back,
                                    onClick = {
                                        navHostController.popBackStack()
                                    },
                                )

                                Row() {
                                    BaseIconButton(
                                        icon = R.drawable.ic_edit,
                                        onClick = {
                                            navHostController.navigate(route = NavigationItem.Edit.route + "/${data.eventId}")
                                        },
                                    )
                                    BaseIconButton(
                                        icon = R.drawable.ic_delete,
                                        onClick = {
                                            showDialog = true
                                        },
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 32.dp),
                            ) {
                                Text(
                                    text = "${data.solarDate.toFormatString()}",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = type.dateColor,
                                )

                                val dday = calculateDDay(data.solarDate.time)

                                Text(
                                    text = if (dday == 365L || dday == 0L) "D-DAY" else "D$dday",
                                    style = MaterialTheme.typography.headlineLarge.copy(lineHeight = 72.sp),
                                    color = Primary500,
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Row(Modifier.heightIn(max = 60.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Divider(
                                        modifier = Modifier.fillMaxHeight()
                                            .width(2.5.dp),
                                        color = type.dDayColor,
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(start = 16.dp),
                                    ) {
                                        Text(
                                            text = data.title,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = type.titleColor,
                                            modifier = Modifier
                                                .padding(bottom = 8.dp),
                                        )

                                        if (data.content.isNotEmpty()) {
                                            Text(
                                                text = data.content,
                                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight(500)),
                                                color = type
                                                    .dateColor,
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxHeight(),

                            )
                        }
                    }
                }
            }

            is DetailUiState.Fail -> {
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        snackState.showSnackbar("기념일 조회에 실패하였습니다.")
                    }
                }
                Box(modifier = Modifier.background(color = Color.Transparent), contentAlignment = Alignment.TopCenter) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 16.dp + it.calculateTopPadding()),

                        ) {
                            BaseIconButton(
                                icon = R.drawable.ic_back,
                                onClick = {
                                    navHostController.popBackStack()
                                },
                            )
                        }
                    }
                }
            }
        }
        CustomSnackBar(snackState = snackState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun CustomSnackBar(
    snackState: SnackbarHostState,
    modifier: Modifier,
) {
    SnackbarHost(
        modifier = modifier,
        hostState = snackState,
    ) { snackbarData: SnackbarData ->
        Snackbar(
            containerColor = Gray900,
            modifier = Modifier
                .padding(all = 8.dp)
                .offset(y = -20.dp), // show the snackbar at the bottom of the screen
        ) {
            Text(text = snackbarData.visuals.message, style = MaterialTheme.typography.bodySmall)
        }
    }
}
