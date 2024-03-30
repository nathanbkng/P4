package com.example.nf_frontend.screens.quizz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.questions.QuestionEntity
import com.example.nf_frontend.data.quizzes.QuizzWithQuestions
import com.example.nf_frontend.screens.formquizz.composant.QuestionCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun QuizzScreen(quizzId: Long, navController: NavController) {
    var quizzWithQuestions by remember { mutableStateOf<QuizzWithQuestions?>(null) }

    LaunchedEffect(quizzId) {
        MainActivity.database.quizzDao().getQuizzWithQuestionsById(quizzId).collect { quizz ->
            quizzWithQuestions = quizz
        }
    }

    // Function to add a new question to the list
    fun addQuestion() {
        quizzWithQuestions?.let { quizz ->
            val newQuestion = QuestionEntity(questionText = "", isTrue = true, quizzLinkedId = quizz.quizz.quizzId)
            val updatedQuestions = quizz.questions.toMutableList().apply { add(newQuestion) }
            quizzWithQuestions = quizz.copy(questions = updatedQuestions)
        }
    }

    // Function to save modified questions to the database
    fun saveQuestions() {
        quizzWithQuestions?.let { quizz ->
            quizz.questions.forEach { question ->
                if (question.questionText.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        MainActivity.database.questionDao().insertOrUpdateQuestion(question)
                    }
                }
            }
            navController.popBackStack()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            quizzWithQuestions?.let { quizz ->
                Text(
                    text = quizz.quizz.title,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = quizz.quizz.description,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        items(quizzWithQuestions?.questions ?: emptyList()) { question ->
            QuestionCard(
                question = question,
                onQuestionChange = { updatedQuestion ->
                    // Update the question in the list
                    quizzWithQuestions = quizzWithQuestions?.let { quizz ->
                        val updatedQuestions = quizz.questions.map { if (it == question) updatedQuestion else it }
                        quizz.copy(questions = updatedQuestions)
                    }
                }
            )
            Divider(modifier = Modifier.fillMaxWidth())
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { addQuestion() },
                    modifier = Modifier
                        .padding(start = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Question"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { saveQuestions() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp)
                ) {
                    Text(text = "Save Questions")
                }
            }
        }
    }
}




