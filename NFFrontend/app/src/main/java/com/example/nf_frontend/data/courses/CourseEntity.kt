package com.example.nf_frontend.data.courses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val code: String,
    val name: String,
    val color: Int
) {
}
