package com.example.nf_frontend.screens.coursequizz.composant.addquizz

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.courses.CourseWithQuizzes
import com.example.nf_frontend.data.quizzes.QuizzEntity
import com.example.nf_frontend.screens.homescreen.composant.addcourse.AddCourseForm
import com.example.nf_frontend.screens.quizz.PopupRedirect

@Composable
fun ListOfQuizzes(code : String, navController: NavController){

    var courseWithQuizzes by remember { mutableStateOf<List<CourseWithQuizzes>>(emptyList())}
    var quizzes by remember { mutableStateOf<List<QuizzEntity>>(emptyList())}

    LaunchedEffect(Unit) {
        MainActivity.database.courseDao().getCourseWithQuizzes().collect {
            updatedData ->
            run {
                courseWithQuizzes = updatedData.filter { cwq -> cwq.course.code == code }
                if (courseWithQuizzes[0].quizzes.isNotEmpty()) {
                    quizzes = courseWithQuizzes[0].quizzes
                }
            }
        }
    }


    if (quizzes.isNotEmpty()){
        LazyColumn(Modifier.padding(10.dp)) {
            items(quizzes) { quizz ->
                Quizz(
                    quizz,
                    navController
                )
            }
        }
    } else {
        Text(text = "Il n'y a aucun questionnaire disponible pour ce cours." +
                " Veuillez en ajouter un \uD83D\uDE00.",
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentHeight(),
        )
    }
}

@Composable
fun Quizz(quizz:QuizzEntity, navController: NavController) {
    var isFormVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(width = 1.dp, color = Color.Gray, shape = MaterialTheme.shapes.medium)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
//                  navController.navigate("quizzes/${quizzId}")
                    isFormVisible = true
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = quizz.title, style = MaterialTheme.typography.titleMedium)
                Text(text = quizz.description,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray)
            }
        }
    }

    if (isFormVisible) {
        Dialog(
            onDismissRequest = { isFormVisible = false }
        ) {
            PopupRedirect(
                quizz,
                onDismiss = { isFormVisible = false },
                navController
            )
        }
    }
}
