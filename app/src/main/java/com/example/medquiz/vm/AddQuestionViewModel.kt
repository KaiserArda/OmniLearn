package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.QuestionEntity
import com.example.medquiz.data.repository.MedicalRepository
import kotlinx.coroutines.launch

class AddQuestionViewModel(private val repo: MedicalRepository) : ViewModel() {
    fun addQuestion(question: QuestionEntity, onComplete: (Long) -> Unit = {}) {
        viewModelScope.launch {
            val id = repo.insertQuestion(question)
            onComplete(id)
        }
    }
}
