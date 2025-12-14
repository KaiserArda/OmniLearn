package com.example.medquiz.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward // İleri ikonu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.medquiz.R
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.data.local.entity.DailyStatsEntity
import com.example.medquiz.data.local.entity.QuestionEntity
import com.example.medquiz.data.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetailScreen(
    questionId: Long,
    onBack: () -> Unit = {},
    onNavigateToQuestion: (Long) -> Unit // <-- YENİ PARAMETRE
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val database = remember { AppDatabase.getDatabase(context, scope) }

    val repository = remember {
        QuizRepository(
            database.categoryDao(),
            database.questionDao(),
            database.statsDao()
        )
    }

    var question by remember { mutableStateOf<QuestionEntity?>(null) }
    var nextQuestionId by remember { mutableStateOf<Long?>(null) } // Sıradaki sorunun ID'si

    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var showExplanation by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    LaunchedEffect(questionId) {
        scope.launch(Dispatchers.IO) {
            val fetchedQuestion = repository.getQuestionById(questionId)
            withContext(Dispatchers.Main) {
                question = fetchedQuestion
            }

            if (fetchedQuestion != null) {

                val currentLang = java.util.Locale.getDefault().language
                val nextId = repository.getNextQuestionId(fetchedQuestion.categoryId, fetchedQuestion.id, currentLang)

                withContext(Dispatchers.Main) {
                    nextQuestionId = nextId
                }
            }
        }
    }

    // İstatistik Güncelleme Fonksiyonu
    fun updateStats(isCorrectAnswer: Boolean, isSkipped: Boolean) {
        scope.launch(Dispatchers.IO) {
            // Tarih formatı (Tüm Android sürümleri için güvenli)
            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val today = sdf.format(java.util.Date())

            val statsDao = database.statsDao()
            val currentStats = statsDao.getStatsByDate(today) ?: DailyStatsEntity(date = today)

            val newStats = currentStats.copy(
                totalSeen = currentStats.totalSeen + 1,
                correctCount = if (isCorrectAnswer) currentStats.correctCount + 1 else currentStats.correctCount,
                wrongCount = if (!isCorrectAnswer && !isSkipped) currentStats.wrongCount + 1 else currentStats.wrongCount,
                emptyCount = if (isSkipped) currentStats.emptyCount + 1 else currentStats.emptyCount
            )
            statsDao.insertStats(newStats)
        }
    }

    // Bir sonraki adıma geçiş mantığı
    fun proceedToNext() {
        if (nextQuestionId != null) {
            // Sırada soru varsa ona git
            onNavigateToQuestion(nextQuestionId!!)
        } else {
            // Yoksa listeye dön
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.title_question_detail)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
                        if (idx == q.correctIndex) Color(0xFF4CAF50) // Yeşil
                        else if (idx == selectedOptionIndex) Color(0xFFEF5350) // Kırmızı
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
                                val correct = (idx == q.correctIndex)
                                isCorrect = correct
                                showExplanation = true
                                updateStats(isCorrectAnswer = correct, isSkipped = false)
                            }
                        }
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            val optionLetter = ('A' + idx)
                            Text(
                                text = "$optionLetter) $opt",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (showExplanation && (idx == q.correctIndex || idx == selectedOptionIndex)) Color.White else Color.Unspecified
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- PAS GEÇ BUTONU ---
                // Sadece cevaplanmadıysa görünür
                if (selectedOptionIndex == null) {
                    OutlinedButton(
                        onClick = {
                            updateStats(isCorrectAnswer = false, isSkipped = true)
                            proceedToNext() // Otomatik ilerle
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.btn_skip))
                    }
                }

                // --- SONUÇ VE İLERLEME ALANI ---
                if (showExplanation && selectedOptionIndex != null) {
                    Text(
                        text = stringResource(id = if (isCorrect) R.string.msg_correct else R.string.msg_wrong),
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFEF5350)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (q.explanation.isNotBlank()) {
                        Text(
                            text = "${stringResource(id = R.string.label_explanation_prefix)} ${q.explanation}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))


                    Button(
                        onClick = { proceedToNext() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (nextQuestionId != null) stringResource(R.string.btn_next_question) else stringResource(R.string.btn_finish_quiz),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}