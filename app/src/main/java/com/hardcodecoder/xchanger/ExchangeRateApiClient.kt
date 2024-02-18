package com.hardcodecoder.xchanger

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class ExchangeRateApiClient(private val url: String = URL) {

    companion object {
        const val URL = "https://open.er-api.com/v6/latest/INR"
    }

    fun fetchLatestRates(): ExchangeRate {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response ->
            val deserializer = Json { ignoreUnknownKeys = true }
            return deserializer.decodeFromString<ExchangeRate>(response.body!!.string())
        }
    }
}