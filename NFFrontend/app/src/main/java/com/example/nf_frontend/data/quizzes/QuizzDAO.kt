package com.example.nf_frontend.data.quizzes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface QuizzDAO{
    
    @Insert
    suspend fun insertQuizz(quizz: QuizzEntity)

    @Query("SELECT * FROM quizzes WHERE quizzId = :id")
    suspend fun selectByQuizzId(id: Long)
}