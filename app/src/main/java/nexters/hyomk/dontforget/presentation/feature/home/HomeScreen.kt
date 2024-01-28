package nexters.hyomk.dontforget.presentation.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val list = (1..10).toList()
    val guide = GuideCompositionLocal.current
    val bottomState = rememberBottomSheetScaffoldState(
        SheetState(
            skipHiddenState = false,
            skipPartiallyExpanded = false,
            initialValue = SheetValue.PartiallyExpanded,
        ),
    )
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val displayMetrics = context.resources.displayMetrics
    val height = displayMetrics.heightPixels

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

    BottomSheetScaffold(
        modifier = Modifier.background(Gray900),
        sheetContainerColor = Gray900,
        containerColor = Gray900,
        scaffoldState = bottomState,
        sheetShape = RectangleShape,
        sheetPeekHeight = 300.dp,
        sheetDragHandle = {
            Timber.d("hanldle")
        },
        sheetSwipeEnabled = true,
        sheetContent = {
            Column(
                modifier = Modifier.navigationBarsPadding(),
            ) {
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
        },
    ) {
        Scaffold {
            Column {
                val offset = bottomState.bottomSheetState.requireOffset().roundToInt()
                Surface(
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                    modifier = Modifier.background(Gray900)
                        .height(pixelsToDp(pixels = offset)),
                ) {
                    Box(
                        modifier = Modifier
                            .background(Primary600)
                            .fillMaxSize()
                            .pointerInput(
                                Unit,
                            ) {
                                detectVerticalDragGestures { change, dragAmount ->
                                    if (dragAmount > 0) {
                                        closeSheet()
                                    } else {
                                        partialExpandSheet()
                                    }
                                }
                            },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMain() {
    HomeScreen()
}
