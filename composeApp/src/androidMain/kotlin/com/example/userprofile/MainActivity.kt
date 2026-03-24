package com.example.userprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.userprofile.feature.ProfileViewModel
import com.example.userprofile.feature.UserProfileTheme
import com.example.userprofile.navigation.ProfileNavHost
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            UserProfileApp()
        }
    }
}

@Composable
fun UserProfileApp() {
    val viewModel: ProfileViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    // Применяем тему в зависимости от настройки
    UserProfileTheme(
        darkTheme = state.settings.darkTheme
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            ProfileNavHost(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

