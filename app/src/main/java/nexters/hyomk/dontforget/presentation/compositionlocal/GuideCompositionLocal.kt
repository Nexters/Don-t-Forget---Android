package nexters.hyomk.dontforget.presentation.compositionlocal

import android.annotation.SuppressLint
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import nexters.hyomk.dontforget.ui.language.KorGuide
import nexters.hyomk.dontforget.ui.language.TransGuide

@SuppressLint("CompositionLocalNaming")
val GuideCompositionLocal: ProvidableCompositionLocal<TransGuide> = staticCompositionLocalOf { KorGuide() }
