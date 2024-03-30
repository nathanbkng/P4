package com.example.nf_frontend.data.questions

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity (
    @PrimaryKey(autoGenerate = true) val questionId: Long = 0,
    var questionText: String,
    var isTrue: Boolean,
    val quizzLinkedId: Long
) {
}