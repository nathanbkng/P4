package com.example.nf_frontend.screens.fillquizz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.quizzes.QuizzWithQuestions
import com.example.nf_frontend.data.scores.ScoreEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun QuizzEndPage(score : Int, quizzId: Long, previousNavController: NavController) {
    var currentQuizzWQ by remember { mutableStateOf<QuizzWithQuestions?>(null) }

    LaunchedEffect(quizzId) {
        MainActivity.database.quizzDao().getQuizzWithQuestionsById(quizzId).collect {
                qwq ->
            currentQuizzWQ = qwq!!
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally, // Aligns children horizontally
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(title = currentQuizzWQ?.quizz?.title)
        Body(score, currentQuizzWQ?.questions?.size)
        Footer(
            previousNavController,
            score, currentQuizzWQ?.questions?.size,quizzId
        ) { score ->
            CoroutineScope(Dispatchers.IO).launch {
                MainActivity.database.scoreDao().insertScore(score)
            }
        }
    }
}

@Composable
fun Body(score: Int, total: Int?){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Vous avez fini le questionnaire.\n",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Note finale sur: \n",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "${score}/${total}",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun Footer(
    previousNavController: NavController,
    score: Int, total: Int?, quizzId: Long,
    onAddScore: (ScoreEntity) -> Unit
           ){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    val newScore = ScoreEntity(
                        score = score.toDouble(),
                        totalGrade = total!!,
                        quizzLinkedId = quizzId
                    )
                    onAddScore(newScore)
                    previousNavController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF8BC34A)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Retour à la liste des questionnaires",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}