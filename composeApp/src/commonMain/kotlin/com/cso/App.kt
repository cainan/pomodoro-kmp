package com.cso

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cso.ui.PomodoroTimerScreen
import com.cso.ui.PomodoroViewModel
import com.cso.ui.components.MinimalDropdownMenu
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        val model = remember { PomodoroViewModel() }
        val state = model.state
        var menuExpanded by remember { mutableStateOf(false) }


        var showDialog by remember { mutableStateOf(false) }
        var pomodoroTime by remember { mutableStateOf(25) }
        var shortBreakTime by remember { mutableStateOf(5) }
        var longBreakTime by remember { mutableStateOf(15) }



        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Pomodoro") },
                    actions = {
                        Box {
                            IconButton(onClick = { menuExpanded = true }) {
                                Icon(Icons.Filled.MoreVert, contentDescription = "More Options")
                            }
                            MinimalDropdownMenu(
                                menuExpanded,
                                onSettingsClicked = {
                                    showDialog = true
                                    menuExpanded = false
                                },
                                onDismiss = { menuExpanded = false })
                        }
                    },
                    backgroundColor = Color(0xFFE57373)
                )
            },
            backgroundColor = Color(0xFFE57373)
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize(1F)) {
                PomodoroTimerScreen(
                    modifier = Modifier.padding(paddingValues),
                    state = state,
                    onPomodoroItemClicked = model::onPomodoroItemClicked,
                    onStartClicked = model::onStartClicked
                )
                TimerSettingsPopup(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    pomodoroTime = pomodoroTime,
                    shortBreakTime = shortBreakTime,
                    longBreakTime = longBreakTime,
                    onPomodoroTimeChange = { pomodoroTime = it },
                    onShortBreakTimeChange = { shortBreakTime = it },
                    onLongBreakTimeChange = { longBreakTime = it }
                )
            }
        }
    }
}


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

@Composable
fun TimePicker(label: String, time: Int, onTimeChange: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onTimeChange(time - 1) }) {
                Icon(Icons.Default.Delete, contentDescription = "Decrease")
            }
            Text(time.toString())
            IconButton(onClick = { onTimeChange(time + 1) }) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
        }
    }
}