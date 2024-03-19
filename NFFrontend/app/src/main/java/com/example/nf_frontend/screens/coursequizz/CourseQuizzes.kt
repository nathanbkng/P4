package com.example.nf_frontend.screens.coursequizz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nf_frontend.screens.coursequizz.composant.addquizz.AddQuizzButton
import com.example.nf_frontend.screens.coursequizz.composant.addquizz.ListOfQuizzes

@Composable
fun CourseQuizzes(code : String) {
    Column(
        modifier = Modifier
            .padding()
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = "${code}",
            style = MaterialTheme.typography.displayLarge,
        )
        Text(
            text = "Voici les questionnaires que vous avez créé pour le cours ${code}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))
        // AddQuizzButton()
        Divider(
            thickness = 1.dp,
            color = Color.Black,
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
        )
        Text(
            text = "Liste des questionnaires ajoutés:",
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
        ListOfQuizzes(code)
    }
    Spacer(modifier = Modifier.height(12.dp))
}