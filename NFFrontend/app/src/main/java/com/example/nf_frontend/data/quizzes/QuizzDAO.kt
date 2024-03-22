package com.example.nf_frontend.data.quizzes

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface QuizzDAO{
    
    @Insert
    suspend fun insertQuizz(quizz: QuizzEntity)

}