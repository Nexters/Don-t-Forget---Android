package nexters.hyomk.dontforget.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import nexters.hyomk.dontforget.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_light, FontWeight.Light, FontStyle.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.64.sp,
        letterSpacing = 0.5.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 21.48.sp,
        letterSpacing = 0.5.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 23.87.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 72.sp,
        lineHeight = 85.97.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 42.96.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 29.04.sp,
        letterSpacing = 0.5.sp,
    ),
    displayLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.64.sp,
        letterSpacing = 0.5.sp,
    ),

)
