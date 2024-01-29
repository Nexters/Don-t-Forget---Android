package nexters.hyomk.dontforget.presentation.component.card

import androidx.compose.ui.graphics.Color
import nexters.hyomk.dontforget.R
import nexters.hyomk.dontforget.ui.theme.Gray400
import nexters.hyomk.dontforget.ui.theme.Gray600
import nexters.hyomk.dontforget.ui.theme.Primary500
import nexters.hyomk.dontforget.ui.theme.Primary600
import nexters.hyomk.dontforget.ui.theme.Primary700
import nexters.hyomk.dontforget.ui.theme.Yellow500

abstract class CardProperties() {
    abstract val type: CardType
    abstract val titleColor: Color
    abstract val dDayColor: Color
    abstract val dateColor: Color
    abstract val background: Int
}

data class ATypeCard(
    override val type: CardType = CardType.A,
    override val titleColor: Color = Gray400,
    override val dDayColor: Color = Primary600,
    override val dateColor: Color = Gray600,
    override val background: Int = R.drawable.ic_launcher_background,
) : CardProperties()

data class BTypeCard(
    override val type: CardType = CardType.B,
    override val titleColor: Color = Yellow500,
    override val dDayColor: Color = Primary600,
    override val dateColor: Color = Primary700,
    override val background: Int = R.drawable.ic_launcher_background,
) : CardProperties()
