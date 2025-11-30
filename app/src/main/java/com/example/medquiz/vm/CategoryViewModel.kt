package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.repository.MedicalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class CategoryViewModel(repository: MedicalRepository) : ViewModel() {

    val categories: StateFlow<List<CategoryEntity>> =
        repository.getAllCategories()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = emptyList()
            )
}