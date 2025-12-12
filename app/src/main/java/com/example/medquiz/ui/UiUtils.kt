package com.example.medquiz.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import com.example.medquiz.vm.SettingsViewModel
import kotlinx.coroutines.delay
import java.util.Locale

fun updateLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val resources = context.resources
    val config = resources.configuration
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
}

@Composable
fun LanguageChangeButton(viewModel: SettingsViewModel) {
    var expanded by remember { mutableStateOf(false) }

    val languages = mapOf("tr" to "Türkçe", "en" to "English", "ja" to "日本語")

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onPrimary)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            languages.forEach { (code, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        viewModel.changeLanguage(code)
                        expanded = false
                    }
                )
            }
        }
    }
}

// 1. VERİTABANI YAZILARI İÇİN (String Key ile çalışır)
@Composable
fun getLocalizedText(dbKey: String, languageCode: String): String {
    val context = LocalContext.current
    return remember(dbKey, languageCode) {
        try {
            val desiredLocale = Locale(languageCode)
            val config = Configuration(context.resources.configuration)
            config.setLocale(desiredLocale)
            val localizedContext = context.createConfigurationContext(config)
            val resourceId = localizedContext.resources.getIdentifier(dbKey, "string", context.packageName)
            if (resourceId != 0) localizedContext.getString(resourceId) else dbKey
        } catch (e: Exception) {
            dbKey
        }
    }
}

// 2. XML YAZILARI İÇİN (R.string.id ile çalışır) - YENİ EKLENDİ
@Composable
fun getLocalizedResource(resId: Int, languageCode: String): String {
    val context = LocalContext.current
    return remember(resId, languageCode) {
        try {
            val desiredLocale = Locale(languageCode)
            val config = Configuration(context.resources.configuration)
            config.setLocale(desiredLocale)
            val localizedContext = context.createConfigurationContext(config)
            localizedContext.getString(resId)
        } catch (e: Exception) {
            ""
        }
    }
}

fun Modifier.clickableSingle(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    var isClickable by remember { mutableStateOf(true) }
    LaunchedEffect(isClickable) { if (!isClickable) { delay(1500); isClickable = true } }
    Modifier.clickable(enabled = enabled && isClickable) { if (isClickable) { isClickable = false; onClick() } }
}