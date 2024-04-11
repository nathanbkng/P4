package com.example.nf_frontend.screens.homescreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.nf_frontend.screens.formquizz.FormQuizz
import com.example.nf_frontend.screens.homescreen.composant.ButtonRow
import com.example.nf_frontend.screens.homescreen.composant.ListOfCourses
import com.example.nf_frontend.screens.homescreen.composant.TopBar
import com.example.nf_frontend.screens.homescreen.composant.addcourse.AddCourseButton
import com.example.nf_frontend.screens.quizz.QuizzDetails
import com.example.nf_frontend.screens.quizz.QuizzScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(){
    val navController = rememberNavController()
    var courses by remember { mutableStateOf<List<CourseEntity>>(emptyList()) }
    var showDialog by rememberSaveable { mutableStateOf(true) }


    Log.d("test", courses.toString())
    NavHost(navController = navController, startDestination = "first_screen" ){
        composable("first_screen"){
            if (showDialog) {
                AlertDialog(
                    icon = { Icon(Icons.Outlined.Info , contentDescription = "Info") },
                    onDismissRequest = {
                        showDialog = false
                    },
                    title = {
                        Text(text = "Bienvenue 😁🤓")
                    },
                    text = {
                        Text(text = "Les membres de l'équipe NeverForget sont heureux de vous présenter une " +
                                "première version de l'application qui vous permettra de gérer votre étude continue au cours de vos " +
                                "différents quadrimestres.\n" +
                                "\n" +
                                "Dans la version actuelle, vous serez capable de créer différents questionnaires pour chacun de vos cours.\n" +
                                "\n" +
                                "Dans la prochaine version, vous pourrez vous entraîner à y répondre !\n" +
                                "(Cette fonctionnalité est en cours de développement)." )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog = false
                            },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("Ok")
                        }
                    }
                )
            }else{
                MesCourses(navController = navController)
            }
        }
        composable("course/{code}", arguments = listOf(
            navArgument("code"){
                type = NavType.StringType
            }
        )){
                idx -> CourseQuizzes(code = idx.arguments?.getString("code")!!, navController)
        }
        composable("createQuizz/{code}", arguments = listOf(
            navArgument("code"){
                type = NavType.StringType
            }
        )){
                idx -> FormQuizz(code = idx.arguments?.getString("code")!!, navController)
        }
        composable("questionsForm/{quizzId}", arguments = listOf(
            navArgument("quizzId"){
                type = NavType.LongType
            }
        )){
                idx -> QuizzScreen(quizzId = idx.arguments?.getLong("quizzId")!!, navController)
        }
        composable("quizzes/{quizzId}", arguments = listOf(
            navArgument("quizzId"){
                type = NavType.LongType
            }
        )){
                idx -> QuizzDetails(quizzId = idx.arguments?.getLong("quizzId")!!)
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
//            ButtonRow()
//            Spacer(modifier = Modifier.height(8.dp))
            AddCourseButton { course ->
                CoroutineScope(Dispatchers.IO).launch {
                    MainActivity.database.courseDao().insertCourse(course)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            ListOfCourses(navController)
        }
}