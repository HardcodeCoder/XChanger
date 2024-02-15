package com.hardcodecoder.xchanger

import android.app.Application
import androidx.compose.runtime.asFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var exchangeRates: ExchangeRate
    private var sourceRate = "INR"
    private var targetRate = "INR"
    private var inputAmount: Float? = 0.0f
    private var targetAmount = mutableFloatStateOf(0.0f)
    val result by targetAmount.asFloatState()
    val availableRates = mutableStateListOf<String>()

    init {
        viewModelScope.launch {
            availableExchangeRates()
        }
    }

    fun submitInput(text: String) {
        inputAmount = text.toFloatOrNull()
        recalculate()
    }

    fun sourceRate(rate: String) {
        sourceRate = rate
        recalculate()
    }

    fun targetRate(rate: String) {
        targetRate = rate
        recalculate()
    }

    private fun recalculate() {
        val amountInINR = inputAmount?.div(exchangeRates.rates[sourceRate] ?: 1.0f)
        targetAmount.floatValue =
            amountInINR?.times(exchangeRates.rates[targetRate] ?: 1.0f) ?: 0.0f
    }

    private suspend fun availableExchangeRates() {
        withContext(Dispatchers.IO) {
            exchangeRates = ExchangeRateRepo().fetchLatestRates()

            val tempList = mutableListOf<String>()
            exchangeRates.rates.forEach {
                tempList.add(it.key)
            }
            availableRates.addAll(tempList.sorted())
        }
    }
}