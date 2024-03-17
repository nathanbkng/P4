package com.example.nf_frontend

import androidx.room.Room
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.nf_frontend.data.AppDatabase
import com.example.nf_frontend.data.courses.CourseEntity
import com.example.nf_frontend.screens.homescreen.HomeScreen
import com.example.nf_frontend.screens.homescreen.composant.ButtonRow
import com.example.nf_frontend.screens.homescreen.composant.TopBar
import com.example.nf_frontend.screens.homescreen.composant.addcourse.AddCourseForm
import com.example.nf_frontend.ui.theme.NFFrontendTheme

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "my-database"
        ).build()

        setContent {
            NFFrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        HomeScreen()
                    }
                }
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
//            AddCourseButton()
            Spacer(modifier = Modifier.height(16.dp))
//            ListOfCourses()
        }
    }
}