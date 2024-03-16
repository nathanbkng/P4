package com.example.nf_frontend

import androidx.room.Room
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nf_frontend.data.AppDatabase
import com.example.nf_frontend.data.courses.CourseEntity
import com.example.nf_frontend.ui.theme.NFFrontendTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

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
            idx -> CourseDetail(code = idx.arguments?.getString("code")!!)
        }
    }
}

@Composable
fun CourseDetail(code : String) {
    Column(
        modifier = Modifier.padding()
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
        AddQuizz()
    }
    Spacer(modifier = Modifier.height(12.dp))
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
        ButtonRow()
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
            style = MaterialTheme.typography.displayLarge,
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


@Composable
fun AddCourseButton( onAddCourse: (CourseEntity) -> Unit ) {

    var isFormVisible by remember { mutableStateOf(false) }

    Column {
        IconButton(
            onClick = { isFormVisible = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(8.dp)
                )
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

        if (isFormVisible) {
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
        }
    }
}

@Composable
fun AddQuizz() {

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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Add, contentDescription = "Ajouter", tint = Color.White)
                Text(
                    text = "Ajouter un Quizz",
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

//        if (isFormVisible) {
//            Dialog(
//                onDismissRequest = { isFormVisible = false }
//            ) {
//                AddCourseForm(
//                    onAddCourse = { course ->
//                        onAddCourse(course)
//                        isFormVisible = false
//                    },
//                    onDismiss = { isFormVisible = false }
//                )
//            }
//        }
    }
}

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