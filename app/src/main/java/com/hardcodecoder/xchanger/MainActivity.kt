package com.hardcodecoder.xchanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hardcodecoder.xchanger.composable.OptionTextField
import com.hardcodecoder.xchanger.ui.theme.XChangerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XChangerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val viewModel = viewModel<MainViewModel>()
        var sourceValue by remember { mutableStateOf("0.0") }

        OptionTextField(
            label = "From",
            value = { sourceValue },
            keyboardType = KeyboardType.Number,
            options = viewModel.availableRates,
            onOptionChanged = {
                viewModel.sourceRate(it)
            },
            onValueChanged = {
                sourceValue = it
                viewModel.submitInput(it)
            }
        )
        Spacer(modifier = Modifier.size(72.dp))
        OptionTextField(
            label = "To",
            value = { viewModel.result.toString() },
            keyboardType = KeyboardType.Number,
            options = viewModel.availableRates,
            readOnlyText = true,
            onOptionChanged = {
                viewModel.targetRate(it)
            },
            onValueChanged = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_7_pro",
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun GreetingPreview() {
    XChangerTheme {
        MainScreen()
    }
}