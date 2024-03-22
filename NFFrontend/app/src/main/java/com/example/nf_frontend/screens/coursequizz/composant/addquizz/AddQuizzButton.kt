package com.example.nf_frontend.screens.coursequizz.composant.addquizz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddQuizzButton(code:String, navController: NavController) {

    var isFormVisible by remember { mutableStateOf(false) }

    Column {
        IconButton(
            onClick = { isFormVisible = true },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { navController.navigate("createQuizz/${code}")  }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Ajouter", tint = Color.White)
                Text(
                    text = "Ajouter un Quizz",
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        /*if (isFormVisible) {
            Dialog(
                onDismissRequest = { isFormVisible = false }
            ) {
                AddCourseForm(
                    onAddCourse = { course ->
                        onAddCourse(course)
                        isFormVisible = false
                    },
                    onDismiss = { isFormVisible = false }
                )
            }
        }*/
    }
}

