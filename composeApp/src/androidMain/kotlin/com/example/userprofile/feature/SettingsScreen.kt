package com.example.userprofile.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.userprofile.R
import com.example.userprofile.feature.components.ScaledText
import com.example.userprofile.feature.components.SettingsSection
import com.example.userprofile.feature.components.scaledTextSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val state by viewModel.state.collectAsState()
    val appStrings = rememberAppStrings(state.settings.language)
    val brush = getGradientBrush(state.settings.darkTheme)

    val snackbarHostState = remember { SnackbarHostState() }

    var showSavedMessage by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.clearSnackbarMessage()
        }
    }

    LaunchedEffect(state.isSaving) {
        if (!state.isSaving && showSavedMessage) {
            kotlinx.coroutines.delay(2000)
            showSavedMessage = false
            navController.navigateUp()
        }
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            showError = true
            kotlinx.coroutines.delay(3000)
            showError = false
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .background(brush)
                    .fillMaxWidth(),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        ScaledText(
                            text = appStrings.settings,
                            fontSize = scaledTextSize(18f, state.settings.fontSize),
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .background(brush),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsSection(
                darkTheme = state.settings.darkTheme,
                notificationsEnabled = state.settings.notificationsEnabled,
                language = state.settings.language,
                fontSize = state.settings.fontSize,
                onDarkThemeChange = { viewModel.updateDarkTheme(it) },
                onNotificationsChange = { viewModel.updateNotifications(it) },
                onLanguageChange = { viewModel.updateLanguage(it) },
                onFontSizeChange = { viewModel.updateFontSize(it) }
            )

            Button(
                onClick = {
                    viewModel.saveSettings()
                    showSavedMessage = true
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isSaving,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                    ScaledText(
                        text = appStrings.saveSettings,
                        fontSize = scaledTextSize(14f, state.settings.fontSize),
                        color = Color.White
                    )
                }
            }


            if (showError && state.error != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    ScaledText(
                        text = state.error!!,
                        fontSize = scaledTextSize(14f, state.settings.fontSize),
                        modifier = Modifier.padding(12.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}