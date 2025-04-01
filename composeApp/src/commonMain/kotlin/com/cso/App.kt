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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cso.ui.components.Menu
import com.cso.ui.menu.SettingsPopup
import com.cso.ui.menu.SettingsViewModel
import com.cso.ui.pomodoro.PomodoroTimerScreen
import com.cso.ui.pomodoro.PomodoroViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    prefs: DataStore<Preferences>
) {
    MaterialTheme {
        val model = remember { PomodoroViewModel(prefs) }
        val state = model.state

        val settingsModel = remember { SettingsViewModel(prefs) }
        val settingsState = settingsModel.state

        var menuExpanded by remember { mutableStateOf(false) }
        var showDialog by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Pomodoro") },
                    actions = {
                        Box {
                            IconButton(onClick = { menuExpanded = true }) {
                                Icon(Icons.Filled.MoreVert, contentDescription = "More Options")
                            }
                            Menu(
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
                SettingsPopup(
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    state = settingsState,
                    onPomodoroTimeChange = { settingsModel.onPomodoroTimeChange(it) },
                    onShortBreakTimeChange = { settingsModel.onShortBreakTimeChange(it) },
                    onLongBreakTimeChange = { settingsModel.onLongBreakTimeChange(it) },
                    onConfirm = { settingsModel.onConfirm() }
                )
            }
        }
    }
}
