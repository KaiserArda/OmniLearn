package com.example.medquiz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.medquiz.data.local.AppDatabase
import com.example.medquiz.data.repository.MedicalRepository
import com.example.medquiz.vm.AddQuestionViewModel
import com.example.medquiz.vm.AddQuestionViewModelFactory
import com.example.medquiz.vm.CategoryViewModel
import com.example.medquiz.vm.CategoryViewModelFactory
import com.example.medquiz.vm.QuestionListViewModel
import com.example.medquiz.vm.QuestionListViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("MEDQUIZ_TEST", ">>> UYGULAMA BAŞLATILIYOR... <<<")


        val database = AppDatabase.getDatabase(applicationContext, lifecycleScope)
        val repository = MedicalRepository(database.categoryDao(), database.questionDao())




        val catFactory = CategoryViewModelFactory(repository)
        val categoryViewModel = ViewModelProvider(this, catFactory)[CategoryViewModel::class.java]


        val questionFactory = QuestionListViewModelFactory(repository)
        val questionListViewModel = ViewModelProvider(this, questionFactory)[QuestionListViewModel::class.java]


        val addFactory = AddQuestionViewModelFactory(repository)
        val addQuestionViewModel = ViewModelProvider(this, addFactory)[AddQuestionViewModel::class.java]

        Log.e("MEDQUIZ_TEST", "✅ Tüm ViewModel'ler başarıyla kuruldu!")

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "Sistem Hazır!\n(Logcat'e bakabilirsin)")
                    }
                }
            }
        }
    }
}