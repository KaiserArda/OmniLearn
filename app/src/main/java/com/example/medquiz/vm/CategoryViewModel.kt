package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.CategoryEntity
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

    init {
        loadMainCategories()
    }

    fun loadMainCategories() {
        viewModelScope.launch {
            repository.getMainCategories().collectLatest { list ->
                _categories.value = list
                _currentParentId.value = null  // Ana kategorideyiz
            }
        }
    }

    fun loadSubCategories(parentId: Long) {
        viewModelScope.launch {
            repository.getSubCategories(parentId).collectLatest { list ->
                _categories.value = list
                _currentParentId.value = parentId  // Bu parent'ın altındayız
            }
        }
    }

    // YENİ EKLENECEK FONKSİYON (EN ALTA EKLEYİN):
    // CategoryViewModel.kt - goBack fonksiyonu:
    fun goBack() {
        viewModelScope.launch {
            val currentParent = _currentParentId.value

            if (currentParent != null) {
                // Mevcut parent kategoriyi bul
                val currentCategory = repository.getCategoryById(currentParent)

                if (currentCategory == null || currentCategory.parentId == null) {
                    // Ana kategoriye dön (ya kategori bulunamadı ya da parent'ı yok)
                    loadMainCategories()
                    _currentParentId.value = null
                } else {
                    // Üst kategoriye dön
                    loadSubCategories(currentCategory.parentId)
                    _currentParentId.value = currentCategory.parentId
                }
            } else {
                // Zaten ana kategorideyiz
                loadMainCategories()
            }
        }
    }

    // YENİ: Alt kategori kontrolü (CategoryListScreen için gerekli)
    suspend fun checkHasSubCategories(categoryId: Long): Boolean {
        return repository.getSubCategories(categoryId).firstOrNull()?.isNotEmpty() ?: false
    }
}