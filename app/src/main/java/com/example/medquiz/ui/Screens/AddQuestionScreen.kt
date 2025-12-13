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
import com.example.medquiz.R
import com.example.medquiz.vm.AddQuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionScreen(
    defaultCategoryId: Long?,
    viewModel: AddQuestionViewModel, // NavGraph'tan gelecek
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Değişkenler
    var questionText by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var optionC by remember { mutableStateOf("") }
    var optionD by remember { mutableStateOf("") }

    // Radyo butonu seçimi: 1=A, 2=B, 3=C, 4=D
    var selectedOption by remember { mutableIntStateOf(0) }
    var explanation by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    // --- KARANLIK MOD DESTEĞİ (Surface) ---
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Başlık
            Text(
                text = stringResource(id = R.string.title_add_new_question),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Soru Alanı
            OutlinedTextField(
                value = questionText,
                onValueChange = { questionText = it },
                label = { Text(stringResource(id = R.string.label_question_text)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            // Seçenekler Başlığı
            Text(
                text = stringResource(id = R.string.label_options),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Şıklar
            OptionRow("A)", optionA, { optionA = it }, selectedOption == 1, { selectedOption = 1 }, R.string.label_option_a)
            OptionRow("B)", optionB, { optionB = it }, selectedOption == 2, { selectedOption = 2 }, R.string.label_option_b)
            OptionRow("C)", optionC, { optionC = it }, selectedOption == 3, { selectedOption = 3 }, R.string.label_option_c)
            OptionRow("D)", optionD, { optionD = it }, selectedOption == 4, { selectedOption = 4 }, R.string.label_option_d)

            // Açıklama
            OutlinedTextField(
                value = explanation,
                onValueChange = { explanation = it },
                label = { Text(stringResource(id = R.string.label_explanation)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            // KAYDET BUTONU
            Button(
                onClick = {
                    if (questionText.isBlank() || optionA.isBlank() || optionB.isBlank() || optionC.isBlank() || optionD.isBlank() || selectedOption == 0) {
                        message = context.getString(R.string.err_empty_fields_or_selection)
                    } else {
                        // Seçilen şıkkı (1-4 arası) indekse (0-3 arası) çeviriyoruz.
                        // Çünkü veritabanı "correctIndex" istiyor.
                        val correctIndex = selectedOption - 1

                        // ViewModel'e Gönder
                        viewModel.saveQuestion(
                            categoryId = defaultCategoryId ?: -1L,
                            rawText = questionText,
                            options = listOf(optionA, optionB, optionC, optionD),
                            correctIndex = correctIndex, // INT GÖNDERİYORUZ
                            explanation = explanation,
                            onSuccess = {
                                onBack() // Kayıt başarılı, geri dön
                            }
                        )
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
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// Yardımcı Fonksiyon
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
        Text(text = label, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
        OutlinedTextField(
            value = text, onValueChange = onTextChange,
            placeholder = { Text(stringResource(id = placeholderRes)) },
            modifier = Modifier.weight(1f), singleLine = true
        )
        RadioButton(selected = isSelected, onClick = onSelect)
    }
}