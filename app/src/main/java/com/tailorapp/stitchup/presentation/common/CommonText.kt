package com.tailorapp.stitchup.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.tailorapp.stitchup.ui.theme.DarkBrown
import com.tailorapp.stitchup.ui.theme.SoftGolden

@Composable
fun CommonText(
    label : String,
) {
    val uiColor = if (isSystemInDarkTheme()) SoftGolden else DarkBrown
    Text(
        text = label,
        color = uiColor,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
    )
}
