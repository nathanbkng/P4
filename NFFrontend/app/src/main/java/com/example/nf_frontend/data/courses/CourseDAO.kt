package com.example.nf_frontend.data.courses

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.nf_frontend.data.questions.QuestionDAO
import com.example.nf_frontend.data.quizzes.QuizzDAO
import com.example.nf_frontend.data.quizzes.QuizzEntity
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

    @Transaction
    suspend fun deleteCourseWithAssociatedData(course: CourseEntity, quizzDAO: QuizzDAO, questionDAO: QuestionDAO) {
        // Delete associated quizzes
        val quizzes = quizzDAO.getQuizzesForCourse(course.code)
        quizzes.forEach { quizz ->
            deleteQuizzWithQuestions(quizz, quizzDAO, questionDAO) // Delete associated questions for each quiz
        }
        // Delete the course itself
        deleteCourse(course)
    }

    @Transaction
    suspend fun deleteQuizzWithQuestions(quizz: QuizzEntity, quizzDAO: QuizzDAO, questionDAO: QuestionDAO) {
        // Delete associated questions
        val questions = questionDAO.getQuestionsForQuiz(quizz.quizzId)
        questions.forEach { question ->
            questionDAO.deleteQuestion(question)
        }
        // Delete the quiz itself
        quizzDAO.deleteQuizz(quizz)
    }
}