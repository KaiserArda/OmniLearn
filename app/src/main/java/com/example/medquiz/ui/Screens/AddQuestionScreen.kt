package com.example.medquiz.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
// Kendi data class'ının ve ViewModel'inin importlarını kontrol et
import com.example.medquiz.R
// import com.example.medquiz.data.Question  <-- Kendi Question modelini import etmelisin
// import com.example.medquiz.viewmodel.QuestionViewModel <-- ViewModel importu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionScreen(
    defaultCategoryId: Long?,
    // viewModel: QuestionViewModel = viewModel(), // Eğer Hilt veya ViewModel kullanıyorsan burayı aç
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // State Variables
    var questionText by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var optionC by remember { mutableStateOf("") }
    var optionD by remember { mutableStateOf("") }
    var selectedOption by remember { mutableIntStateOf(0) } // 1=A, 2=B, 3=C, 4=D
    var explanation by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) } // Mesaj rengini kırmızı yapmak için

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title_add_new_question),
            style = MaterialTheme.typography.headlineSmall
        )

        // Question Input
        OutlinedTextField(
            value = questionText,
            onValueChange = { questionText = it },
            label = { Text(stringResource(id = R.string.label_question_text)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Text(
            text = stringResource(id = R.string.label_options),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Options with Radio Buttons
        OptionRow(label = "A)", text = optionA, onTextChange = { optionA = it }, isSelected = selectedOption == 1, onSelect = { selectedOption = 1 }, placeholderRes = R.string.label_option_a)
        OptionRow(label = "B)", text = optionB, onTextChange = { optionB = it }, isSelected = selectedOption == 2, onSelect = { selectedOption = 2 }, placeholderRes = R.string.label_option_b)
        OptionRow(label = "C)", text = optionC, onTextChange = { optionC = it }, isSelected = selectedOption == 3, onSelect = { selectedOption = 3 }, placeholderRes = R.string.label_option_c)
        OptionRow(label = "D)", text = optionD, onTextChange = { optionD = it }, isSelected = selectedOption == 4, onSelect = { selectedOption = 4 }, placeholderRes = R.string.label_option_d)

        // Explanation (Optional)
        OutlinedTextField(
            value = explanation,
            onValueChange = { explanation = it },
            label = { Text(stringResource(id = R.string.label_explanation)) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        // SAVE BUTTON LOGIC
        Button(
            onClick = {
                // 1. Validation Logic
                if (questionText.isBlank()) {
                    message = context.getString(R.string.err_missing_question)
                    isError = true
                } else if (optionA.isBlank() || optionB.isBlank() || optionC.isBlank() || optionD.isBlank()) {
                    message = context.getString(R.string.err_missing_options)
                    isError = true
                } else if (selectedOption == 0) {
                    message = context.getString(R.string.err_select_correct_opt)
                    isError = true
                } else {
                    // All valid! Proceed to save.

                    // Determine correct answer string based on selection
                    val correctAnsStr = when(selectedOption) {
                        1 -> optionA
                        2 -> optionB
                        3 -> optionC
                        4 -> optionD
                        else -> ""
                    }

                    // --- REAL SAVING LOGIC START ---
                    // Burada oluşturduğun soru objesini veritabanına göndermelisin.
                    // Örnek:
                    /*
                    val newQuestion = Question(
                        categoryId = defaultCategoryId ?: 0,
                        text = questionText,
                        options = listOf(optionA, optionB, optionC, optionD),
                        correctAnswer = correctAnsStr,
                        explanation = explanation // Can be empty
                    )
                    viewModel.insertQuestion(newQuestion)
                    */
                    // --- REAL SAVING LOGIC END ---

                    onBack() // Go back to list
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(text = stringResource(id = R.string.btn_save_question))
        }

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
// Bu kodu AddQuestionScreen fonksiyonunun DIŞINA, dosyanın en altına yapıştır:

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionRow(
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    isSelected: Boolean,
    onSelect: () -> Unit,
    placeholderRes: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // A), B) gibi etiketler
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        // Metin kutusu
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text(stringResource(id = placeholderRes)) },
            modifier = Modifier.weight(1f),
            singleLine = true
        )
        // Seçim yuvarlağı
        RadioButton(
            selected = isSelected,
            onClick = onSelect
        )
    }
}