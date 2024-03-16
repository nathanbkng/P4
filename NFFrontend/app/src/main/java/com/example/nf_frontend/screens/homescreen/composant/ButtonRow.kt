package com.example.nf_frontend.screens.homescreen.composant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
        ) {
            Text(text = "Ma liste de cours")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
        ) {
            Text(text = "Planning")
        }
    }
}

