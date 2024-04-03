package com.example.nf_frontend.screens.quizz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nf_frontend.MainActivity

import com.example.nf_frontend.data.quizzes.QuizzWithQuestions


@Composable
fun QuizzDetails(quizzId: Long){
    var quizzWithQuestions by remember { mutableStateOf<QuizzWithQuestions?>(null) }
    var areAnswersVisible by remember { mutableStateOf(false) }
    LaunchedEffect(quizzId) {
        MainActivity.database.quizzDao().getQuizzWithQuestionsById(quizzId).collect { quizz ->
            quizzWithQuestions = quizz
        }
    }
    
//    Text(text = quizzWithQuestions.toString())

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
                if (quizzWithQuestions?.questions?.isEmpty() == false){
                    IconButton(
                        onClick = { areAnswersVisible = !areAnswersVisible },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(Icons.Outlined.Visibility, contentDescription = "dévoiler", tint = Color.White)
                            Text(
                                text = "Afficher les réponses",
                                color = Color.White,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
                )
            }
        }
         item {

            quizzWithQuestions?.let { quizz ->
                if (quizz.questions.isEmpty()){
                    Text(
                        text = "Il n'y a aucune question ajouté à ce questionnaire.",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        itemsIndexed(quizzWithQuestions?.questions ?: emptyList()) {
                idx, question ->
            Text(
                text = "Question ${idx+1}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.titleLarge
            )
            if (areAnswersVisible){
                Text(
                    text = "Réponse : ${if (question.isTrue) "Vrai" else "Faux"}",
                    fontStyle = FontStyle.Italic,
                    color = Color.Blue
                )
            }
            Divider(
                thickness = 5.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )

        }
    }
}