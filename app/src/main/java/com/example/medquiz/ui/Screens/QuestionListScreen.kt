package com.example.medquiz.ui.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medquiz.data.local.entity.QuestionEntity
import com.example.medquiz.vm.QuestionListViewModel
import com.example.medquiz.vm.QuestionListViewModelFactory
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.data.repository.MedicalRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionListScreen(
    categoryId: Long,
    onQuestionClick: (Long) -> Unit,
    onAddQuestion: () -> Unit
) {
    val context = LocalContext.current
    val viewModelStoreOwner = LocalViewModelStoreOwner.current!!

    val viewModel: QuestionListViewModel = remember {
        val database = AppDatabase.getDatabase(context, kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO))
        val repository = MedicalRepository(database.categoryDao(), database.questionDao())
        val factory = QuestionListViewModelFactory(repository)
        ViewModelProvider(viewModelStoreOwner, factory)[QuestionListViewModel::class.java]
    }

    LaunchedEffect(categoryId) {
        viewModel.selectCategory(categoryId)
    }

    val questions by viewModel.questions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sorular") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddQuestion) {
                Icon(Icons.Default.Add, contentDescription = "Soru Ekle")
            }
        }
    ) { padding ->
        if (questions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Bu kategoride henüz soru yok.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
                items(questions) { q ->
                    QuestionCard(question = q, onClick = { onQuestionClick(q.id) })
                }
            }
        }
    }
}

@Composable
fun QuestionCard(question: QuestionEntity, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "A) ${question.option1}", style = MaterialTheme.typography.bodySmall)
            Text(text = "B) ${question.option2}", style = MaterialTheme.typography.bodySmall)
        }
    }
}