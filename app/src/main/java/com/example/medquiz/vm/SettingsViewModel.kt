package com.example.medquiz.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.datastore.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = SettingsDataStore(application)

    val isDark: Flow<Boolean> = dataStore.isDarkTheme
    val currentLanguage: Flow<String> = dataStore.language // Observe language

    fun toggleTheme() {
        viewModelScope.launch {
            val current = dataStore.isDarkTheme.first()
            dataStore.toggleTheme(current)
        }
    }

    fun changeLanguage(langCode: String) {
        viewModelScope.launch {
            dataStore.saveLanguage(langCode)
        }
    }
}