package com.example.medquiz.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medquiz.data.local.entity.QuestionEntity
import com.example.medquiz.data.repository.QuizRepository
import kotlinx.coroutines.launch

class AddQuestionViewModel(private val repo: QuizRepository) : ViewModel() {

    // UI'dan gelen ham verileri alıp işleyen fonksiyon
    fun saveQuestion(
        categoryId: Long,
        rawText: String,
        options: List<String>, // Ekrandan [A, B, C, D] listesi gelecek
        correctIndex: Int,     // Doğru şıkkın sırası (0, 1, 2 veya 3)
        explanation: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            // 1. Veritabanından mevcut soru sayısını öğren (Örn: 200)
            val currentCount = repo.getQuestionCount(categoryId)
            val nextNumber = currentCount + 1

            // 2. Başlığı oluştur ve soru metninin başına ekle
            // Çıktı Örneği: "Soru 201 (Kullanıcı Tarafından): Mitokondri nedir?"
            val prefix = "Soru $nextNumber (Kullanıcı Tarafından):"
            val finalText = "$prefix $rawText"

            // 3. Entity nesnesini senin veritabanı yapına göre oluştur
            val newQuestion = QuestionEntity(
                id = 0, // Otomatik ID
                categoryId = categoryId,
                questionText = finalText,
                // Listeyi parçalayıp senin entity'deki option1, option2... alanlarına atıyoruz
                option1 = options.getOrElse(0) { "" },
                option2 = options.getOrElse(1) { "" },
                option3 = options.getOrElse(2) { "" },
                option4 = options.getOrElse(3) { "" },
                correctIndex = correctIndex,
                explanation = explanation,
                isUserCreated = true
            )

            // 4. Veritabanına kaydet
            repo.insertQuestion(newQuestion)

            // 5. İşlem bitti, ekranı kapatmak için geri bildir
            onSuccess()
        }
    }
}