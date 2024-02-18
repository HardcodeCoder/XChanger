package com.hardcodecoder.xchanger

import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private lateinit var exchangeRates: ExchangeRate
    private var sourceRate = "INR"
    private var targetRate = "INR"
    private var inputAmount: Float? = 0f
    private var targetAmount = mutableFloatStateOf(0f)
    val result by targetAmount.asFloatState()
    val availableRates = mutableStateListOf<String>()

    init {
        viewModelScope.launch {
            availableExchangeRates()
        }
    }

    fun submitInput(text: String) {
        inputAmount = text.toFloatOrNull()
        calculate()
    }

    fun sourceRate(rate: String) {
        sourceRate = rate
        calculate()
    }

    fun targetRate(rate: String) {
        targetRate = rate
        calculate()
    }

    private fun calculate() {
        val amountInINR = inputAmount?.div(exchangeRates.rates[sourceRate] ?: 1f)
        targetAmount.floatValue =
            amountInINR?.times(exchangeRates.rates[targetRate] ?: 1f) ?: 0f
    }

    private suspend fun availableExchangeRates() {
        withContext(Dispatchers.IO) {
            val tempList = mutableListOf<String>()
            exchangeRates = ExchangeRateApiClient().fetchLatestRates()
            exchangeRates.rates.forEach { tempList.add(it.key) }
            availableRates.addAll(tempList.sorted())
        }
    }
}