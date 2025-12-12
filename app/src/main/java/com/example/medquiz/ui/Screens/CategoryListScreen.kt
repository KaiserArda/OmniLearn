package com.example.medquiz.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medquiz.R
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.repository.QuizRepository
import com.example.medquiz.vm.CategoryViewModel
import com.example.medquiz.vm.CategoryViewModelFactory
import com.example.medquiz.vm.SettingsViewModel
// İki fonksiyonu da import ediyoruz
import com.example.medquiz.ui.getLocalizedText
import com.example.medquiz.ui.getLocalizedResource
import com.example.medquiz.ui.LanguageChangeButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    onCategoryClick: (Long) -> Unit,
    onAddQuestion: () -> Unit
) {
    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!
    val scope = rememberCoroutineScope()

    val viewModel: CategoryViewModel = remember {
        val database = AppDatabase.getDatabase(context, kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO))
        val repository = QuizRepository(database.categoryDao(), database.questionDao())
        val factory = CategoryViewModelFactory(repository)
        ViewModelProvider(viewModelStoreOwner, factory)[CategoryViewModel::class.java]
    }

    val settingsViewModel: SettingsViewModel = viewModel()
    val isDark by settingsViewModel.isDark.collectAsState(initial = false)
    val categories by viewModel.categories.collectAsState()
    val currentLang by settingsViewModel.currentLanguage.collectAsState(initial = "tr")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // BURASI DEĞİŞTİ: stringResource YERİNE getLocalizedResource KULLANDIK
                    Text(
                        text = getLocalizedResource(R.string.category_list_title, currentLang),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { settingsViewModel.toggleTheme() }) {
                        if (isDark) Icon(Icons.Outlined.DarkMode, "Dark") else Icon(Icons.Outlined.LightMode, "Light")
                    }
                    LanguageChangeButton(viewModel = settingsViewModel)
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(padding).padding(16.dp)) {
            if (categories.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    // Buradaki hata mesajını da çevirelim
                    Text(text = getLocalizedResource(R.string.empty_category_error, currentLang), color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(items = categories, key = { it.id }) { category ->
                        CategoryItem(
                            category = category,
                            currentLang = currentLang,
                            onClick = {
                                scope.launch {
                                    delay(100)
                                    onCategoryClick(category.id)
                                    viewModel.loadSubCategories(category.id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: CategoryEntity, currentLang: String, onClick: () -> Unit) {
    var isClickProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth().height(80.dp).clickable(enabled = !isClickProcessing) {
            if (!isClickProcessing) { isClickProcessing = true; scope.launch { delay(100); onClick(); delay(1000); isClickProcessing = false } }
        }
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                Text(
                    text = getLocalizedText(category.name, currentLang),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(Icons.Default.ArrowForward, null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}