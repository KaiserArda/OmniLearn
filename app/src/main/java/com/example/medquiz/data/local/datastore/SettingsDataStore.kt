package com.example.medquiz.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private const val DATASTORE_NAME = "settings_datastore"

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATASTORE_NAME
)

class SettingsDataStore(private val context: Context) {

    private val THEME_KEY = booleanPreferencesKey("dark_theme")

    val isDarkTheme: Flow<Boolean> = context.settingsDataStore.data
        .map { prefs ->
            prefs[THEME_KEY] ?: false
        }

    suspend fun toggleTheme(current: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[THEME_KEY] = !current
        }
    }
}
