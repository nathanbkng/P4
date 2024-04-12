package com.example.nf_frontend.screens.fillquizz

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizzScaffold (
//    listOfQuestions : List<QuestionEntity>,
    quizzId: Long,
    mainNavController: NavController
){
    Log.println(Log.DEBUG, "test","$quizzId")
    val navController: NavHostController = rememberNavController()
    Scaffold {
        paddingValues ->
        Surface (
            modifier = Modifier.padding(paddingValues)
        ){
            NavHost(
                navController = navController,
                startDestination = "completeQuizz/{quizzId}"
            ){
                composable("completeQuizz/{quizzId}", arguments = listOf(
                    navArgument("quizzId"){
                        type = NavType.LongType;
                        defaultValue = quizzId
                    }
                )){
                    idx -> QuizzActivity(quizzId = idx.arguments?.getLong("quizzId")!!, mainNavController)
                }
            }
        }
  }
}

@Composable
fun HelloWorld() {
    Text("Hello World")
}
