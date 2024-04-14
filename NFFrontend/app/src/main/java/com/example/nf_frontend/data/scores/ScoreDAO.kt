package com.example.nf_frontend.data.scores

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDAO {
    @Insert
    suspend fun insertScore(scoreEntity: ScoreEntity)

    @Query("SELECT * FROM scores WHERE quizzLinkedId = :quizzId")
    fun getScoresForQuizz(quizzId: Long): List<ScoreEntity>
}