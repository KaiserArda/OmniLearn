package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.QuestionEntity
import com.example.medquiz.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.util.Locale // <-- Bunu eklemeyi unutma

class QuestionListViewModel(private val repo: QuizRepository) : ViewModel() {
    private val _selectedCategoryId = MutableStateFlow<Long?>(null)

    val questions = _selectedCategoryId
        .flatMapLatest { id ->
            if (id == null) {
                kotlinx.coroutines.flow.flowOf(emptyList())
            } else {

                val currentLang = Locale.getDefault().language


                repo.getQuestionsForCategory(id, currentLang)
            }
        }
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())

    fun selectCategory(id: Long) {
        _selectedCategoryId.value = id
    }

    suspend fun getQuestionById(id: Long): QuestionEntity? = repo.getQuestionById(id)
}