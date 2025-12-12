package com.example.medquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medquiz.ui.navigation.NavGraph
import com.example.medquiz.ui.theme.MedQuizTheme
import com.example.medquiz.ui.updateLocale
import com.example.medquiz.vm.SettingsViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()

            val isDark by settingsViewModel.isDark.collectAsState(initial = false)
            val language by settingsViewModel.currentLanguage.collectAsState(initial = "tr")

            val context = LocalContext.current

            // Dil değiştiğinde sistem ayarını güncelle
            LaunchedEffect(language) {
                updateLocale(context, language)
            }

            MedQuizTheme(darkTheme = isDark) {
                // BURADAKİ "key(language)" KODUNU KALDIRDIK.
                // Artık dil değişince uygulama baştan başlamaz, sadece ekran güncellenir.
                NavGraph()
            }
        }
    }
}