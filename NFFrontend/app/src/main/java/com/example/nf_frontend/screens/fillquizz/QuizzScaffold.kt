package com.example.nf_frontend.screens.fillquizz

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.quizzes.QuizzWithQuestions


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizzScaffold (
//    listOfQuestions : List<QuestionEntity>,
    quizzId: Long,
    mainNavController: NavController
){
    var quizzWithQuestions by remember { mutableStateOf<QuizzWithQuestions?>(null) }
    LaunchedEffect(quizzId) {
        MainActivity.database.quizzDao().getQuizzWithQuestionsById(quizzId).collect { quizz ->
            quizzWithQuestions = quizz
        }
    }
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
                    idx -> QuizzActivity(quizzId = idx.arguments?.getLong("quizzId")!!,
                    mainNavController, navController)
                }
                composable("completeQuizz/{quizzId}/{indexQuestion}?score={score}", arguments = listOf(
                    navArgument("quizzId"){
                        type = NavType.LongType;
                        defaultValue = quizzId
                    },
                    navArgument("indexQuestion"){
                        type = NavType.IntType;
                        defaultValue = 0
                    },
                    navArgument("score"){
                        defaultValue = 0
                    }
                )){
                    idx -> QuizzPage(
                    quizzId = idx.arguments?.getLong("quizzId")!!,
                    indexQuestion = idx.arguments?.getInt("indexQuestion")!!,
                    idx.arguments?.getInt("score")!!,
                    navController)
                }
                composable("completeQuizz/end/{quizz}/{score}", arguments = listOf(
                    navArgument("quizzId"){
                        type = NavType.LongType;
                        defaultValue = quizzId
                    },
                    navArgument("score"){
                        type = NavType.IntType;
                        defaultValue = 0
                    })){
                    backStackEntry ->
                    QuizzEndPage(score = backStackEntry.arguments?.getInt("score")!!,
                        quizzId = backStackEntry.arguments?.getLong("quizzId")!!)
                }

            }
        }
  }
}

@Composable
fun HelloWorld() {
    Text("Hello World")
}
