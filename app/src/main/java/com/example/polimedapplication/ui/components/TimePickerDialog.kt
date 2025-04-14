import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.polimedapplication.ui.screens.AlbastruPoliMed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit,
    accentColor: Color = AlbastruPoliMed,
    onDismissRequest: () -> Unit,
    albastruPoliMed: Color,
    onTimeConfirmed: Any
) {
    if (showDialog) {
        val timePickerState = rememberTimePickerState()

        TimePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onTimeSelected(timePickerState.hour, timePickerState.minute)
                    onDismiss()
                }) {
                    Text("OK", color = accentColor)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Anulează", color = accentColor)
                }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

fun TimePickerDialog(onDismissRequest: () -> Unit, confirmButton: @Composable () -> Unit, dismissButton: @Composable () -> Unit, function: @Composable () -> Unit) {

}
