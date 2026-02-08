package com.tailorapp.stitchup.presentation.customerDetails.componenents

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.SoftGolden
import com.tailorapp.stitchup.ui.theme.White
import com.tailorapp.stitchup.ui.theme.textFieldContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdown(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    modifier: Modifier
) {
    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown
    val genders = listOf("Male", "Female", "Other")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = { onGenderSelected(it) },
            readOnly = true,
            label = { Text("Gender", style = MaterialTheme.typography.labelMedium, color = uiColor) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedPlaceholderColor = uiColor.copy(alpha = 0.5f),
                focusedPlaceholderColor = uiColor,
                unfocusedContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.textFieldContainer else White,
                focusedContainerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.textFieldContainer else White,
                focusedBorderColor = uiColor,
                unfocusedBorderColor = uiColor.copy(alpha = 0.5f),
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(text = gender) },
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}