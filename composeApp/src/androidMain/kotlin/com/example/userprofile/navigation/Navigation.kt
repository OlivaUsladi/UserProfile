package com.example.userprofile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.userprofile.feature.EditProfileScreen
import com.example.userprofile.feature.ProfileScreen
import com.example.userprofile.feature.ProfileViewModel
import com.example.userprofile.feature.SettingsScreen

sealed class Routes(val route: String) {

    object Home : Routes("home")
    object Settings : Routes("settings")
    object EditProfile: Routes("editprofile")
}

@Composable
fun ProfileNavHost(navController: NavHostController, viewModel: ProfileViewModel) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            ProfileScreen(viewModel, navController)
        }

        composable(Routes.Settings.route) {
            SettingsScreen(navController, viewModel)
        }
        composable(Routes.EditProfile.route) {
            EditProfileScreen(viewModel, navController)
        }


    }
}