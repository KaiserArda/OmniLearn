package com.example.medquiz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.medquiz.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    fun getQuestionsByCategory(categoryId: Long): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE id = :questionId")
    suspend fun getQuestion(questionId: Long): QuestionEntity?

    // --- YENİ EKLENDİ: Kategorideki toplam soru sayısını verir ---
    @Query("SELECT COUNT(*) FROM questions WHERE categoryId = :categoryId")
    suspend fun getCountByCategory(categoryId: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionEntity): Long

    @Query("DELETE FROM questions")
    suspend fun deleteAllQuestions()
}