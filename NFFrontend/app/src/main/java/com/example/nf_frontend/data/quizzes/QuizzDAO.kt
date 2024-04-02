package com.example.nf_frontend.data.quizzes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface QuizzDAO{
    
    @Insert
    suspend fun insertQuizz(quizz: QuizzEntity)

    @Query("SELECT * FROM quizzes WHERE quizzId = :id")
    fun selectByQuizzId(id: Long) : Flow<List<QuizzEntity>>
}