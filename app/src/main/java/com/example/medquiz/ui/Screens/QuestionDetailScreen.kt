package com.example.medquiz.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource // <-- Bu import önemli
import androidx.compose.ui.unit.dp
import com.example.medquiz.R // <-- R dosyasının import edildiğinden emin ol
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.data.local.entity.QuestionEntity
import com.example.medquiz.data.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetailScreen(
    questionId: Long,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val database = remember { AppDatabase.getDatabase(context, scope) }
    val repository = remember { QuizRepository(database.categoryDao(), database.questionDao()) }

    var question by remember { mutableStateOf<QuestionEntity?>(null) }

    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var showExplanation by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    LaunchedEffect(questionId) {
        scope.launch(Dispatchers.IO) {
            val fetchedQuestion = repository.getQuestionById(questionId)
            withContext(Dispatchers.Main) {
                question = fetchedQuestion
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                // String kaynağından başlık
                title = { Text(stringResource(id = R.string.title_question_detail)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        // String kaynağından açıklama
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.cd_back_button)
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (question == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val q = question!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = q.questionText, style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(16.dp))

                val options = listOf(q.option1, q.option2, q.option3, q.option4)

                options.forEachIndexed { idx, opt ->
                    val cardColor = if (showExplanation) {
                        if (idx == q.correctIndex) Color(0xFF4CAF50)
                        else if (idx == selectedOptionIndex) Color(0xFFEF5350)
                        else MaterialTheme.colorScheme.surfaceVariant
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        onClick = {
                            if (selectedOptionIndex == null) {
                                selectedOptionIndex = idx
                                isCorrect = (idx == q.correctIndex)
                                showExplanation = true
                            }
                        }
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "${'A' + idx}) $opt",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (showExplanation && (idx == q.correctIndex || idx == selectedOptionIndex)) Color.White else Color.Unspecified
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (showExplanation && selectedOptionIndex != null) {
                    // String kaynağından Sonuç Mesajı
                    Text(
                        text = stringResource(id = if (isCorrect) R.string.msg_correct else R.string.msg_wrong),
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFEF5350)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (q.explanation.isNotBlank()) {
                        // String kaynağından "Açıklama:" ön eki
                        Text(
                            text = stringResource(id = R.string.label_explanation_prefix) + " " + q.explanation,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}