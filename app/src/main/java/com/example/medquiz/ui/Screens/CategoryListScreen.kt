package com.example.medquiz.ui.Screens

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.vm.CategoryViewModel
import com.example.medquiz.vm.CategoryViewModelFactory
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.data.repository.MedicalRepository
import androidx.compose.ui.res.stringResource
import com.example.medquiz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    onCategoryClick: (Long) -> Unit,
    onAddQuestion: () -> Unit
) {
    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!

    val viewModel: CategoryViewModel = remember {
        val database = AppDatabase.getDatabase(context, kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO))
        val repository = MedicalRepository(database.categoryDao(), database.questionDao())
        val factory = CategoryViewModelFactory(repository)
        ViewModelProvider(viewModelStoreOwner, factory)[CategoryViewModel::class.java]
    }

    val categories by viewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.category_list_title),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    // 🌍 İŞTE DİL DEĞİŞTİRME BUTONU BURADA
                    IconButton(onClick = {
                        val currentLocale = AppCompatDelegate.getApplicationLocales().toLanguageTags()
                        val newLocale = when {
                            currentLocale.contains("tr") -> "ja" // Tr -> Ja
                            currentLocale.contains("ja") -> "en" // Ja -> En
                            else -> "tr"                         // En -> Tr
                        }
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(newLocale))
                    }) {
                        Icon(Icons.Default.Settings, contentDescription = "Change Language")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryItem(category = category, onClick = {
                        onCategoryClick(category.id)
                        viewModel.loadSubCategories(category.id)
                    })
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: CategoryEntity, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                category.description?.let { desc ->
                    Text(
                        text = desc,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}