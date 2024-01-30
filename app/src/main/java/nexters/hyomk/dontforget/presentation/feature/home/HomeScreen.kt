package nexters.hyomk.dontforget.presentation.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import nexters.hyomk.dontforget.navigation.NavigationItem
import nexters.hyomk.dontforget.presentation.component.AddAnniversaryButton
import nexters.hyomk.dontforget.presentation.component.card.ATypeCard
import nexters.hyomk.dontforget.presentation.component.card.AnniversaryCard
import nexters.hyomk.dontforget.presentation.compositionlocal.GuideCompositionLocal
import nexters.hyomk.dontforget.presentation.utils.pixelsToDp
import nexters.hyomk.dontforget.ui.theme.Gray900
import nexters.hyomk.dontforget.ui.theme.Primary600
import timber.log.Timber
import java.util.Calendar
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
) {
    fun createInitialBottomSheetState(): BottomSheetScaffoldState {
        return BottomSheetScaffoldState(
            bottomSheetState = SheetState(
                initialValue = SheetValue.PartiallyExpanded,
                skipHiddenState = false,
                skipPartiallyExpanded = false,
            ),
            snackbarHostState = SnackbarHostState(),
        )
    }

    val list = (1..20).toList()
    val guide = GuideCompositionLocal.current
    var bottomState: BottomSheetScaffoldState by remember { mutableStateOf(createInitialBottomSheetState()) }

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val displayMetrics = context.resources.displayMetrics
    val maxHeightPx = displayMetrics.heightPixels
    var offset = remember { mutableIntStateOf(0) }
    var sheetPeekHeight by remember { mutableStateOf(0.dp) }

    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        Timber.d("init screen ${bottomState.bottomSheetState}")
        bottomState = createInitialBottomSheetState()
    }

    fun closeSheet() {
        coroutine.launch {
            bottomState.bottomSheetState.hide()
        }
    }

    fun partialExpandSheet() {
        coroutine.launch {
            bottomState.bottomSheetState.partialExpand()
        }
    }

    val gestureModifier = Modifier.pointerInput(Unit) {
        detectVerticalDragGestures { change, dragAmount ->
            if (dragAmount > 0) {
                closeSheet()
            } else {
                partialExpandSheet()
            }
        }
    }

    BottomSheetScaffold(
        modifier = Modifier
            .background(Gray900)
            .imePadding(),
        sheetContainerColor = Gray900,
        sheetContentColor = Gray900,
        containerColor = Gray900,
        contentColor = Gray900,
        scaffoldState = bottomState,
        sheetShape = RectangleShape,
        sheetPeekHeight = 200.dp,
        sheetDragHandle = {
            Timber.d("hanldle")
        },
        sheetSwipeEnabled = true,
        sheetContent = {
            Box(
                modifier = Modifier
                    .navigationBarsPadding()
                    .onPlaced {
                        if (sheetPeekHeight == 0.dp) {
                            sheetPeekHeight = with(density) {
                                it.size.height.toDp()
                            }
                        }
                    },
            ) {
                Column() {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp,
                        ),
                        horizontalArrangement = Arrangement.SpaceAround,
                        content = {
                            items(list.size + 1) { index ->
                                if (index == list.size) {
                                    Box(modifier = Modifier.padding(8.dp)) {
                                        AddAnniversaryButton(
                                            text = guide.createTitle,
                                        )
                                    }
                                } else {
                                    Box(modifier = Modifier.padding(8.dp)) {
                                        val calendar = Calendar.getInstance()
                                        calendar.set(2024, 3, 24)

                                        AnniversaryCard(
                                            properties = ATypeCard(),
                                            title = "생일이다",
                                            date = calendar,
                                            onClick = {
                                                navHostController.navigate(NavigationItem.Create.route)
                                            },
                                        )
                                    }
                                }
                            }
                        },
                    )
                    Spacer(
                        Modifier.navigationBarsPadding(),
                    )
                }
            }
        },
    ) {
        FlexAnniversaryContent(
            bottomState = bottomState,
            offset = offset,
            maxHeightPx = maxHeightPx,
            padding = it,
            navHostController = navHostController,
            gestureModifier = gestureModifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FlexAnniversaryContent(
    gestureModifier: Modifier,
    bottomState: BottomSheetScaffoldState,
    offset: MutableState<Int>,
    maxHeightPx: Int,
    padding: PaddingValues,
    navHostController: NavHostController,
) {
    val topBarHeight = 24.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(gestureModifier)
            .onGloballyPositioned { coordinates ->
                Timber.d("coordinates ${coordinates.size.height}")

                Timber.d("offset ${bottomState.bottomSheetState.requireOffset()}")
                offset.value = bottomState.bottomSheetState
                    .requireOffset()
                    .roundToInt()
            },
    ) {
        val contentOffset = min(pixelsToDp(pixels = maxHeightPx - offset.value) * -1, topBarHeight)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding),
        ) {
            Surface(
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                modifier = Modifier
                    .background(Gray900)
                    .fillMaxWidth()
                    .height(pixelsToDp(pixels = maxHeightPx))
                    .offset(y = contentOffset),
            ) {
                Box(
                    modifier = Modifier
                        .background(Primary600)
                        .fillMaxSize(),

                ) {
                    DetailContent(navHostController)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMain() {
    HomeScreen(rememberNavController())
}
