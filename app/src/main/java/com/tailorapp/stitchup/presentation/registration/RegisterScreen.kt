package com.tailorapp.stitchup.presentation.registration

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tailorapp.stitchup.R
import com.tailorapp.stitchup.data.remote.dto.registerDto.RegisterRequestDto
import com.tailorapp.stitchup.presentation.login.component.LoginTextField
import com.tailorapp.stitchup.ui.theme.Blue
import com.tailorapp.stitchup.ui.theme.Gray
import com.tailorapp.stitchup.ui.theme.dimens
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val authState = viewModel.authState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(authState.value) {
        Log.d("###", "State: ${authState.value}")
    }

    LaunchedEffect(authState.value.message) {
        authState.value.message?.let {
            snackBarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    LaunchedEffect(authState.value.authenticated) {
        if (authState.value.authenticated) {
            viewModel.consumeRegisterEvent()
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearState()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TopSection()
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
                LazyColumn (
                    modifier = Modifier
                        .fillMaxHeight(fraction = 0.80f)
                        .padding(horizontal = 30.dp)
                ) {
                    item {
                        RegisterSection(
                            state = authState.value,
                            name = name,
                            userName = userName,
                            age = age,
                            email = email,
                            phone = phone,
                            address = address,
                            password = password,

                            onNameChange = { name = it },
                            onUserNameChange = { userName = it },
                            onAgeChange = { age = it },
                            onAddressChange = { address = it },
                            onEmailChange = { email = it },
                            onPhoneChange = { phone = it },
                            onPasswordChange = { password = it },

                            onRegisterClick = {
                                when{
                                    name.isBlank() -> {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Name is required")
                                        }
                                    }
                                    userName.isBlank() -> {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Username is required")
                                        }
                                    }
                                    age.isBlank() -> {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Age is required")
                                        }
                                    }
                                    email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Email is required with proper format")
                                        }
                                    }
                                    phone.isBlank() -> {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Phone is required")
                                        }
                                    }
                                    phone.length <  10 -> {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Invalid phone number")
                                        }
                                    }
                                    password.isBlank() -> {
                                        scope.launch {
                                            snackBarHostState.showSnackbar("Password is required")
                                        }
                                    }
                                    else -> {
                                        val req = RegisterRequestDto(
                                            name = name,
                                            username = userName,
                                            age = age.toInt(),
                                            email = email,
                                            mob_no = phone,
                                            address = address,
                                            password = password
                                        )
                                        viewModel.register(req)
                                        Log.d("###", "RegisterScreen: ${viewModel.register(req)}")
                                    }
                                }
                            }
                        )
                    }

                }
                LastSection(
                    onSignInClick = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                )
            }
        }
        if (authState.value.isLoading) {
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
            text = "Register",
            style = typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = uiColor
        )
    }
}

@Composable
fun RegisterSection(
    state: AuthState,

    name: String,
    userName: String,
    age: String,
    email: String,
    phone: String,
    address: String,
    password: String,
    
    onNameChange: (String) -> Unit,
    onUserNameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,

    onRegisterClick: () -> Unit,
) {
    LoginTextField(
        modifier = Modifier.fillMaxWidth(),
        label = "Enter Full Name",
        value = name,
        onValueChange = { onNameChange(it) }
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
    LoginTextField(
        label = "Username",
        modifier = Modifier.fillMaxWidth(),
        value = userName,
        onValueChange = { onUserNameChange(it) }
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        LoginTextField(
            label = "Age",
            modifier = Modifier.weight(1f),
            value = age,
            onValueChange = { onAgeChange(it) }
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
        LoginTextField(
            label = "Mobile Number",
            modifier = Modifier.weight(2f),
            value = phone,
            onValueChange = { onPhoneChange(it) }
        )
    }
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
    LoginTextField(
        label = "Address",
        modifier = Modifier.fillMaxWidth(),
        value = address,
        onValueChange = { onAddressChange(it) }
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
    LoginTextField(
        label = "Email",
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = { onEmailChange(it) }
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
    LoginTextField(
        label = "Password",
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { onPasswordChange(it) }
    )
    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimens.buttonHeight),
        onClick = { onRegisterClick() },
        enabled = !state.isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSystemInDarkTheme()) Gray else Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp)

    ) {
        Text(text = "Register", style = typography.labelMedium.copy(fontWeight = FontWeight.Medium), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LastSection(
    onSignInClick: () -> Unit = {}
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

    val text = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                color = uiColor,
            )
        ) {
            append("Already have an account? ")
        }

        pushStringAnnotation(
            tag = "LOGIN",
            annotation = "login"
        )

        withStyle(
            style = SpanStyle(
                color = uiColor,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("Login")
        }
        pop()
    }

    Box(
        modifier = Modifier
            .fillMaxHeight(fraction = 0.8f)
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        ClickableText(
            text = text,
            style = typography.labelMedium,
            onClick = { offset ->
                text.getStringAnnotations(
                    tag = "LOGIN",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onSignInClick()
                }
            }
        )
    }
}

//@Preview(
//    showBackground = true ,
//    showSystemUi = true,
//    name = "Register Screen",
//
//)
//@Composable
//fun PreviewScreen(modifier: Modifier = Modifier) {
//    RegisterScreen(navController = NavController(LocalContext.current))
//}