package com.example.medquiz.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.delay

@Composable
fun getLocalizedText(dbKey: String): String {
    val context = LocalContext.current

    // 'remember' to avoid heavy resource lookups on every frame/recomposition
    return remember(dbKey, context.resources.configuration.locales) {
        try {
            val resourceId = context.resources.getIdentifier(
                dbKey,
                "string",
                context.packageName
            )
            // If resource exists, return translated string; otherwise return the key itself
            if (resourceId != 0) {
                context.getString(resourceId)
            } else {
                dbKey
            }
        } catch (e: Exception) {
            dbKey // Fallback to key on error
        }
    }
}


fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    var isClickable by remember { mutableStateOf(true) }

    // Delay for click
    LaunchedEffect(isClickable) {
        if (!isClickable) {
            delay(1500) // Cooldown
            isClickable = true
        }
    }

    Modifier.clickable(enabled = enabled && isClickable) {
        if (isClickable) {
            isClickable = false
            onClick()
        }
    }
}