package com.example.medquiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.ui.navigation.NavGraph
import com.example.medquiz.ui.theme.MedQuizTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainActivity : AppCompatActivity() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Database
        // It loads all medical faculties, committees, and questions into the DB.
        AppDatabase.getDatabase(applicationContext, applicationScope)

        // Set the UI Content
        setContent {
            MedQuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Start the Navigation Graph
                    NavGraph()
                }
            }
        }
    }
}