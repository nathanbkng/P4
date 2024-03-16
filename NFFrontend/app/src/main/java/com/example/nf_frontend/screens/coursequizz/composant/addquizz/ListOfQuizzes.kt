package com.example.nf_frontend.screens.coursequizz.composant.addquizz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.courses.CourseEntity
import com.example.nf_frontend.data.courses.CourseWithQuizzes
import com.example.nf_frontend.data.quizzes.QuizzEntity

@Composable
fun ListOfQuizzes(code : String){

    var courseWithQuizzes by remember { mutableStateOf<List<CourseWithQuizzes>>(emptyList())}
    var quizzes by remember { mutableStateOf<List<QuizzEntity>>(emptyList())}

    LaunchedEffect(Unit) {
        MainActivity.database.courseDao().getCourseWithQuizzes().collect {
            updatedData ->
            run {
                courseWithQuizzes = updatedData.filter { cwq -> cwq.course.code == code }
                print(">> ${courseWithQuizzes}")
                if (courseWithQuizzes[0].quizzes.isNotEmpty()) {
                    quizzes = courseWithQuizzes[0].quizzes
                }
            }
        }
    }

//    println("${ courseWithCourses}")

//    print("${code} // ${courseWithCourses}")
    if (quizzes.isNotEmpty()){
        quizzes.forEach {
                quizz ->  Quizz(quizz.title, quizz.description)
        }
    }else{


        Text(text = "Il n'y a aucun questionnaire disponible pour ce cours." +
                " Veuillez en ajouter un \uD83D\uDE00.",
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentHeight(),

        )
    }
    
}

@Composable
fun Quizz(title: String, description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(width = 1.dp, color = Color.Gray, shape = MaterialTheme.shapes.medium)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = description,
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray)
            }
        }
    }
}
