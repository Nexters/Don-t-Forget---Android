package nexters.hyomk.dontforget.utils

interface SafeEnumValue {
    val value: String
}

inline fun <reified T> String.enumValueOrNull(): T? where T : Enum<*>, T : SafeEnumValue =
    T::class.java.enumConstants?.firstOrNull { it.value == this }
