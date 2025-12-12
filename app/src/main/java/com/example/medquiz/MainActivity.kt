package com.example.medquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medquiz.ui.navigation.NavGraph
import com.example.medquiz.ui.theme.MedQuizTheme
import com.example.medquiz.vm.SettingsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val settingsViewModel: SettingsViewModel = viewModel()
            val isDark by settingsViewModel.isDark.collectAsState(initial = false)

            MedQuizTheme(darkTheme = isDark) {
                NavGraph()
            }
        }
    }
}
