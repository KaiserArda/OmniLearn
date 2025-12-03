package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.repository.MedicalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: MedicalRepository) : ViewModel() {


    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories: StateFlow<List<CategoryEntity>> = _categories.asStateFlow()


    init {
        loadMainCategories()
    }


    fun loadMainCategories() {
        viewModelScope.launch {

            repository.getMainCategories().collectLatest { list ->
                _categories.value = list
            }
        }
    }


    fun loadSubCategories(parentId: Long) {
        viewModelScope.launch {
            repository.getSubCategories(parentId).collectLatest { list ->
                _categories.value = list
            }
        }
    }
}