package com.example.nf_frontend.data.courses

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    @Delete
    suspend fun deleteCourse(course: CourseEntity)

    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Transaction
    @Query("SELECT * FROM courses")
    fun getCourseWithQuizzes() : Flow<List<CourseWithQuizzes>>
}