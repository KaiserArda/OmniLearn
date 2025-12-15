package com.example.medquiz.ui.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medquiz.R
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.data.local.entity.CategoryEntity
import com.example.medquiz.data.local.entity.DailyStatsEntity
import com.example.medquiz.data.repository.QuizRepository
import com.example.medquiz.ui.getLocalizedResource
import com.example.medquiz.ui.getLocalizedText
import com.example.medquiz.vm.CategoryViewModel
import com.example.medquiz.vm.CategoryViewModelFactory
import com.example.medquiz.vm.SettingsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    onNavigateToQuestions: (Long) -> Unit,
    onNavigateToStats: () -> Unit,
    onAddQuestion: (Long) -> Unit
) {
    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!

    // ViewModel Setup
    val viewModel: CategoryViewModel = remember {
        val database = AppDatabase.getDatabase(context, kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO))
        val repository = QuizRepository(
            database.categoryDao(),
            database.questionDao(),
            database.statsDao()
        )
        val factory = CategoryViewModelFactory(repository)
        ViewModelProvider(viewModelStoreOwner, factory)[CategoryViewModel::class.java]
    }

    val settingsViewModel: SettingsViewModel = viewModel()
    val isDark by settingsViewModel.isDark.collectAsState(initial = false)
    val categories by viewModel.categories.collectAsState()
    val currentParentId by viewModel.currentParentId.collectAsState()
    val currentCategory by viewModel.currentCategory.collectAsState()
    val currentLang by settingsViewModel.currentLanguage.collectAsState(initial = "tr")
    val todayStats by viewModel.todayStats.collectAsState()

    // --- DIALOG STATES ---
    var showTypeSelectionDialog by remember { mutableStateOf(false) }
    var showNameInputDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) } // Silme Diyaloğu

    var newCategoryName by remember { mutableStateOf("") }
    var categoryToDelete by remember { mutableStateOf<CategoryEntity?>(null) } // Silinecek kategori

    LaunchedEffect(Unit) {
        viewModel.refreshStats()
    }

    BackHandler(enabled = currentParentId != null) {
        viewModel.goBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (currentParentId != null && currentCategory != null) {
                        // Kategori Başlığı
                        val titleText = if (currentCategory!!.isUserCreated) {
                            // Dinamik Suffix kullanımı
                            "${currentCategory!!.name}${stringResource(R.string.suffix_user_created)}"
                        } else {
                            getLocalizedText(currentCategory!!.name, currentLang)
                        }
                        Text(text = titleText, fontWeight = FontWeight.Bold)
                    } else {
                        StatsStatusBadge(stats = todayStats, currentLang = currentLang, onClick = onNavigateToStats)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    if (currentParentId != null) {
                        IconButton(onClick = { viewModel.goBack() }) {
                            Icon(Icons.Default.ArrowBack, "Back")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { settingsViewModel.toggleTheme() }) {
                        Icon(if (isDark) Icons.Outlined.DarkMode else Icons.Outlined.LightMode, "Theme")
                    }
                    LanguageSelectorDropdown(
                        currentLanguageCode = currentLang,
                        onLanguageSelected = { settingsViewModel.changeLanguage(it) }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (currentParentId == null) {
                        showNameInputDialog = true
                    } else {
                        showTypeSelectionDialog = true
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ekle")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(16.dp)) {
                if (categories.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (currentParentId == null) getLocalizedResource(R.string.empty_category_error, currentLang)
                            else getLocalizedResource(R.string.no_subcategories, currentLang),
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(items = categories, key = { it.id }) { category ->
                            CategoryItem(
                                category = category,
                                currentLang = currentLang,
                                onClick = {

                                    viewModel.handleCategoryClick(category, onNavigateToQuestions)
                                },
                                onLongClick = {

                                    categoryToDelete = category
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }

            // --- DIALOGS ---

            // 1. ADD TYPE DIALOG
            if (showTypeSelectionDialog) {
                AlertDialog(
                    onDismissRequest = { showTypeSelectionDialog = false },
                    icon = { Icon(Icons.Default.HelpOutline, null) },
                    title = { Text(stringResource(R.string.dialog_add_title)) },
                    text = { Text(stringResource(R.string.dialog_add_desc)) },
                    confirmButton = {
                        TextButton(onClick = {
                            showTypeSelectionDialog = false
                            if (currentParentId != null) onAddQuestion(currentParentId!!)
                        }) {
                            Text(stringResource(R.string.btn_add_question))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showTypeSelectionDialog = false
                            showNameInputDialog = true
                        }) {
                            Text(stringResource(R.string.btn_add_folder))
                        }
                    }
                )
            }

            // 2. NAME INPUT DIALOG
            if (showNameInputDialog) {
                AlertDialog(
                    onDismissRequest = { showNameInputDialog = false },
                    title = { Text(stringResource(if (currentParentId == null) R.string.dialog_new_main_cat else R.string.dialog_new_sub_cat)) },
                    text = {
                        OutlinedTextField(
                            value = newCategoryName,
                            onValueChange = { newCategoryName = it },
                            label = { Text(stringResource(R.string.hint_category_name)) },
                            singleLine = true
                        )
                    },
                    confirmButton = {
                        Button(onClick = {
                            if (newCategoryName.isNotBlank()) {
                                viewModel.addUserCategory(newCategoryName, currentParentId)
                                newCategoryName = ""
                                showNameInputDialog = false
                            }
                        }) {
                            Text(stringResource(R.string.btn_create))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showNameInputDialog = false }) {
                            Text(stringResource(R.string.btn_cancel))
                        }
                    }
                )
            }

            // 3. DELETE CONFIRMATION DIALOG (GÜNCELLENDİ: Strings Uyumlu)
            if (showDeleteDialog && categoryToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    icon = { Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red) },
                    title = { Text(stringResource(R.string.dialog_delete_title)) }, // "Silinsin mi?"
                    text = {
                        // String içinde parametre kullanımı (%1$s yerine isim gelecek)
                        Text(stringResource(R.string.dialog_delete_msg, categoryToDelete?.name ?: ""))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                categoryToDelete?.let { viewModel.deleteCategory(it) }
                                showDeleteDialog = false
                                categoryToDelete = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(stringResource(R.string.btn_delete), color = Color.White) // "Sil"
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text(stringResource(R.string.btn_cancel))
                        }
                    }
                )
            }
        }
    }
}

