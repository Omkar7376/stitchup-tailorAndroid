package com.tailorapp.stitchup.presentation.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tailorapp.stitchup.presentation.login.LoginUIState
import com.tailorapp.stitchup.presentation.login.LoginViewModel
import com.tailorapp.stitchup.ui.theme.Blue
import com.tailorapp.stitchup.ui.theme.Gray
import com.tailorapp.stitchup.ui.theme.dimens

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    Box (
        modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.Center
    ){
        Logout(
            onLogoutClick = viewModel::logout
        )
    }
}

@Composable
fun Logout(
    onLogoutClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.buttonHeight),
        onClick = { onLogoutClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) Gray else Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp)

    ) {
        Text(
            text = "Logout",
            style = typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            fontWeight = FontWeight.Bold
        )
    }
}