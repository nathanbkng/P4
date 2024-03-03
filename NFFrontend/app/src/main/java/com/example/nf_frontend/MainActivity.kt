package com.example.nf_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nf_frontend.ui.theme.NFFrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NFFrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopBar()
                        Spacer(modifier = Modifier.height(12.dp))
                        ButtonRow()
                        Spacer(modifier = Modifier.height(8.dp))
                        AddCourseButton()
                        Spacer(modifier = Modifier.height(8.dp))
                        ListOfCourses()
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Mes cours",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Person, contentDescription = null)
        }
    }
}

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
                .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
        ) {
            Text(text = "Ma liste de cours")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(1f)
                .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
        ) {
            Text(text = "Planning")
        }
    }
}

@Composable
fun AddCourseButton() {
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Add, contentDescription = "Ajouter", tint = Color.White)
            Text(
                text = "Ajouter un cours",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun ListOfCourses() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Example courses
        Course("LINFO1361", "Intelligence Artificielle", Color.Blue)
        Course("LINFO1341", "Réseaux", Color.Green)
        Course("LINFO2172", "Databases", Color.Red)
    }
}

@Composable
fun Course(code: String, name: String, color: Color) {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NFFrontendTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar()
            Spacer(modifier = Modifier.height(16.dp))
            ButtonRow()
            Spacer(modifier = Modifier.height(16.dp))
            AddCourseButton()
            Spacer(modifier = Modifier.height(16.dp))
            ListOfCourses()
        }
    }
}