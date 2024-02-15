package com.hardcodecoder.xchanger

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class ExchangeRateRepo {

    private val erApiEndpoint = "https://open.er-api.com/v6/latest/INR"

    fun fetchLatestRates(): ExchangeRate {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(erApiEndpoint)
            .get()
            .build()
        val response = client.newCall(request).execute()

        if (response.code == 200 && response.body != null) {
            val deserializer = Json { ignoreUnknownKeys = true }
            return deserializer.decodeFromString<ExchangeRate>(response.body!!.string())
        }

        val rates: HashMap<String, Float> = HashMap()
        rates["INR"] = 1f
        rates["USD"] = 0.05f
        rates["AUS"] = 0.3f
        return ExchangeRate(
            result = "Success",
            lastUpdate = System.currentTimeMillis(),
            nextUpdate = System.currentTimeMillis() + 3600000,
            base = "INR",
            rates = rates
        )
    }
}