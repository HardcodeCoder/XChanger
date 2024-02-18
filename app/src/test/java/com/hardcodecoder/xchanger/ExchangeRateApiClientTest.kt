package com.hardcodecoder.xchanger

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExchangeRateApiClientTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var clientApi: ExchangeRateApiClient

    @Before
    fun setupMockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        clientApi = ExchangeRateApiClient(mockWebServer.url("/").toString())
    }

    @Test
    fun fetchExchangeRate_WithValidResponse() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(
                """
                {
                    "result": "success",
                    "time_last_update_unix": 1708214551,
                    "time_next_update_unix": 1708302381,
                    "base_code": "INR",
                    "rates": {
                        "INR": 1,
                        "AED": 0.044227,
                        "AFN": 0.887686
                    }
                }
            """.trimIndent()
            )
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockResponse)
        val exchangeRate = clientApi.fetchLatestRates()

        Assert.assertEquals("success", exchangeRate.result)
        Assert.assertEquals("INR", exchangeRate.base)
    }

    @Test
    fun fetchExchangeRate_WithEmptyBody() {
        val mockResponse = MockResponse()
            .setResponseCode(401)
            .setBody(
                """
                {
                    "result": "failed",
                    "message": "Resources not found"
                }
            """.trimIndent()
            )
            .addHeader("Content-Type", "application/json")

        mockWebServer.enqueue(mockResponse)
        val exchangeRate = clientApi.fetchLatestRates()

        Assert.assertEquals("failed", exchangeRate.result)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}