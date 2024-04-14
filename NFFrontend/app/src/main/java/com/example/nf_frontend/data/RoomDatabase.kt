package com.example.nf_frontend.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nf_frontend.data.courses.CourseDao
import com.example.nf_frontend.data.courses.CourseEntity
import com.example.nf_frontend.data.questions.QuestionDAO
import com.example.nf_frontend.data.questions.QuestionEntity
import com.example.nf_frontend.data.quizzes.QuizzDAO
import com.example.nf_frontend.data.quizzes.QuizzEntity
import com.example.nf_frontend.data.scores.ScoreDAO
import com.example.nf_frontend.data.scores.ScoreEntity

@Database(
    entities = [CourseEntity::class,
                QuizzEntity::class,
                QuestionEntity::class,
                ScoreEntity::class
               ],
    version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun quizzDao(): QuizzDAO
    abstract fun questionDao(): QuestionDAO
    abstract fun scoreDao(): ScoreDAO
}
