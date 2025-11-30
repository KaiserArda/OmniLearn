package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.medquiz.data.repository.MedicalRepository


class CategoryViewModelFactory(private val repo: MedicalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}


class QuestionListViewModelFactory(private val repo: MedicalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}


class AddQuestionViewModelFactory(private val repo: MedicalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddQuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddQuestionViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}