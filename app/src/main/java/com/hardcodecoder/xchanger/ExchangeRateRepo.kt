package com.hardcodecoder.xchanger

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class ExchangeRateRepo {

    fun fetchExchangeRates(): ExchangeRate {
        val response = fetch()
        if (response.code == 200 && response.body != null) {
            val deserializer = Json { ignoreUnknownKeys = true }
            return deserializer.decodeFromString<ExchangeRate>(response.body!!.string())
        }
        return defaultExchangeRate()
    }

    private fun defaultExchangeRate(): ExchangeRate {
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

    private fun fetch(): Response {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://open.er-api.com/v6/latest/INR")
            .get()
            .build()
        return client.newCall(request).execute()
    }
}