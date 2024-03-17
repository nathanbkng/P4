package com.example.nf_frontend.screens.homescreen.composant.addcourse

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

@Composable
fun ColorPicker(
    selectedColor: Int,
    onColorSelected: (Int) -> Unit
) {
    val colors = listOf(Color.Blue, Color.Red, Color.Green, Color.Yellow, Color.Magenta, Color.Cyan)

    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(colors) { color ->
            ColorButton(
                color = color,
                isSelected = color.toArgb() == selectedColor,
                onClick = { onColorSelected(color.toArgb()) }
            )
        }
    }
}

@Composable
fun ColorButton(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick() }
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = Color.Black,
                shape = CircleShape
            )
    )
}
