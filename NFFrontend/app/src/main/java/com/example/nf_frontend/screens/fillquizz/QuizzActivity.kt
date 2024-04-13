package com.example.nf_frontend.screens.fillquizz

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.nf_frontend.MainActivity

import com.example.nf_frontend.data.quizzes.QuizzWithQuestions


@Composable
fun QuizzActivity(
    quizzId: Long,
    mainNavController: NavController,
    navController: NavHostController){
    var quizzWithQuestions by remember { mutableStateOf<QuizzWithQuestions?>(null) }
    LaunchedEffect(quizzId) {
        MainActivity.database.quizzDao().getQuizzWithQuestionsById(quizzId).collect { quizz ->
            quizzWithQuestions = quizz
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp, bottom = 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // Aligns children horizontally

        verticalArrangement = Arrangement.SpaceBetween
    ) {
        quizzWithQuestions.let { quizzWithQuestions ->
            Header(quizzWithQuestions?.quizz?.title)
            Body(quizzWithQuestions?.questions?.size)
            Footer(mainNavController,navController,quizzWithQuestions)
        }
    }
}

@Composable
fun Header(title:String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(alignment = Alignment.Center),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Footer(mainNavController: NavController,navController: NavController, quizzWithQuestions: QuizzWithQuestions?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = { mainNavController.popBackStack() },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(end = 8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Quit Quizz"
                    )
            }

            Button(
                onClick = {
                    quizzWithQuestions.let { q ->
                        var nextQuestionId = q?.questions?.get(0)?.questionId
                        Log.println(Log.DEBUG, "debug", ">>> here : $nextQuestionId")
                        navController.navigate(
                            "completeQuizz/" +
                                    "${q?.quizz?.quizzId}/${0}")
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(0XFF039dfc)),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Text(
                    text = "Commencer!",
                    fontSize = 30.sp
                )
            }
        }
    }
}

@Composable
fun Body(numberQuestion : Int?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Vous êtes sur le point de commencer le questionnaire.\n",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Ce dernier est composé ${
                if (numberQuestion != null && numberQuestion > 1) "de $numberQuestion questions"
                else "d'une question"
            }.\n",
            style = MaterialTheme.typography.headlineMedium,

            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Note finale sur: \n",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = "/${numberQuestion}",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
