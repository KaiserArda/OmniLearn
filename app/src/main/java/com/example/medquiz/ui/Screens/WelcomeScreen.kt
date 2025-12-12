package com.example.medquiz.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
// ViewModel imports
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medquiz.vm.SettingsViewModel
// UI Utils import
import com.example.medquiz.ui.getLocalizedResource
import com.example.medquiz.R

@Composable
fun WelcomeScreen(onNavigateToHome: () -> Unit) {

    // 1. DİL BİLGİSİNİ ALMAK İÇİN VIEWMODEL'I ÇAĞIRIYORUZ
    val settingsViewModel: SettingsViewModel = viewModel()
    val currentLang by settingsViewModel.currentLanguage.collectAsState(initial = "tr")

    // 2 seconds screen time
    LaunchedEffect(key1 = true) {
        delay(2000)
        onNavigateToHome()
    }

    // Screen design
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "OmniLearn",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 2. YAZIYI GÜNCELLİYORUZ
            Text(
                text = getLocalizedResource(R.string.welcome_message, currentLang),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.9f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator(color = Color.White)
        }
    }
}