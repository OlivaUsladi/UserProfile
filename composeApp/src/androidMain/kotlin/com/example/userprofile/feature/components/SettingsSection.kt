package com.example.userprofile.feature.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.userprofile.feature.rememberAppStrings
import com.example.userprofile.model.domain.AppLanguage

@Composable
fun SettingsSection(
    darkTheme: Boolean,
    notificationsEnabled: Boolean,
    language: AppLanguage,
    fontSize: Float,
    onDarkThemeChange: (Boolean) -> Unit,
    onNotificationsChange: (Boolean) -> Unit,
    onLanguageChange: (AppLanguage) -> Unit,
    onFontSizeChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val appStrings = rememberAppStrings(language)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ScaledText(
                text = appStrings.settings,
                fontSize = scaledTextSize(18f, fontSize),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScaledText(
                    text = appStrings.darkTheme,
                    fontSize = scaledTextSize(14f, fontSize),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Switch(
                    checked = darkTheme,
                    onCheckedChange = onDarkThemeChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScaledText(
                    text = appStrings.notifications,
                    fontSize = scaledTextSize(14f, fontSize),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = onNotificationsChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScaledText(
                    text = appStrings.languageText,
                    fontSize = scaledTextSize(14f, fontSize),
                    color = MaterialTheme.colorScheme.onSurface
                )
                LanguageDropdown(
                    selectedLanguage = language,
                    onLanguageSelected = onLanguageChange
                )
            }

            Column {
                ScaledText(
                    text = "${appStrings.fontSize}: ${fontSize.toInt()}px",
                    fontSize = scaledTextSize(14f, fontSize),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Slider(
                    value = fontSize,
                    onValueChange = onFontSizeChange,
                    valueRange = 12f..24f,
                    steps = 12,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}