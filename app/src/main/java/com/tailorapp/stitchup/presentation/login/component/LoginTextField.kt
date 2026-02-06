package com.tailorapp.stitchup.presentation.login.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.SoftGolden
import com.tailorapp.stitchup.ui.theme.White
import com.tailorapp.stitchup.ui.theme.focusedTextFieldText
import com.tailorapp.stitchup.ui.theme.textFieldContainer
import com.tailorapp.stitchup.ui.theme.unfocusedTextFieldText

@Composable
fun LoginTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = uiColor)
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedPlaceholderColor = uiColor.copy(alpha = 0.5f),
            focusedPlaceholderColor = uiColor,
            unfocusedContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.textFieldContainer else White,
            focusedContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.textFieldContainer else White,
            focusedBorderColor = uiColor,
            unfocusedBorderColor = uiColor.copy(alpha = 0.5f),
        ),
        singleLine = true
    )
}