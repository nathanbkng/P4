package com.example.nf_frontend.screens.formquizz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.questions.QuestionEntity
import com.example.nf_frontend.data.quizzes.QuizzEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormQuizz(code : String, navController: NavController){
    var quizzTitle by remember { mutableStateOf("") }
    var quizzDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding()
            .fillMaxWidth()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = "Formulaire de création d'un quizz",
            style = MaterialTheme.typography.displaySmall,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Titre",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            value = quizzTitle,
            onValueChange = { quizzTitle = it },
            placeholder =
            {
                Text(
                    text = "ex. Les Bases de la logique",
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic)
            },
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            value = quizzDescription,
            onValueChange = { quizzDescription = it },
            placeholder =
            {
                Text(
                    text = "ex. Questionnaire concernant le cours X, ...",
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic)
            },
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            colors = ButtonDefaults.buttonColors(Color.Black) ,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally),
            enabled = quizzTitle.isNotBlank() && quizzDescription.isNotBlank(),
            onClick = {
                val newQuizz = QuizzEntity(
                    title = quizzTitle,
                    description = quizzDescription,
                    courseLinkedId = code
                )
                CoroutineScope(Dispatchers.IO).launch {
                    MainActivity.database.quizzDao().insertQuizz(newQuizz)
                }
                navController.popBackStack()
            }
        ) {
            Text(text = "Ajouter")
        }
    }
}