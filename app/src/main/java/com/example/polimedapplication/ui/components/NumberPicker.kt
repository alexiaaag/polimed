package com.example.polimedapplication.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

@Composable
fun NumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    label: String,
    accentColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 14.sp, color = accentColor)
        LazyColumn(
            modifier = Modifier
                .height(100.dp)
                .width(60.dp)
                .border(1.dp, accentColor, RoundedCornerShape(8.dp))
        ) {
            items(range.toList()) { item ->
                TextButton(onClick = { onValueChange(item) }) {
                    Text(
                        text = String.format("%02d", item),
                        fontSize = if (item == value) 20.sp else 16.sp,
                        color = if (item == value) accentColor else Color.Gray
                    )
                }
            }
        }
    }
}
