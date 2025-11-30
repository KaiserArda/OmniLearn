package com.example.medquiz.data.repository

import com.example.medquiz.data.local.dao.CategoryDao
import com.example.medquiz.data.local.dao.QuestionDao
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

class MedicalRepository(
    private val categoryDao: CategoryDao,
    private val questionDao: QuestionDao
) {
    // Tüm kategorileri getir
    fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()

    // Bir kategoriye tıklandığında sorularını getir
    fun getQuestionsForCategory(categoryId: Long): Flow<List<QuestionEntity>> =
        questionDao.getQuestionsForCategory(categoryId)

    // Yeni kategori ekle
    suspend fun insertCategory(category: CategoryEntity): Long = categoryDao.insert(category)

    // Yeni soru ekle
    suspend fun insertQuestion(question: QuestionEntity): Long = questionDao.insert(question)

    // ID ile soru bul
    suspend fun getQuestionById(id: Long): QuestionEntity? = questionDao.getById(id)
}