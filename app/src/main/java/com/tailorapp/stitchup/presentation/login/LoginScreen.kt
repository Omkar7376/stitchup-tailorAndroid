package com.tailorapp.stitchup.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tailorapp.stitchup.R
import com.tailorapp.stitchup.presentation.login.component.LoginTextField
import com.tailorapp.stitchup.ui.theme.Blue
import com.tailorapp.stitchup.ui.theme.Gray
import com.tailorapp.stitchup.ui.theme.dimens

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.message) {
        state.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    LaunchedEffect(state.loginSuccessEvent) {
        if (state.loginSuccessEvent) {
            viewModel.consumeLoginEvent()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Surface {
            Column(modifier = Modifier.fillMaxSize()) {
                TopSection()
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium2))
                Column(
                    modifier = Modifier
                        .fillMaxHeight(fraction = 0.60f)
                        .padding(horizontal = 30.dp)
                ) {
                    LoginSection(
                        state = state,
                        onEmailChange = viewModel::onEmailChanged,
                        onPasswordChange = viewModel::onPasswordChanged,
                        onLoginClick = viewModel::login
                    )
                }
                LastSection()
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                    strokeWidth = 3.dp
                )
            }
        }
    }
}

@Composable
fun TopSection() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Box(contentAlignment = Alignment.TopCenter) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.46f),
            painter = painterResource(id = R.drawable.shape), contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxHeight(fraction = 0.30f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(id = R.drawable.stitchuplogo3), contentDescription = null)
            Text("Tailor App", color = uiColor, fontWeight = FontWeight.Bold, fontSize = 18.sp, style = typography.bodyLarge)
        }

        Text(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.BottomCenter),
            text = "Login",
            style = typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = uiColor
        )
    }
}

@Composable
fun LoginSection(
    state: LoginUIState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    LoginTextField(
        modifier = Modifier.fillMaxWidth(),
        label = "Email",
        trailing = "",
        value = state.email,
        onValueChange = onEmailChange
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
    LoginTextField(
        label = "Password",
        trailing = "Forgot?",
        modifier = Modifier.fillMaxWidth(),
        value = state.password,
        onValueChange = onPasswordChange
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.buttonHeight),
        onClick = { onLoginClick() },
        enabled = !state.isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) Gray else Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp)

    ) {
        Text(text = "Login", style = typography.labelMedium.copy(fontWeight = FontWeight.Medium), fontWeight = FontWeight.Bold)
    }

//    if (state.error != null) {
//        Text(
//            text = state.error,
//            color = Color.Red,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//    }
}

@Composable
fun LastSection() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Box(
        modifier = Modifier
            .fillMaxHeight(fraction = 0.8f)
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xFF94A3B8),
                        fontSize = typography.labelMedium.fontSize,
                        fontWeight = FontWeight.Normal
                    )
                ){
                    append("Don't have account?")
                }
                withStyle(
                    style = SpanStyle(
                        color = uiColor,
                        fontSize = typography.labelMedium.fontSize,
                        fontWeight = FontWeight.Normal
                    )
                ){
                    append(" ")
                    append("Create now")
                }
            }
        )
    }
}
