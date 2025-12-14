package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.QuestionEntity
import com.example.medquiz.data.repository.QuizRepository
import kotlinx.coroutines.launch
import java.util.Locale // <-- Bunu unutma

class AddQuestionViewModel(private val repo: QuizRepository) : ViewModel() {


    fun saveQuestion(
        categoryId: Long,
        rawText: String,
        options: List<String>,
        correctIndex: Int,
        explanation: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {

            val currentLang = Locale.getDefault().language


            val currentCount = repo.getQuestionCount(categoryId, currentLang)
            val nextNumber = currentCount + 1


            val prefix = if (currentLang == "tr") {
                "Soru $nextNumber (Kullanıcı Tarafından):"
            } else {
                "Question $nextNumber (User Created):"
            }

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


            onSuccess()
        }
    }
}