package com.cso.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cso.ui.components.TimePicker

@Composable
fun SettingsPopup(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    state: SettingsState,
    onPomodoroTimeChange: (Int) -> Unit,
    onShortBreakTimeChange: (Int) -> Unit,
    onLongBreakTimeChange: (Int) -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text("Settings")
            },
            text = {
                Column {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TimePicker("Pomodoro", state.pomodoroDuration, onPomodoroTimeChange)
                        TimePicker("Short Break", state.shortBreakDuration, onShortBreakTimeChange)
                        TimePicker("Long Break", state.longBreakDuration, onLongBreakTimeChange)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    onDismiss()
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}