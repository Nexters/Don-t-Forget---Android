package nexters.hyomk.dontforget.ui.language

fun getSupportGuide(lan: SupportLanguage?): TransGuide {
    return when (lan) {
        SupportLanguage.CHN -> ChnGuide()
        SupportLanguage.JPN -> JpnGuide()
        else -> KorGuide()
    }
}
