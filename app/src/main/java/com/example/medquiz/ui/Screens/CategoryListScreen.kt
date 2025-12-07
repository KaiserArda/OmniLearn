package com.example.medquiz.ui.Screens

// Imports for Language Switching
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

// Standard Compose Imports
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Coroutines for delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Lifecycle & ViewModel Imports
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

// Project Specific Imports
import com.example.medquiz.R
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.repository.MedicalRepository
import com.example.medquiz.vm.CategoryViewModel
import com.example.medquiz.vm.CategoryViewModelFactory
import com.example.medquiz.ui.getLocalizedText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    onCategoryClick: (Long) -> Unit,
    onAddQuestion: () -> Unit
) {
    // 1. Setup ViewModel & Scope
    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!

    // We use this scope to delay navigation
    val scope = rememberCoroutineScope()

    val viewModel: CategoryViewModel = remember {
        val database = AppDatabase.getDatabase(context, kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO))
        val repository = MedicalRepository(database.categoryDao(), database.questionDao())
        val factory = CategoryViewModelFactory(repository)
        ViewModelProvider(viewModelStoreOwner, factory)[CategoryViewModel::class.java]
    }

    // Collect data from ViewModel
    val categories by viewModel.categories.collectAsState()

    // 2. State for Dropdown Menu
    var showMenu by remember { mutableStateOf(false) }

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
                    // LANGUAGE SETTINGS BUTTON
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.Settings, contentDescription = "Language Settings")
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                        ) {
                            DropdownMenuItem(text = { Text("English") }, onClick = {
                                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                                showMenu = false
                            })
                            DropdownMenuItem(text = { Text("Türkçe") }, onClick = {
                                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("tr"))
                                showMenu = false
                            })
                            DropdownMenuItem(text = { Text("日本語") }, onClick = {
                                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ja"))
                                showMenu = false
                            })
                        }
                    }
                }
            )
        }
    ) { padding ->
        // 3. MAIN CONTENT
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(16.dp)
        ) {
            if (categories.isEmpty()) {
                // Empty State Handler
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(id = R.string.empty_category_error),
                        color = Color.Gray
                    )
                }
            } else {
                // List of Categories
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // New ui for click
                    items(
                        items = categories,
                        key = { category -> category.id }
                    ) { category ->
                        CategoryItem(category = category, onClick = {
                            scope.launch {
                                // Delay
                                delay(100)
                                onCategoryClick(category.id)
                                viewModel.loadSubCategories(category.id)
                            }
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: CategoryEntity, onClick: () -> Unit) {
    var isClickProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(
                // If processing, disable the click physically
                enabled = !isClickProcessing
            ) {
                if (!isClickProcessing) {
                    isClickProcessing = true

                    scope.launch {
                        // Delay time

                        delay(100)

                        onClick() // Next screen


                        delay(1000)
                        isClickProcessing = false
                    }
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Text Box (Only Title)
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = getLocalizedText(dbKey = category.name),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}