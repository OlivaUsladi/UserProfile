package com.example.userprofile.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.userprofile.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.userprofile.feature.components.ScaledText
import com.example.userprofile.feature.components.scaledTextSize
import com.example.userprofile.feature.domain.ValidateNameUseCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val appStrings = rememberAppStrings(state.settings.language)
    val validateNameUseCase = remember { ValidateNameUseCase() }

    var nameInput by remember(state.userProfile.name) { mutableStateOf(state.userProfile.name) }
    var emailInput by remember(state.userProfile.email) { mutableStateOf(state.userProfile.email) }
    var localNameError by remember { mutableStateOf<String?>(null) }

    val brush = getGradientBrush(state.settings.darkTheme)

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.clearSnackbarMessage()
        }
    }

    LaunchedEffect(nameInput) {
        val result = validateNameUseCase(nameInput)
        localNameError = when (result) {
            is ValidateNameUseCase.ValidationResult.Error -> result.message
            else -> null
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
                            text = appStrings.editProfile,
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ScaledText(
                        text = appStrings.editProfile,
                        fontSize = scaledTextSize(24f, state.settings.fontSize),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { ScaledText(appStrings.name, scaledTextSize(14f, state.settings.fontSize)) },
                        isError = localNameError != null,
                        supportingText = {
                            if (localNameError != null) {
                                ScaledText(
                                    text = localNameError!!,
                                    fontSize = scaledTextSize(12f, state.settings.fontSize),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )

                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { ScaledText(appStrings.email, scaledTextSize(14f, state.settings.fontSize)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (localNameError == null && nameInput.isNotBlank()) {
                                viewModel.updateName(nameInput)
                                viewModel.updateEmail(emailInput)
                                viewModel.saveSettings()
                                navController.navigateUp()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = localNameError == null && nameInput.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        ScaledText(
                            text = appStrings.save,
                            fontSize = scaledTextSize(14f, state.settings.fontSize),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}