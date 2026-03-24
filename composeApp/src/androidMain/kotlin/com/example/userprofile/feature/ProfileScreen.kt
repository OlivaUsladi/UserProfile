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
import com.example.userprofile.feature.components.scaledTextSize
import com.example.userprofile.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val appStrings = rememberAppStrings(state.settings.language)
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

    if (state.showLogoutDialog) {
        AlertDialog(
            onDismissRequest = viewModel::hideLogoutDialog,
            title = {
                ScaledText(
                    text = appStrings.logoutConfirmTitle,
                    fontSize = scaledTextSize(20f, state.settings.fontSize)
                )
            },
            text = {
                ScaledText(
                    text = appStrings.logoutConfirmMessage,
                    fontSize = scaledTextSize(16f, state.settings.fontSize)
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.logout()
                }) {
                    ScaledText(
                        text = appStrings.logout,
                        fontSize = scaledTextSize(14f, state.settings.fontSize)
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::hideLogoutDialog) {
                    ScaledText(
                        text = appStrings.cancel,
                        fontSize = scaledTextSize(14f, state.settings.fontSize)
                    )
                }
            }
        )
    }

    if (state.error != null) {
        LaunchedEffect(state.error) {
            snackbarHostState.showSnackbar(
                message = state.error!!,
                duration = SnackbarDuration.Short
            )
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
                            text = appStrings.profileTitle,
                            fontSize = scaledTextSize(18f, state.settings.fontSize),
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
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
                        containerColor = Color.Transparent
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.person),
                            contentDescription = "Avatar",
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        ScaledText(
                            text = state.userProfile.name.ifEmpty { appStrings.notSpecified },
                            fontSize = scaledTextSize(24f, state.settings.fontSize),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        ScaledText(
                            text = state.userProfile.email.ifEmpty { appStrings.notSpecified },
                            fontSize = scaledTextSize(16f, state.settings.fontSize),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )

                        Button(
                            onClick = { navController.navigate(Routes.EditProfile.route) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            ScaledText(
                                text = appStrings.editProfile,
                                fontSize = scaledTextSize(14f, state.settings.fontSize),
                                color = Color.White
                            )
                        }
                    }
                }

                OutlinedButton(
                    onClick = { navController.navigate(Routes.Settings.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ScaledText(
                        text = appStrings.settings,
                        fontSize = scaledTextSize(14f, state.settings.fontSize),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                OutlinedButton(
                    onClick = viewModel::showLogoutDialog,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ScaledText(
                        text = appStrings.logout,
                        fontSize = scaledTextSize(14f, state.settings.fontSize),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}