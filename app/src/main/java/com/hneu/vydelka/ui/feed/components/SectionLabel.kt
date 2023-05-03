package com.hneu.vydelka.ui.feed.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionLabel(text:String) {
    Text(
        text = text,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}