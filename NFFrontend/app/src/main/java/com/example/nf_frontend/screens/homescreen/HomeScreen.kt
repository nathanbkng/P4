package com.example.nf_frontend.screens.homescreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.courses.CourseEntity
import com.example.nf_frontend.screens.coursequizz.CourseQuizzes
import com.example.nf_frontend.screens.homescreen.composant.ButtonRow
import com.example.nf_frontend.screens.homescreen.composant.ListOfCourses
import com.example.nf_frontend.screens.homescreen.composant.TopBar
import com.example.nf_frontend.screens.homescreen.composant.addcourse.AddCourseButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(){
    val navController = rememberNavController()
    var courses by remember { mutableStateOf<List<CourseEntity>>(emptyList()) }

    Log.d("test", courses.toString())
    NavHost(navController = navController, startDestination = "first_screen" ){
        composable("first_screen"){
            MesCourses(navController = navController)
        }
        composable("course/{code}", arguments = listOf(
            navArgument("code"){
                type = NavType.StringType
            }
        )
        ){
                idx -> CourseQuizzes(code = idx.arguments?.getString("code")!!)
        }
    }
}

@Composable
fun MesCourses(navController: NavController){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(12.dp))
        // ButtonRow()
        Spacer(modifier = Modifier.height(8.dp))
        AddCourseButton { course ->
            CoroutineScope(Dispatchers.IO).launch {
                MainActivity.database.courseDao().insertCourse(course)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        ListOfCourses(navController)
    }
}