package nexters.hyomk.dontforget.presentation.component.card

import androidx.compose.ui.graphics.Color
import nexters.hyomk.domain.model.AnniversaryCardType
import nexters.hyomk.dontforget.R
import nexters.hyomk.dontforget.ui.theme.Gray400
import nexters.hyomk.dontforget.ui.theme.Gray50
import nexters.hyomk.dontforget.ui.theme.Gray600
import nexters.hyomk.dontforget.ui.theme.Gray900
import nexters.hyomk.dontforget.ui.theme.Primary500
import nexters.hyomk.dontforget.ui.theme.Primary600
import nexters.hyomk.dontforget.ui.theme.Primary700
import nexters.hyomk.dontforget.ui.theme.White
import nexters.hyomk.dontforget.ui.theme.Yellow500

abstract class CardProperties() {
    abstract val type: AnniversaryCardType
    abstract val titleColor: Color
    abstract val dDayColor: Color
    abstract val dateColor: Color
    abstract val background: Int
    abstract val backgroundColor: Color
}

data class ATypeCard(
    override val type: AnniversaryCardType = AnniversaryCardType.LUNAR,
    override val titleColor: Color = Gray400,
    override val dDayColor: Color = Primary600,
    override val dateColor: Color = Gray600,
    override val background: Int = R.drawable.bg_type_a,
    override val backgroundColor: Color = Color(0XFF81E23),

) : CardProperties()

data class BTypeCard(
    override val type: AnniversaryCardType = AnniversaryCardType.FACE,
    override val titleColor: Color = Gray900,
    override val dDayColor: Color = Primary600,
    override val dateColor: Color = Primary700,
    override val background: Int = R.drawable.bg_type_b,
    override val backgroundColor: Color = Color(0XFFD3E3F0),
) : CardProperties()

data class CTypeCard(
    override val type: AnniversaryCardType = AnniversaryCardType.ARM,
    override val titleColor: Color = White,
    override val dDayColor: Color = White,
    override val dateColor: Color = White,
    override val background: Int = R.drawable.bg_type_c,
    override val backgroundColor: Color = Primary500,
) : CardProperties()

data class DTypeCard(
    override val type: AnniversaryCardType = AnniversaryCardType.TAIL,
    override val titleColor: Color = Gray50,
    override val dDayColor: Color = Yellow500,
    override val dateColor: Color = White,
    override val background: Int = R.drawable.bg_type_d,
    override val backgroundColor: Color = Color(
        0XFF181E23,
    ),
) : CardProperties()

data class ETypeCard(
    override val type: AnniversaryCardType = AnniversaryCardType.FOREST,
    override val titleColor: Color = Gray50,
    override val dDayColor: Color = Primary600,
    override val dateColor: Color = White,
    override val background: Int = R.drawable.bg_type_e,
    override val backgroundColor: Color = Color(
        0XFF181E23,
    ),
) : CardProperties()

fun getCardProperties(type: AnniversaryCardType): CardProperties {
    return when (type) {
        AnniversaryCardType.LUNAR -> ATypeCard()
        AnniversaryCardType.FACE -> BTypeCard()
        AnniversaryCardType.ARM -> CTypeCard()
        AnniversaryCardType.TAIL -> DTypeCard()
        AnniversaryCardType.FOREST -> ETypeCard()
    }
}
