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


    fun getMainCategories(): Flow<List<CategoryEntity>> = categoryDao.getMainCategories()

    fun getSubCategories(parentId: Long): Flow<List<CategoryEntity>> = categoryDao.getSubCategories(parentId)

    suspend fun insertCategory(category: CategoryEntity): Long = categoryDao.insert(category)

    
    fun getQuestionsForCategory(categoryId: Long): Flow<List<QuestionEntity>> =
        questionDao.getQuestionsByCategory(categoryId)


    suspend fun insertQuestion(question: QuestionEntity): Long = questionDao.insert(question)


    suspend fun getQuestionById(id: Long): QuestionEntity? = questionDao.getQuestion(id)
}