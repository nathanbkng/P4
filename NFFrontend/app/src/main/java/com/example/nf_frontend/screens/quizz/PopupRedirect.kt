package com.example.nf_frontend.screens.quizz


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nf_frontend.data.quizzes.QuizzEntity

@Composable
fun PopupRedirect(
    quizz: QuizzEntity,
    onDismiss: () -> Unit,
    navController: NavController
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = "Vous avez sélectionné le questionnaire : \"${quizz.title}\", que voulez-vous faire pour ce questionnaire ?",
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