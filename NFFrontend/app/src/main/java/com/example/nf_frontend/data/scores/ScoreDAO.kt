package com.example.nf_frontend.data.scores

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDAO {
    @Insert
    suspend fun insertScore(scoreEntity: ScoreEntity)

    @Query("SELECT * FROM scores WHERE quizzLinkedId = :quizzId")
    fun getScoresForQuizz(quizzId: Long): Flow<List<ScoreEntity>>

    @Query("SELECT * FROM scores WHERE quizzLinkedId = :quizzId ORDER BY score / totalGrade DESC LIMIT 1")
    fun getBestScoreForQuizz(quizzId: Long): Flow<ScoreEntity?>
}