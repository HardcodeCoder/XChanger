package com.hardcodecoder.xchanger.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hardcodecoder.xchanger.ui.theme.XChangerTheme

@Composable
fun OptionTextField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    label: String,
    value: () -> String,
    keyboardType: KeyboardType = KeyboardType.Text,
    options: List<String>,
    readOnlyText: Boolean = false,
    onOptionChanged: (String) -> Unit,
    onValueChanged: (String) -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        OptionField(
            modifier = Modifier.padding(top = 8.dp),
            options = options,
            onOptionChanged = onOptionChanged
        )
        OutlinedTextField(
            modifier = Modifier.padding(start = 16.dp),
            label = { Text(text = label) },
            value = value(),
            readOnly = readOnlyText,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            onValueChange = onValueChanged
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionField(
    modifier: Modifier = Modifier,
    options: List<String>,
    onOptionChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier
                .width(128.dp)
                .wrapContentWidth()
                .menuAnchor(),
            value = selectedOption,
            readOnly = true,
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        selectedOption = it
                        onOptionChanged(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun DropDownMenuPreview() {
    XChangerTheme {
        OptionTextField(
            label = "Amount",
            value = { "36,787" },
            options = listOf("A", "B", "C"),
            onOptionChanged = {},
            onValueChanged = {}
        )
    }
}