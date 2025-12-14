package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.local.entity.DailyStatsEntity
import com.example.medquiz.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: QuizRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories: StateFlow<List<CategoryEntity>> = _categories.asStateFlow()

    private val _currentParentId = MutableStateFlow<Long?>(null)
    val currentParentId: StateFlow<Long?> = _currentParentId.asStateFlow()

    private val _currentCategory = MutableStateFlow<CategoryEntity?>(null)
    val currentCategory: StateFlow<CategoryEntity?> = _currentCategory.asStateFlow()

    // --- Statistics State ---
    private val _todayStats = MutableStateFlow<DailyStatsEntity?>(null)
    val todayStats: StateFlow<DailyStatsEntity?> = _todayStats.asStateFlow()

    init {
        loadMainCategories()
        refreshStats()
    }

    fun refreshStats() {
        viewModelScope.launch {
            _todayStats.value = repository.getTodayStats()
        }
    }

    fun loadMainCategories() {
        viewModelScope.launch {
            repository.getMainCategories().collectLatest { list ->
                _categories.value = list
                _currentParentId.value = null
                _currentCategory.value = null
            }
        }
    }

    fun loadSubCategories(parentId: Long) {
        viewModelScope.launch {
            repository.getSubCategories(parentId).collectLatest { list ->
                _categories.value = list
                _currentParentId.value = parentId
                val parentCategory = repository.getCategoryById(parentId)
                _currentCategory.value = parentCategory
            }
        }
    }

    fun goBack() {
        viewModelScope.launch {
            val currentParent = _currentParentId.value
            if (currentParent != null) {
                val currentCat = repository.getCategoryById(currentParent)
                if (currentCat == null || currentCat.parentId == null) {
                    loadMainCategories()
                } else {
                    loadSubCategories(currentCat.parentId)
                }
            } else {
                loadMainCategories()
            }
        }
    }

    suspend fun checkHasSubCategories(categoryId: Long): Boolean {
        return repository.getSubCategories(categoryId).firstOrNull()?.isNotEmpty() ?: false
    }


    fun addUserCategory(name: String, parentId: Long?) {
        viewModelScope.launch {
            val newCategory = CategoryEntity(
                name = name,
                description = "User Created",
                parentId = parentId,
                isUserCreated = true
            )

            repository.insertCategory(newCategory)
        }
    }
    fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(category)

            if (_currentParentId.value == null) {
                loadMainCategories()
            } else {
                loadSubCategories(_currentParentId.value!!)
            }
        }
    }


    fun handleCategoryClick(
        category: CategoryEntity,
        onNavigateToQuestions: (Long) -> Unit
    ) {
        viewModelScope.launch {
            val hasSubs = checkHasSubCategories(category.id)


            if (hasSubs || category.isUserCreated) {
                loadSubCategories(category.id)
            } else {

                onNavigateToQuestions(category.id)
            }
        }
    }
}