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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddQuestionScreen(
    defaultCategoryId: Long?,
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
    var selectedOption by remember { mutableIntStateOf(0) }
    var explanation by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    // --- KRİTİK DEĞİŞİKLİK BURADA BAŞLIYOR ---
    // Surface: Sayfanın zemin rengini telefonun temasına (Karanlık/Aydınlık) göre ayarlar.
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Telefon siyahsa burası siyah olur
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
                color = MaterialTheme.colorScheme.onBackground // Zemin siyahsa yazı beyaz olur
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

            // Şıklar (OptionRow fonksiyonunu kullanır)
            OptionRow("A)", optionA, { optionA = it }, selectedOption == 1, { selectedOption = 1 }, R.string.label_option_a)
            OptionRow("B)", optionB, { optionB = it }, selectedOption == 2, { selectedOption = 2 }, R.string.label_option_b)
            OptionRow("C)", optionC, { optionC = it }, selectedOption == 3, { selectedOption = 3 }, R.string.label_option_c)
            OptionRow("D)", optionD, { optionD = it }, selectedOption == 4, { selectedOption = 4 }, R.string.label_option_d)

            // Açıklama (İsteğe Bağlı)
            OutlinedTextField(
                value = explanation,
                onValueChange = { explanation = it },
                label = { Text(stringResource(id = R.string.label_explanation) + " (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Kaydet Butonu
            Button(
                onClick = {
                    if (questionText.isBlank() || optionA.isBlank() || optionB.isBlank() || optionC.isBlank() || optionD.isBlank() || selectedOption == 0) {
                        message = context.getString(R.string.err_empty_fields_or_selection)
                    } else {
                        // Şimdilik sadece geri dönüyor, veritabanı işini sonra halledeceğiz
                        onBack()
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

// --- YARDIMCI FONKSİYON (Dosyanın en altında dursun) ---
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
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground // Temaya uyumlu yazı rengi
        )
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text(stringResource(id = placeholderRes)) },
            modifier = Modifier.weight(1f),
            singleLine = true
        )
        RadioButton(
            selected = isSelected,
            onClick = onSelect
        )
    }
}