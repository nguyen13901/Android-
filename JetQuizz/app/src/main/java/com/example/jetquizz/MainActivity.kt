package com.example.jetquizz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetquizz.ui.theme.JetQuizzTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetquizz.model.Question
import com.example.jetquizz.model.QuestionItem
import com.example.jetquizz.viewModel.QuestionViewModel

val listQuestion = ArrayList<QuestionItem>()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        listQuestion.add(
            QuestionItem("True", "world", listOf("False","True"), "LFD2 was banned in Australia."))
        listQuestion.add(
            QuestionItem("Louisiana", "world", listOf("Oklahoma","Florida","Louisiana","Georgia"), "One of the remnants of this states former status as a possession of France is the fact that it was named after a French king."))
        listQuestion.add(
            QuestionItem("Montana", "world", listOf("Nebraska","Nevada","Colorado","Montana"), "No state has as many different species of mammals as this one. The average square mile of land contains 1.4 elk, 1.4 pronghorn antelope, and 3.3 deer."))
        listQuestion.add(
            QuestionItem("True", "world", listOf("False","True"), "Arizona became the 48th state on February 14, 1912."))
        super.onCreate(savedInstanceState)
        setContent {
            JetQuizzTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    val questionViewModel = viewModel<QuestionViewModel>()
//                    val listQuestion = remember {
//                        mutableStateListOf<QuestionItem>()
//                    }
//                    val listQuestion = questionViewModel.questions
//                    if (listQuestion != null) {
//                        MainContent(listQuestion)
//                    }
                    MainContent(listQuestion)
                }
            }
        }
    }
}

fun Color.Companion.parse(colorString: String): Color =
    Color(color = android.graphics.Color.parseColor(colorString))

//@Preview
@Composable
fun MainContent(listQuestion: ArrayList<QuestionItem>) {
    val stroke = Stroke(width = 5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    val index = remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Question ${index.value} /",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            Text(
                text = "4875",
                fontSize = 20.sp
            )
        }
        Row() {
            Canvas(
                Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .padding(0.dp, 10.dp)
            ) {
                drawRoundRect(color = Color.White, style = stroke)
            }
        }
        Text(
            text = listQuestion.get(index.value).question,
            fontSize = 18.sp,
            modifier = Modifier.padding(0.dp, 20.dp, 0.dp, 10.dp)
        )
        fun nextClick() {
            index.value = index.value + 1
        }
        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier.fillMaxSize()
        ) {
            Form(listQuestion.get(index.value).choices, listQuestion.get(index.value).answer, ::nextClick)
        }
    }
}

//@Preview
@Composable
fun Form(listAnswer: List<String> = listOf("Yes", "No"), result: String, onClick: () -> Unit) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }
    val isShow = remember {
        mutableStateOf(false)
    }
    Column(

    ) {
        LazyColumn() {
            items(items = listAnswer) {
                answer ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (answer == selectedOption),
                                onClick = { onOptionSelected(answer) }
                            ),
                        verticalAlignment = CenterVertically
                    ) {
                        RadioButton(
                            selected = (answer == selectedOption),
                            modifier = Modifier.padding(all = Dp(value = 8F)),
                            onClick = {
                                onOptionSelected(answer)
                                isShow.value = true
                            }
                        )
                        Text(
                            text = answer,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .fillMaxWidth()
                                .background(
                                    if (isShow.value && answer == result) {
                                        Color.parse("#19cf74")
                                    } else {
                                        Color.Transparent
                                    }
                                ),
                            fontSize = 18.sp,
                        )
                    }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedButton(
                onClick = {
                    onClick()
                    isShow.value = false
                },
                border = BorderStroke(1.dp, Color.parse("#03dac5")),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.parse("#03dac5")),
            ){
                Text(
                    text = "Next",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                )
            }
        }
    }
}
