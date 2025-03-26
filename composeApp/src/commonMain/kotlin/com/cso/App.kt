package com.cso

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cso.ui.PomodoroTimerScreen
import com.cso.ui.PomodoroViewModel
import com.cso.ui.components.MinimalDropdownMenu
import com.cso.ui.components.TimerSettingsPopup
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