// --- HELPER COMPOSABLES ---

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItem(
    category: CategoryEntity,
    currentLang: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {

                val displayName = if (category.isUserCreated) {
                    "${category.name}${stringResource(R.string.suffix_user_created)}"
                } else {
                    getLocalizedText(category.name, currentLang)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (category.isUserCreated) {
                        Icon(
                            Icons.Default.CreateNewFolder,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Text(
                        text = displayName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Icon(Icons.Default.ArrowForward, null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun StatsStatusBadge(
    stats: DailyStatsEntity?,
    currentLang: String,
    onClick: () -> Unit
) {
    val count = stats?.totalSeen ?: 0

    val (bgColor, icon, text) = when {
        count < 20 -> Triple(Color(0xFFE53935), Icons.Default.Warning, R.string.stat_start)
        count < 50 -> Triple(Color(0xFFFFB300), Icons.Default.LocalFireDepartment, R.string.stat_warming)
        else -> Triple(Color(0xFF4CAF50), Icons.Default.RocketLaunch, R.string.stat_great)
    }

    Surface(
        onClick = onClick,
        color = bgColor,
        shape = RoundedCornerShape(50),
        modifier = Modifier.wrapContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))

            // 2. Fix: Use stringResource(id) and append the count
            Text(
                text = "${getLocalizedResource(text, currentLang)} ($count)",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
data class LanguageOption(val code: String, val name: String, val flagRes: Int)

@Composable
fun LanguageSelectorDropdown(currentLanguageCode: String, onLanguageSelected: (String) -> Unit) {
    val languages = listOf(
        LanguageOption("tr", "Türkçe", R.drawable.flag_tr),
        LanguageOption("zh", "中文 (Çince)", R.drawable.flag_cn),
        LanguageOption("ja", "日本語 (Japonca)", R.drawable.flag_ja),
        LanguageOption("en", "English", R.drawable.flag_en),
        LanguageOption("de", "Deutsch", R.drawable.flag_de)
    )

    val selectedLanguage = languages.find { it.code == currentLanguageCode } ?: languages.first()
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = selectedLanguage.flagRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp).clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {
            languages.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = language.flagRes),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp).clip(RoundedCornerShape(4.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = language.name)
                        }
                    },
                    onClick = {
                        onLanguageSelected(language.code)
                        expanded = false
                    }
                )
            }
        }
    }
}