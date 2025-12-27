package com.tailorapp.stitchup.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tailorapp.stitchup.presentation.AuthState
import com.tailorapp.stitchup.presentation.MainViewModel
import com.tailorapp.stitchup.presentation.home.HomeScreen
import com.tailorapp.stitchup.presentation.login.LoginScreen
import com.tailorapp.stitchup.presentation.registration.RegisterScreen

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

    if (authState is AuthState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {}
                ),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                strokeWidth = 3.dp
            )
        }
    } else {
        val startDest = if (authState is AuthState.Authenticated) "home" else "login"
        NavHost(
            navController = navController,
            startDestination = startDest
        ) {
            composable("login") {
                LoginScreen(navController)
            }
            composable("home") {
                HomeScreen()
            }
            composable("register") {
                RegisterScreen(navController)
            }
        }
    }
}