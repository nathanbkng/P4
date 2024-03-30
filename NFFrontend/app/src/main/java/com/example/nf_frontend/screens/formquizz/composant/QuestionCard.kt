package com.example.nf_frontend.screens.formquizz.composant

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nf_frontend.data.questions.QuestionEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionCard(
    question: QuestionEntity,
    onQuestionChange: (QuestionEntity) -> Unit
) {
    val selectedOption = remember { mutableStateOf(question.isTrue) }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = question.questionText,
            onValueChange = { newText ->
                onQuestionChange(question.copy(questionText = newText))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            label = { Text("Question") }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    selectedOption.value = true
                    onQuestionChange(question.copy(isTrue = true))
                },
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    if (selectedOption.value) Color.Green else Color.Gray
                )
            ) {
                Text(text = "True", color = Color.White)
            }
            Button(
                onClick = {
                    selectedOption.value = false
                    onQuestionChange(question.copy(isTrue = false))
                },
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    if (!selectedOption.value) Color.Red else Color.Gray
                )
            ) {
                Text(text = "False", color = Color.White)
            }
        }
    }
}

