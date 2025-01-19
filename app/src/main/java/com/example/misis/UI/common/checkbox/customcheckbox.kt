package com.example.misis.UI.common.checkbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomCheckbox(
    isChecked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    boxSize: Dp = 24.dp,
    checkIconSize: Dp = 16.dp,
    checkIconColor: Color = Color.White
) {
    val boxBackgroundColor = resolveCheckboxBackgroundColor(isChecked)

    Box(
        modifier = modifier
            .size(boxSize)
            .clip(CircleShape)
            .background(boxBackgroundColor)
            .clickable { onCheckChanged(!isChecked) },
        contentAlignment = Alignment.Center
    ) {
        if (isChecked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = checkIconColor,
                modifier = Modifier.size(checkIconSize)
            )
        }
    }
}

@Composable
private fun resolveCheckboxBackgroundColor(isChecked: Boolean): Color {
    return if (isChecked) {
        MaterialTheme.colorScheme.surface
    } else {
        Color.White
    }
}
