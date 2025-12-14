package com.example.medquiz.data.repository

import com.example.medquiz.data.local.dao.CategoryDao
import com.example.medquiz.data.local.dao.QuestionDao
import com.example.medquiz.data.local.dao.StatsDao // <-- EKLENDİ
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.local.entity.DailyStatsEntity // <-- EKLENDİ
import com.example.medquiz.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate // <-- EKLENDİ

class QuizRepository(
    private val categoryDao: CategoryDao,
    private val questionDao: QuestionDao,
    private val statsDao: StatsDao // <-- 3. Parametre olarak eklendi
) {
    fun getMainCategories(): Flow<List<CategoryEntity>> = categoryDao.getMainCategories()

    fun getSubCategories(parentId: Long): Flow<List<CategoryEntity>> = categoryDao.getSubCategories(parentId)

    suspend fun insertCategory(category: CategoryEntity): Long = categoryDao.insert(category)

    suspend fun getCategoryById(id: Long): CategoryEntity? {
        return categoryDao.getCategoryById(id)
    }

    fun getQuestionsForCategory(categoryId: Long, lang: String): Flow<List<QuestionEntity>> =
        questionDao.getQuestionsByCategory(categoryId, lang)

    suspend fun hasSubCategories(categoryId: Long): Boolean {
        return categoryDao.getSubCategories(categoryId).firstOrNull()?.isNotEmpty() ?: false
    }
    suspend fun getNextQuestionId(categoryId: Long, currentId: Long, lang: String): Long? {
        return questionDao.getNextQuestionId(categoryId, currentId, lang)
    }
    suspend fun insertQuestion(question: QuestionEntity): Long = questionDao.insert(question)

    suspend fun getQuestionById(id: Long): QuestionEntity? = questionDao.getQuestion(id)


    suspend fun getQuestionCount(categoryId: Long, lang: String): Int {
        return questionDao.getCountByCategory(categoryId, lang)
    }

    suspend fun getTodayStats(): DailyStatsEntity? {
        val today = LocalDate.now().toString()
        return statsDao.getStatsByDate(today)
    }
}