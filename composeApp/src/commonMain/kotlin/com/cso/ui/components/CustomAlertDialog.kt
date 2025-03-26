package com.cso.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimerSettingsPopup(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    pomodoroTime: Int,
    shortBreakTime: Int,
    longBreakTime: Int,
    onPomodoroTimeChange: (Int) -> Unit,
    onShortBreakTimeChange: (Int) -> Unit,
    onLongBreakTimeChange: (Int) -> Unit
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
                        TimePicker("Pomodoro", pomodoroTime, onPomodoroTimeChange)
                        TimePicker("Short Break", shortBreakTime, onShortBreakTimeChange)
                        TimePicker("Long Break", longBreakTime, onLongBreakTimeChange)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    println("Confirm")
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