package com.example.nf_frontend.screens.fillquizz

import android.util.Log
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.questions.QuestionEntity
import com.example.nf_frontend.data.quizzes.QuizzEntity

@Composable
fun QuizzPage(quizzId: Long, indexQuestion: Int, score :Int , navController: NavHostController){

    var currentQuestion by remember { mutableStateOf<QuestionEntity?>(null) }
    var currentQuizz by remember { mutableStateOf<QuizzEntity?>(null) }
    var numberQuestionInQuizz by remember { mutableStateOf<Int>(0) }
    var userResponse : Boolean? by remember { mutableStateOf(null) }
    println("score now : $score")
    LaunchedEffect(quizzId) {
        MainActivity.database.quizzDao().getQuizzWithQuestionsById(quizzId).collect {
            qwq ->
            currentQuestion = qwq!!.questions.get(indexQuestion)
            currentQuizz = qwq!!.quizz
            numberQuestionInQuizz = qwq!!.questions.size
        }
    }
    Log.println(Log.DEBUG, "debug", currentQuizz.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
          ,
        horizontalAlignment = Alignment.CenterHorizontally, // Aligns children horizontally
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(title = currentQuizz?.title)
        BodyQuestion(
            currentQuestion?.questionText,
            indexQuestion,
            response = userResponse,
            onUserResponseChange = { newValue -> userResponse = newValue }
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (userResponse != null) {
            FooterQuestion(
                null,
                navController,
                indexQuestion,
                numberQuestionInQuizz,
                quizzId,
                userResponse!!,
                currentQuestion!!.isTrue,
                score
            )
        }
    }
}


@Composable
fun BodyQuestion(
    qText: String?,
    idx: Int,
    response: Boolean?,
    onUserResponseChange: (Boolean?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Text(
                text = "Question N⁰${idx+1}:",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box {
            Text(
                text = "$qText",
                style = MaterialTheme.typography.headlineLarge,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Spacer(modifier = Modifier.height(24.dp))
        ResponseRow(
            response = response,
            onUserResponseChange
        )
    }
}

@Composable
fun ResponseRow(
    response: Boolean?,
    onUserResponseChange: (Boolean?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
              onUserResponseChange(true)
            },
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(text = "True", color = Color.White)
        }
        Button(
            onClick = {
                onUserResponseChange(false)
            },
            modifier = Modifier
                .padding(4.dp)
                .weight(1f),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(text = "False", color = Color.White)
        }
    }
}
fun isQuizzFinished(indexQuestion: Int, totalNumber : Int) : Boolean{
    return indexQuestion + 1 >=totalNumber
}

@Composable
fun FooterQuestion(
    mainNavController: NavController?,
    navController: NavController,
    indexQuestion: Int,
    numberQuestionInQuizz: Int,
    quizzId: Long,
    userResponse: Boolean,
    correctRes: Boolean,
    score: Int
) {

    var finished : Boolean = isQuizzFinished(indexQuestion,numberQuestionInQuizz)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {

                    if (!finished){
                        navController.navigate("completeQuizz/"
                                + "${quizzId}/${indexQuestion+1}" +
                                "?score=${score + correctUserResponse(userResponse,correctRes)}")

                    }else{
                        navController.navigate("completeQuizz/end/${quizzId}/${score + correctUserResponse(userResponse,correctRes)}")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    if (!finished) Color(0xFFAB47BC) else Color(0xFFE65100)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = if (!finished) "Prochaine question" else "Finir le questionnaire",
                    fontSize = 24.sp
                )
            }
        }
    }
}

fun correctUserResponse(userResponse: Boolean, correctRes: Boolean): Int {
    return if (userResponse == correctRes) 1 else 0
}
