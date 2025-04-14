// com/example/polimedapplication/ui/components/CustomTimePickerDialog.kt
package com.example.polimedapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    onTimeConfirmed: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
    albastruPoliMed: Color
) {
    val timeState = rememberTimePickerState()

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(
                state = timeState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = albastruPoliMed.copy(alpha = 0.2f),
                    selectorColor = albastruPoliMed,
                    timeSelectorSelectedContainerColor = albastruPoliMed,
                    timeSelectorSelectedContentColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Anulează")
                }
                TextButton(onClick = {
                    onTimeConfirmed(timeState.hour, timeState.minute)
                }) {
                    Text("Confirmă")
                }
            }
        }
    }
}
