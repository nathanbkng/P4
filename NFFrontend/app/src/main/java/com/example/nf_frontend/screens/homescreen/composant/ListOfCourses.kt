package com.example.nf_frontend.screens.homescreen.composant

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.courses.CourseEntity

@Composable
fun ListOfCourses(navController: NavController) {
    var courses by remember { mutableStateOf<List<CourseEntity>>(emptyList()) }

    LaunchedEffect(Unit) {
        MainActivity.database.courseDao().getAllCourses().collect { updatedCourses ->
            courses = updatedCourses
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        courses.forEach { course ->
            Course(course.code, course.name, Color(course.color), navController)
        }
    }
}

@Composable
fun Course(code: String, name: String, color: Color, navController: NavController) {
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
                .clickable { navController.navigate("course/${code}") },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(color = color, shape = CircleShape),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = code, style = MaterialTheme.typography.titleMedium)
                Text(text = name, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}