package com.example.medquiz.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.data.local.entity.DailyStatsEntity
import com.example.medquiz.data.repository.QuizRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.ui.res.stringResource
import com.example.medquiz.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    onBack: () -> Unit
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


    var stats by remember { mutableStateOf<DailyStatsEntity?>(null) }
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {

            val todayStats = repository.getTodayStats()


            withContext(Dispatchers.Main) {
                stats = todayStats
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.daily_report)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (stats == null) {

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.no_data_today),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {

                val s = stats!!

                Text(
                    text = "${stringResource(R.string.date_label)} ${s.date}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))


                StatCard(
                    title = stringResource(R.string.stat_solved),
                    value = s.totalSeen.toString(),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Box(modifier = Modifier.weight(1f)) {
                        StatCard(
                            title = stringResource(R.string.stat_correct),
                            value = s.correctCount.toString(),
                            color = Color(0xFF4CAF50)
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        StatCard(
                            title = stringResource(R.string.stat_wrong),
                            value = s.wrongCount.toString(),
                            color = Color(0xFFEF5350)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                StatCard(
                    title = stringResource(R.string.stat_empty),
                    value = s.emptyCount.toString(),
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = color
            )
        }
    }
}