package com.example.polimedapplication.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit, // 👈 trimitem formatul pt. DB
    accentColor: Color
) {
    if (showDialog) {
        val datePickerState = rememberDatePickerState()
        val locale = Locale("ro", "RO")

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        // ✅ format corect pentru DB: yyyy-MM-dd
                        val dbFormat = SimpleDateFormat("yyyy-MM-dd", locale).format(Date(millis))
                        onDateSelected(dbFormat)
                    }
                    onDismiss()
                }) {
                    Text("Selectează", color = accentColor)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Renunță", color = accentColor)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = accentColor,
                    selectedYearContainerColor = accentColor,
                    todayContentColor = accentColor,
                    todayDateBorderColor = accentColor
                )
            )
        }
    }
}


