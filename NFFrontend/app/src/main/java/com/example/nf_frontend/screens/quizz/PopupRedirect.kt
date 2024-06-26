package com.example.nf_frontend.screens.quizz


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.quizzes.QuizzEntity
import com.example.nf_frontend.data.scores.ScoreEntity


@Composable
fun PopupRedirect(
    quizz: QuizzEntity,
    onDismiss: () -> Unit,
    navController: NavController
) {
    var countQuestions by remember { mutableIntStateOf(0) }
    var bestscore by remember { mutableStateOf<ScoreEntity?>(null) }

   LaunchedEffect(quizz) {
        MainActivity.database.questionDao().getQuestionCountForQuizz(quizz.quizzId).collect {
            countQuestions = it
        }
    }

    LaunchedEffect(quizz) {
        MainActivity.database.scoreDao().getBestScoreForQuizz(quizz.quizzId).collect {
            bestscore = it
        }
    }

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(Modifier.padding(20.dp)) {
            Text(
                text = "Vous avez sélectionné le questionnaire : \n\"${quizz.title}\",\n que voulez-vous faire pour ce questionnaire ?" +
                        if (bestscore!= null ) "\n\n Votre meilleur score est de : \n ${bestscore!!.score}/${bestscore!!.totalGrade} \n (${String.format("%.${2}f", ((bestscore!!.score/ bestscore!!.totalGrade)*100))}%) " else "",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.wrapContentSize(align = Alignment.Center),
                textAlign = TextAlign.Center
            )


            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(Alignment.Center)
                    .width(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if (countQuestions > 0){
                    Button(
                        colors = ButtonDefaults.buttonColors(Color(0xFF00BFA5)),
                        onClick = {
                            navController.navigate("startQuizz/${quizz.quizzId}")
                            onDismiss()
                        },
                    ) {
                        Text(text = "Compléter le questionnaire!")
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
                Button(
                    onClick = {
                        navController.navigate("questionsForm/${quizz.quizzId}")
                        onDismiss()
                    },
                ) {
                    Text(text = "Ajouter des questions")
                }
                Button(
                    onClick = {
                        navController.navigate("quizzes/${quizz.quizzId}")
                        onDismiss()
                    },
                ) {
                    Text(text = "Afficher les questions")
                }
            }
        }

    }
}