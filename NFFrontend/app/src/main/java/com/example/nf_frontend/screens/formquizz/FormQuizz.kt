package com.example.nf_frontend.screens.formquizz

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nf_frontend.MainActivity
import com.example.nf_frontend.data.questions.QuestionEntity
import com.example.nf_frontend.data.quizzes.QuizzEntity
import com.example.nf_frontend.data.delay.setInexactAlarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormQuizz(code: String, navController: NavController, context: Context) {
    var quizzTitle by remember { mutableStateOf("") }
    var quizzDescription by remember { mutableStateOf("") }

    // Variables d'état pour le délai en jours, heures et minutes
    var delayDays by remember { mutableStateOf(3) }
    var delayHours by remember { mutableStateOf(8) }
    var delayMinutes by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding()
            .fillMaxWidth()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp),
    ) {

        Column(
            modifier = Modifier
                .padding()
                .fillMaxWidth()
                .padding(top = 10.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Formulaire de création d'un quizz",
                style = MaterialTheme.typography.displaySmall,
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Titre",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            value = quizzTitle,
            onValueChange = { quizzTitle = it },
            placeholder = {
                Text(
                    text = "ex. Les Bases de la logique",
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic
                )
            },
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            value = quizzDescription,
            onValueChange = { quizzDescription = it },
            placeholder = {
                Text(
                    text = "ex. Questionnaire concernant le cours X, ...",
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic
                )
            },
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.025f))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Modifier le moment du rappel",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.015f))

            Text(
                text = "Veuillez choisir le nombre de jours dans lequel vous aurez le rappel ainsi que l'heure du rappel",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.01f))

            Text(
                text = "ex. Jours:3 Heure:8 Minutes:0 -> Le rappel s'activera à 08h00 dans 3 jours",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic)
            )
        }



        Spacer(modifier = Modifier.fillMaxHeight(0.025f))



        // Utilisation de Row et Column pour organiser les éléments
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Jours",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.fillMaxWidth(0.27f))

                    // Bouton de décrémentation pour les jours
                    Button(
                        onClick = { if (delayDays > 0) delayDays-- },
                        modifier = Modifier.padding(end = 4.dp),
                        colors = ButtonDefaults.buttonColors(Color.Gray)
                    ) {
                        Text(text = "<")
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(top = 4.dp),
                            value = delayDays.toString(),
                            onValueChange = { delayDays = it.toIntOrNull() ?: 0 },
                        )
                    }

                    // Bouton de d'incrémentation pour les jours
                    Button(
                        onClick = { delayDays++ },
                        modifier = Modifier.padding(end = 4.dp),
                        colors = ButtonDefaults.buttonColors(Color.Gray)
                    ) {
                        Text(text = ">")
                    }
                    Spacer(modifier = Modifier.fillMaxWidth(0.65f))
                }
            }


            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Heure",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.fillMaxWidth(0.27f))

                    // Bouton de décrémentation pour les heures
                    Button(
                        onClick = { if (delayHours > 0) delayHours-- },
                        modifier = Modifier.padding(end = 4.dp),
                        colors = ButtonDefaults.buttonColors(Color.Gray)
                    ) {
                        Text(text = "<")
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(top = 4.dp),
                            value = delayHours.toString(),
                            onValueChange = { delayHours = it.toIntOrNull() ?: 0 },
                        )
                    }

                    // Bouton de d'incrémentation pour les heures
                    Button(
                        onClick = { delayHours++ },
                        modifier = Modifier.padding(end = 4.dp),
                        colors = ButtonDefaults.buttonColors(Color.Gray)
                    ) {
                        Text(text = ">")
                    }
                    Spacer(modifier = Modifier.fillMaxWidth(0.65f))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Minutes",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.fillMaxWidth(0.27f))

                    // Bouton de décrémentation pour les minutes
                    Button(
                        onClick = { if (delayMinutes > 0) delayMinutes-- },
                        modifier = Modifier.padding(end = 4.dp),
                        colors = ButtonDefaults.buttonColors(Color.Gray)
                    ) {
                        Text(text = "<")
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(top = 4.dp),
                            value = delayMinutes.toString(),
                            onValueChange = { delayMinutes = it.toIntOrNull() ?: 0 },
                        )
                    }

                    // Bouton de d'incrémentation pour les jours
                    Button(
                        onClick = { delayMinutes++ },
                        modifier = Modifier.padding(end = 4.dp),
                        colors = ButtonDefaults.buttonColors(Color.Gray)
                    ) {
                        Text(text = ">")
                    }
                    Spacer(modifier = Modifier.fillMaxWidth(0.65f))
                }

            }

        }


        Spacer(modifier = Modifier.fillMaxHeight(0.025f))

        Button(
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally),
            enabled = quizzTitle.isNotBlank() && quizzDescription.isNotBlank(),
            onClick = {
                val newQuizz = QuizzEntity(
                    title = quizzTitle,
                    description = quizzDescription,
                    courseLinkedId = code
                )
                var quizzId: Long = -1
                Log.d("     FORM QUIZZ      ", "Jours: $delayHours | Hours : $delayHours | Minutes : $delayMinutes")
                CoroutineScope(Dispatchers.IO).launch {
                    quizzId = MainActivity.database.quizzDao().insertQuizz(newQuizz)
                    setInexactAlarm(
                        context,
                        delayDays,
                        delayHours,
                        delayMinutes,
                        code,
                        quizzTitle,
                        quizzDescription,
                        quizzId
                    )
                }
                navController.popBackStack()
            }
        ) {
            Text(text = "Ajouter")
        }
    }
}
