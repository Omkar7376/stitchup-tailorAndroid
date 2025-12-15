package com.tailorapp.stitchup.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tailorapp.stitchup.presentation.AuthState
import com.tailorapp.stitchup.presentation.MainViewModel
import com.tailorapp.stitchup.presentation.home.HomeScreen
import com.tailorapp.stitchup.presentation.login.LoginScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val authState by mainViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        Log.d("###", "Auth_state: $authState")
        when (authState) {
            AuthState.Authenticated -> {
                navController.navigate("home") {
                    popUpTo(0) { inclusive = true }
                }
            }
            AuthState.Unauthenticated -> {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            }
            AuthState.Loading -> Unit
        }
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen()
        }
        composable("home") {
            HomeScreen()
        }
    }
}