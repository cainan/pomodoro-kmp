package com.cso

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cso.ui.components.Menu
import com.cso.ui.menu.SettingsPopup
import com.cso.ui.menu.SettingsViewModel
import com.cso.ui.pomodoro.PomodoroTimerScreen
import com.cso.ui.pomodoro.PomodoroViewModel
import com.cso.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    prefs: DataStore<Preferences> = createDataStore { "" },
    darkTheme: Boolean,
    dynamicColor: Boolean,
) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
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
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues).fillMaxSize(1F),
                verticalArrangement = Arrangement.Center,
            ) {
                Box {
                    PomodoroTimerScreen(
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
}
