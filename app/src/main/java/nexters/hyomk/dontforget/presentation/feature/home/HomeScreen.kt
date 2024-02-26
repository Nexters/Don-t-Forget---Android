package nexters.hyomk.dontforget.presentation.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nexters.hyomk.domain.utils.calculateDDay
import nexters.hyomk.domain.utils.toFormatString
import nexters.hyomk.dontforget.R
import nexters.hyomk.dontforget.navigation.NavigationItem
import nexters.hyomk.dontforget.presentation.component.AddAnniversaryButton
import nexters.hyomk.dontforget.presentation.component.card.ATypeCard
import nexters.hyomk.dontforget.presentation.component.card.AnniversaryCard
import nexters.hyomk.dontforget.presentation.component.card.getCardProperties
import nexters.hyomk.dontforget.presentation.compositionlocal.GuideCompositionLocal
import nexters.hyomk.dontforget.presentation.feature.detail.CustomSnackBar
import nexters.hyomk.dontforget.presentation.feature.splash.OnLifecycleEvent
import nexters.hyomk.dontforget.presentation.utils.conditional
import nexters.hyomk.dontforget.presentation.utils.noRippleClickable
import nexters.hyomk.dontforget.presentation.utils.pixelsToDp
import nexters.hyomk.dontforget.ui.language.TransGuide
import nexters.hyomk.dontforget.ui.theme.Gray400
import nexters.hyomk.dontforget.ui.theme.Gray900
import nexters.hyomk.dontforget.ui.theme.Pink500
import nexters.hyomk.dontforget.ui.theme.Primary500
import java.util.Calendar

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val guide = GuideCompositionLocal.current

    val listState = rememberLazyGridState(
        initialFirstVisibleItemIndex = 0,
        initialFirstVisibleItemScrollOffset = 0,
    )

    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    val displayMetrics = context.resources.displayMetrics
    val maxHeightPx = displayMetrics.heightPixels
    val maxWidthPx = displayMetrics.widthPixels

    LaunchedEffect(Unit) {
        homeViewModel.getAnniversaryList()
    }

    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                coroutine.launch {
                    homeViewModel.getAnniversaryList()
                }
            }

            else -> {}
        }
    }

    when (uiState) {
        is HomeUiState.Success -> {
            Scaffold(
                modifier = Modifier.background(Gray900),
                containerColor = Gray900,
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                        .consumeWindowInsets(it),
                ) {
                    val anniversarys = (uiState as HomeUiState.Success).list
                    val main = (uiState as HomeUiState.Success).main

                    LazyVerticalGrid(
                        state = listState,
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.SpaceAround,
                        content = {
                            items(1, span = { GridItemSpan(2) }) {
                                Surface(
                                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                                    modifier = Modifier
                                        .background(Gray900)
                                        .padding(bottom = 40.dp)
                                        .noRippleClickable {
                                            navHostController.navigate(NavigationItem.Detail.route + "/${anniversarys.first().eventId}")
                                        },
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .height(560.dp)
                                            .fillMaxWidth()
                                            .background(Pink500),
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.bg_full),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            alignment = BiasAlignment(0f, 1f),
                                            contentScale = ContentScale.FillWidth,
                                        )
                                        Box(
                                            modifier = Modifier,
                                        ) {
                                            val type = ATypeCard()
                                            Column(
                                                verticalArrangement = Arrangement.Top,
                                                modifier = Modifier
                                                    .padding(horizontal = 36.dp),
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxHeight()
                                                        .padding(top = 100.dp),
                                                ) {
                                                    Text(
                                                        text = main.solarDate.toFormatString(),
                                                        style = MaterialTheme.typography.titleLarge,
                                                        color = type.dateColor,
                                                    )
                                                    val dday = calculateDDay(main.solarDate.time)

                                                    Text(
                                                        text = if (dday == 365L || dday == 0L) "D-DAY" else "D$dday",
                                                        style = MaterialTheme.typography.headlineLarge,
                                                        color = Primary500,
                                                    )
                                                    Spacer(modifier = Modifier.height(20.dp))
                                                    Row(
                                                        Modifier.heightIn(max = 60.dp),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                    ) {
                                                        Divider(
                                                            modifier = Modifier
                                                                .fillMaxHeight()
                                                                .width(2.5.dp),
                                                            color = type.dDayColor,
                                                        )
                                                        Column(
                                                            verticalArrangement = Arrangement.Center,
                                                            modifier = Modifier.padding(start = 16.dp).wrapContentHeight(),
                                                        ) {
                                                            Text(
                                                                text = main.title,
                                                                style = MaterialTheme.typography.titleMedium,
                                                                color = type.titleColor,
                                                                modifier = Modifier
                                                                    .padding(bottom = 8.dp),
                                                            )

                                                            if (main.content.isNotEmpty()) {
                                                                Text(
                                                                    text = main.content,
                                                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight(500)),
                                                                    color = type.dateColor,
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            items(anniversarys.size + 1) { index ->
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .conditional(
                                            index % 2 == 1,
                                        ) {
                                            padding(end = 16.dp, start = 8.dp)
                                        }
                                        .conditional(index % 2 == 0) {
                                            padding(start = 16.dp, end = 8.dp)
                                        },
                                ) {
                                    if (index == anniversarys.size) {
                                        AddAnniversaryButton(
                                            text = guide.createTitle,
                                            onClick = {
                                                navHostController.navigate(NavigationItem.Create.route)
                                            },
                                        )
                                    } else {
                                        val calendar = Calendar.getInstance()
                                        calendar.set(2024, 3, 24)

                                        AnniversaryCard(
                                            properties = getCardProperties(anniversarys[index].cardType),
                                            title = anniversarys[index].title,
                                            date = anniversarys[index].solarDate,
                                            onClick = {
                                                navHostController.navigate(NavigationItem.Detail.route + "/${anniversarys[index].eventId}")
                                            },
                                        )
                                    }
                                }
                            }
                            items(count = 1, span = { GridItemSpan(2) }) {
                                Box(
                                    Modifier
                                        .height(120.dp)
                                        .fillMaxWidth(),
                                )
                            }
                        },
                    )
                }
            }
        }

        is HomeUiState.Loading -> {
            LoadingContent()
        }

        is HomeUiState.Empty -> {
            EmptyContent(
                guide = guide,
                navHostController = navHostController,
                size = maxWidthPx / 2,
            )
        }

        is HomeUiState.Fail -> {
            FailContent(refresh = homeViewModel::getAnniversaryList)
        }

        else -> {}
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun FailContent(refresh: suspend () -> Unit) {
    val snackState = remember { SnackbarHostState() }
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            refreshScope.launch {
                refresh()
                delay(2000)
                refreshing = false
            }
        },
    )

    LaunchedEffect(Unit) {
        snackState.showSnackbar("네트워크 연결이 불안정합니다")
    }
    Scaffold(
        containerColor = Gray900,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(it)
                .imePadding(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_full),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                alignment = BiasAlignment(0f, 1f),
                contentScale = ContentScale.FillWidth,
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            CustomSnackBar(snackState = snackState, modifier = Modifier.align(Alignment.BottomCenter))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(state) // state 적용
                .verticalScroll(scrollState),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .height(
                        if (refreshing) { // 새로고침 중이면 높이 고정
                            140.dp
                        } else { // 당기기 정도에 따라 0~140dp까지 크기가 늘어남
                            lerp(0.dp, 140.dp, state.progress.coerceIn(0f..1f))
                        },
                    ),
            ) {
                if (refreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center),
                        color = Color.White,
                        strokeWidth = 1.dp,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoadingContent() {
    Scaffold(
        containerColor = Gray900,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(it),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),

            ) {
                Image(
                    painter = painterResource(id = R.drawable.bg_full),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    alignment = BiasAlignment(0f, 1f),
                    contentScale = ContentScale.FillWidth,
                )
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Gray400.copy(alpha = 0.6f), strokeWidth = 4.dp)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EmptyContent(
    guide: TransGuide,
    navHostController: NavHostController,
    size: Int,
) {
    Scaffold(
        containerColor = Gray900,
        modifier = Modifier.background(Gray900),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(it),

            contentAlignment = Alignment.BottomCenter,
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_splash),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                alignment = BiasAlignment(0f, 1f),
                contentScale = ContentScale.FillWidth,
            )
        }
        Column(
            modifier = Modifier.padding(top = 80.dp + it.calculateTopPadding(), start = 24.dp, end = 24.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(pixelsToDp(pixels = size) - 24.dp),
            ) {
                AddAnniversaryButton(
                    text = guide.createTitle,
                    onClick = {
                        navHostController.navigate(NavigationItem.Create.route)
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMain() {
    HomeScreen(rememberNavController())
}
