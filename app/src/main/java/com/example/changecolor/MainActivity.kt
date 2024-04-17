package com.example.changecolor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.changecolor.ui.theme.ChangeColorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChangeColorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}
@Composable
fun MainApp() {
    var red by remember { mutableStateOf("") }
    var green by remember { mutableStateOf("") }
    var blue by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        RGBInputField(label = "Red", value = red, onValueChange = { red = it })
        RGBInputField(label = "Green", value = green, onValueChange = { green = it })
        RGBInputField(label = "Blue", value = blue, onValueChange = { blue = it })
        Spacer(modifier = Modifier.height(16.dp))

        val color = try {
            Color(red.toInt(), green.toInt(), blue.toInt())
        } catch (e: NumberFormatException) {
            Color.Gray
        }

        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .border(4.dp, Color.Black, RoundedCornerShape(4.dp))
                .background(color)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RGBInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    var isError by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || newValue.toIntOrNull() in 0..255) {
                onValueChange(newValue)
                isError = false
            } else {
                isError = true
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        isError = isError,
        trailingIcon = {
            if (isError) {
                Text(text = "!", color = Color.Red)
            }
        }
    )
    if (isError) {
        Text(text = "Value must be between 0 and 255", color = Color.Red, style = MaterialTheme.typography.bodySmall)
    }
    Spacer(modifier = Modifier.height(8.dp))
}
