package com.example.nf_frontend.data.scores

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val scoreId: Long = 0,
    val score: Double,
    val quizzLinkedId: Long,
    val totalGrade: Int,
)

