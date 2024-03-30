package com.example.nf_frontend.data.quizzes

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.nf_frontend.data.questions.QuestionEntity

@Entity(tableName = "quizzes")
data class QuizzEntity(
    @PrimaryKey(autoGenerate = true) val quizzId: Long = 0,
    val title: String,
    val description: String,
    val courseLinkedId: String
) {
}

data class QuizzWithQuestions(
    @Embedded val quizz: QuizzEntity,
    @Relation(
        parentColumn = "quizzId",
        entityColumn = "quizzLinkedId"
    )
    val questions: List<QuestionEntity>
){
}