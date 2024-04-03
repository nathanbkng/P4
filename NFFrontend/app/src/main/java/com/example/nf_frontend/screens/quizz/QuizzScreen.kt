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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
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
    var newQuestions by remember { mutableStateOf< List<QuestionEntity>>(emptyList()) }

    LaunchedEffect(quizzId) {
        MainActivity.database.quizzDao().getQuizzWithQuestionsById(quizzId).collect { quizz ->
            quizzWithQuestions = quizz
        }
    }

    // Function to add a new question to the list
    fun addQuestion() {
        val newQuestion = QuestionEntity(questionText = "", isTrue = true, quizzLinkedId = quizzId)
        val updatedQuestions = newQuestions.toMutableList().apply { add(newQuestion) }
        newQuestions = updatedQuestions
    }

    // Function to save modified questions to the database
    fun saveQuestions() {
        newQuestions.forEach { question ->
            if (question.questionText.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    MainActivity.database.questionDao().insertOrUpdateQuestion(question)
                }
            }
        }
        navController.popBackStack()
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
                Divider(
                    thickness = 1.dp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                )
            }

        }


        if (quizzWithQuestions?.questions?.isEmpty() == true){
            item{
                Text(
                    text = "Le questionnaire est vide, veuillez ajouter de nouvelles questions!",
                    fontStyle = FontStyle.Italic
                )
            }
        }else{
            item {
                Text(
                    text = "Liste des questions déjà comprise dans les questionnaires:",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            items(quizzWithQuestions?.questions ?: emptyList()) {q ->
                Text(text = "•  ${q.questionText}", fontStyle = FontStyle.Italic)
            }
        }
        item{
            Divider(
                thickness = 1.dp,
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
            Text(text = "Création de nouvelles question:", style = MaterialTheme.typography.headlineSmall)
        }


        items(newQuestions) { question ->
            QuestionCard(
                question = question,
                onQuestionChange = { updatedQuestion ->
                    // Update the question in the list
                    val updatedQuestions = newQuestions.map { if (it == question) updatedQuestion else it }
                    newQuestions = updatedQuestions
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




