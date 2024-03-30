package com.example.nf_frontend.data.questions

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateQuestion(question: QuestionEntity)

    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)

    @Query("SELECT * FROM questions WHERE quizzLinkedId = :quizzId")
    suspend fun getQuestionsForQuiz(quizzId: Long): List<QuestionEntity>
}