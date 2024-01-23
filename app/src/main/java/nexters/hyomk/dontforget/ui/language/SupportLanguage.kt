package nexters.hyomk.dontforget.ui.language

import nexters.hyomk.dontforget.utils.SafeEnumValue

enum class SupportLanguage : SafeEnumValue {
    KOR {
        override val value: String
            get() = "ko"
    },
    CHN {
        override val value: String
            get() = "zh"
    },
    JPN {
        override val value: String
            get() = "ja"
    },
}
