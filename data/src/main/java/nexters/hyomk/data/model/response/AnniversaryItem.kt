package nexters.hyomk.data.model.response

import com.google.gson.annotations.SerializedName

data class AnniversaryItem(
    @SerializedName("eventId")
    val eventId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("lunarDate")
    val lunarDate: String,
    @SerializedName("solarDate")
    val solarDate: String,
)
