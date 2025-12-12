package com.example.medquiz.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "settings_datastore"

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATASTORE_NAME
)

class SettingsDataStore(private val context: Context) {

    private val THEME_KEY = booleanPreferencesKey("dark_theme")
    private val LANG_KEY = stringPreferencesKey("app_language") // New key for language

    // Get Theme
    val isDarkTheme: Flow<Boolean> = context.settingsDataStore.data
        .map { prefs -> prefs[THEME_KEY] ?: false }

    // Get Language (Default is "tr")
    val language: Flow<String> = context.settingsDataStore.data
        .map { prefs -> prefs[LANG_KEY] ?: "tr" }

    suspend fun toggleTheme(current: Boolean) {
        context.settingsDataStore.edit { prefs ->
            prefs[THEME_KEY] = !current
        }
    }

    suspend fun saveLanguage(langCode: String) {
        context.settingsDataStore.edit { prefs ->
            prefs[LANG_KEY] = langCode
        }
    }
}