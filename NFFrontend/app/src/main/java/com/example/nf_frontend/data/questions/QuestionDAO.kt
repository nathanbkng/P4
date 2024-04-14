package com.example.nf_frontend.data.questions

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nf_frontend.data.quizzes.QuizzEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateQuestion(question: QuestionEntity)

    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)

    @Query("SELECT * FROM questions WHERE quizzLinkedId = :quizzId")
    suspend fun getQuestionsForQuiz(quizzId: Long): List<QuestionEntity>

    @Query("SELECT * FROM questions WHERE questionId = :questionId")
    fun getQuestionById(questionId: Long): Flow<QuestionEntity?>

    @Query("SELECT COUNT(*) FROM questions WHERE quizzLinkedId = :quizzId")
    fun getQuestionCountForQuizz(quizzId: Long): Flow<Int>
}