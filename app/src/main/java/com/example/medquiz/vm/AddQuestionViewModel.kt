package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.QuestionEntity
import com.example.medquiz.data.repository.QuizRepository
import kotlinx.coroutines.launch

class AddQuestionViewModel(private val repo: QuizRepository) : ViewModel() {
    fun addQuestion(question: QuestionEntity, onComplete: (Long) -> Unit = {}) {
        viewModelScope.launch {
            val id = repo.insertQuestion(question)
            onComplete(id)
        }
    }
}
