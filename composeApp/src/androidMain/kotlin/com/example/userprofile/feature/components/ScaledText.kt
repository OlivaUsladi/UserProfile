package com.example.userprofile.feature.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun ScaledText(
    text: String,
    fontSize: Float,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = fontSize.sp,
        color = color,
        style = style
    )
}


@Composable
fun scaledTextSize(baseSize: Float, fontSize: Float): Float {
    val scale = fontSize / 16f
    return baseSize * scale
}