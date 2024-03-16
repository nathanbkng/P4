package com.example.nf_frontend.data.courses

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.nf_frontend.data.quizzes.QuizzEntity

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val code: String,
    val name: String,
    val color: Int
) {
}

data class CourseWithQuizzes(
    @Embedded val course : CourseEntity,
    @Relation(
        parentColumn = "code",
        entityColumn = "courseLinkedId"
    )
    val quizzes : List<QuizzEntity>
){
}
