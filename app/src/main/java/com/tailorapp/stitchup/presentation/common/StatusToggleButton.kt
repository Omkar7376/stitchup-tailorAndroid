package com.tailorapp.stitchup.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatusToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    uiColor: Color
) {
    Button(
        onClick = onClick,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = if (isSelected) uiColor else uiColor.copy(alpha = 0.1f),
            contentColor = if (isSelected) Color.White else uiColor
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, uiColor)
    ) {
        Text(text = text, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
    }
}