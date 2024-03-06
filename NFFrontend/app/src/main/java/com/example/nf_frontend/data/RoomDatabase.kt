package com.example.nf_frontend.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nf_frontend.data.courses.CourseDao
import com.example.nf_frontend.data.courses.CourseEntity

@Database(entities = [CourseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}
