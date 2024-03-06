package com.example.nf_frontend.data.courses

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val code: String,
    val name: String,
    val color: Int
) {
    constructor() : this("", "", 0) // Empty constructor
}
