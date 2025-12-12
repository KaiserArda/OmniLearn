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

    // expose as Flow; composable will collect it
    val isDark: Flow<Boolean> = dataStore.isDarkTheme

    fun toggleTheme() {
        viewModelScope.launch {
            // read current value (suspend) and toggle
            val current = dataStore.isDarkTheme.first()
            dataStore.toggleTheme(current)
        }
    }
}
