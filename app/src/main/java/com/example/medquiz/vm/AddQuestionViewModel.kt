package com.example.medquiz.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.QuestionEntity
import com.example.medquiz.data.repository.QuizRepository
import kotlinx.coroutines.launch
import java.util.Locale

class AddQuestionViewModel(private val repo: QuizRepository) : ViewModel() {

    fun saveQuestion(
        categoryId: Long,
        rawText: String,
        options: List<String>,
        correctIndex: Int,
        explanation: String,
        prefixTemplate: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("AddQuestion", "Kaydediliyor... Kategori ID: $categoryId")

                val currentLang = Locale.getDefault().language
                val currentCount = repo.getQuestionCount(categoryId, currentLang)
                val nextNumber = currentCount + 1


                val prefix = String.format(prefixTemplate, nextNumber)
                val finalText = "$prefix $rawText"

                val newQuestion = QuestionEntity(
                    id = 0,
                    categoryId = categoryId,
                    questionText = finalText,
                    option1 = options.getOrElse(0) { "" },
                    option2 = options.getOrElse(1) { "" },
                    option3 = options.getOrElse(2) { "" },
                    option4 = options.getOrElse(3) { "" },
                    correctIndex = correctIndex,
                    explanation = explanation,
                    isUserCreated = true,
                    language = currentLang
                )

                repo.insertQuestion(newQuestion)

                // Loglar geliştirici içindir, İngilizce kalabilir veya silinebilir.
                Log.d("AddQuestion", "Success.")
                onSuccess()

            } catch (e: Exception) {
                Log.e("AddQuestion", "Error: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}