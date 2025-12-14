package com.example.medquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medquiz.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {


    @Query("SELECT * FROM questions WHERE categoryId = :categoryId AND language = :lang")
    fun getQuestionsByCategory(categoryId: Long, lang: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE id = :questionId")
    suspend fun getQuestion(questionId: Long): QuestionEntity?


    @Query("SELECT COUNT(*) FROM questions WHERE categoryId = :categoryId AND language = :lang")
    suspend fun getCountByCategory(categoryId: Long, lang: String): Int


    @Query("SELECT COUNT(id) FROM questions")
    suspend fun getCountAll(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionEntity): Long

    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()


    @Query("SELECT id FROM questions WHERE categoryId = :categoryId AND language = :lang AND id > :currentId ORDER BY id ASC LIMIT 1")
    suspend fun getNextQuestionId(categoryId: Long, currentId: Long, lang: String): Long?
}