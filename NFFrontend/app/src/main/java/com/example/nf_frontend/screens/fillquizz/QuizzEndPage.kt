package com.example.nf_frontend.screens.fillquizz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.quizzes.QuizzWithQuestions

@Composable
fun QuizzEndPage(score : Int, quizzId: Long) {
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
        Spacer(modifier = Modifier.height(12.dp))
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