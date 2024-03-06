package com.example.nf_frontend

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.nf_frontend.data.courses.CourseEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCourseForm(
    onAddCourse: (CourseEntity) -> Unit,
    onDismiss: () -> Unit
) {
    var courseCode by remember { mutableStateOf("") }
    var courseName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .width(300.dp)
    ) {
        Text(text = "Ajouter un cours", style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = courseCode,
            onValueChange = { courseCode = it },
            label = { Text("Code du cours") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = courseName,
            onValueChange = { courseName = it },
            label = { Text("Nom du cours") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    val newCourse = CourseEntity(code = courseCode, name = courseName, color = Color.Blue.toArgb())
                    onAddCourse(newCourse)
                    onDismiss()
                },
                enabled = courseCode.isNotBlank() && courseName.isNotBlank()
            ) {
                Text(text = "Ajouter")
            }
        }
    }
}

