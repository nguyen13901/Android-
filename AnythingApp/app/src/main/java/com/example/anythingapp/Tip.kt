package com.example.anythingapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Preview
@Composable
fun Tip() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            TopHeader()
            MainContent()
        }
    }
}

var money by mutableStateOf("0.00")

//@Preview
@Composable
fun TopHeader() {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = androidx.compose.ui.Modifier
                .background(androidx.compose.ui.graphics.Color.parse("#ead7f5"))
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Total Per Person",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.parse("#180b1f")
                )

                Text(text = "$" + money,
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.parse("#180b1f"),
                    modifier = Modifier.padding(3.dp))
            }
        }
    }
}

//@Preview
@Composable
fun MainContent() {
    val text = remember {
        mutableStateOf("")
    }
    val num = remember {
        mutableStateOf("1");
    }
    val tip = remember {
        mutableStateOf(0.0f);
    }
    val sliderValue = remember {
        mutableStateOf(0.0f)
    }
    val state = remember {
        mutableStateOf(false)
    }
    fun round(f : Float) : Float {
        return ((f * 100.0).roundToInt() / 100.0).toFloat()
    }
    fun cal() {
        money = round(((text.value.toFloat() + tip.value) / num.value.toInt())).toString()
    }
    Card(
        modifier = Modifier
            .padding(15.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = text.value,
                onValueChange = {
                    text.value = it
                    if (text.value != "") {
                        state.value = true
                        tip.value = sliderValue.value * text.value.toInt() / 100
                        cal()
                    } else {
                        state.value = false
                    }
                },
                label = { Text("Enter bill") },
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.ic_us_dollar),
                        "contentDescription")
                },
                modifier = Modifier.fillMaxWidth(),
            )
            if (state.value) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    Text(text = "Split",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(start = 0.dp, top = 0.dp, end = 10.dp, bottom = 0.dp)
                    )
                    CircleButton(counter = num.value.toInt(), mode = false) {
                            newValue -> num.value = newValue.toString()
                        cal()
                    }
                    Text(
                        text = num.value,
                        style = MaterialTheme.typography.subtitle1,
                    )
                    CircleButton(counter = num.value.toInt()) {
                            newValue -> num.value = newValue.toString()
                        cal()
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    Text(text = "Tip",
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Text(text = "$" + tip.value.toInt().toString(),
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = sliderValue.value.toInt().toString() + "%",
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
                Slider(value = sliderValue.value, onValueChange = {
                    sliderValue.value = it
                    tip.value = sliderValue.value * text.value.toInt() / 100
                    cal()
                }, steps = 4, valueRange = 0f..100f)
            }
        }
    }
}

@Composable
fun CircleButton(counter: Int = 0, mode : Boolean = true, count: (Int) -> Unit) {
    Card(modifier = Modifier
        .padding(3.dp)
        .size(40.dp)
        .clickable {
            if (mode) {
                count(counter + 1)
            } else {
                if (counter > 1) {
                    count(counter - 1)
                }
            }
        },
        shape = CircleShape,
        elevation = 4.dp) {
        Box(contentAlignment = Alignment.Center) {
            if (mode) {
                Text(text = "+")
            } else {
                Text(text = "-")
            }
        }
    }
}
