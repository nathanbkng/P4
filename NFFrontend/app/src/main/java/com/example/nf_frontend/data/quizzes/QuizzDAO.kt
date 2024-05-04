package com.example.nf_frontend.data.quizzes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.nf_frontend.data.questions.QuestionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface QuizzDAO{
    @Insert
    suspend fun insertQuizz(quizz: QuizzEntity):Long

    @Delete
    suspend fun deleteQuizz(quizz: QuizzEntity)

    @Query("SELECT * FROM quizzes WHERE quizzId = :quizzId")
    fun getQuizzById(quizzId: Long): Flow<QuizzEntity?>

    @Query("SELECT * FROM quizzes WHERE quizzId = :quizzId")
    fun getQuizzWithQuestionsById(quizzId: Long): Flow<QuizzWithQuestions?>

    @Query("SELECT * FROM quizzes WHERE courseLinkedId = :courseCode")
    suspend fun getQuizzesForCourse(courseCode: String): List<QuizzEntity>
}