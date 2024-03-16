package com.example.nf_frontend.data.quizzes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class QuizzEntity(
    @PrimaryKey(autoGenerate = true) val quizzId: Long = 0,
    val title: String,
    val description: String,
    val courseLinkedId: String
) {
}