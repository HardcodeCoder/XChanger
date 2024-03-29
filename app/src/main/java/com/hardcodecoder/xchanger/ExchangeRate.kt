package com.hardcodecoder.xchanger

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRate(
    @SerialName("result") val result: String = "",
    @SerialName("time_last_update_unix") val lastUpdate: Long = 0,
    @SerialName("time_next_update_unix") val nextUpdate: Long = 0,
    @SerialName("base_code") val base: String = "",
    @SerialName("rates") val rates: HashMap<String, Float> = HashMap(0)
)
