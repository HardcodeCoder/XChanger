package com.hardcodecoder.xchanger

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class ExchangeRateApiClient(private val url: String = URL) {

    companion object {
        const val URL = "https://open.er-api.com/v6/latest/INR"
    }

    suspend fun fetchLatestRates(): ExchangeRate = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).execute().use { response ->
            val deserializer = Json { ignoreUnknownKeys = true }
            response.body?.string()?.let { deserializer.decodeFromString<ExchangeRate>(it) }
                ?: ExchangeRate()
        }
    }
}