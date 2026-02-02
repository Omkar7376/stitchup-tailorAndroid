package com.tailorapp.stitchup.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.SoftGolden

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    placeholderText: String = "Search..."
) {
    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(placeholderText, color = uiColor.copy(alpha = 0.3f)) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        trailingIcon = {
            Icon(
                modifier = Modifier.padding(end = 10.dp),
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = uiColor
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = uiColor,
            unfocusedBorderColor = uiColor,
            focusedTextColor = uiColor,
            unfocusedTextColor = uiColor,
            cursorColor = uiColor,
            selectionColors = TextSelectionColors(
                backgroundColor = Color(0xFF03A9F4),
                handleColor = uiColor
            )
        )
    )
}