package com.cso

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() {
    val prefs = createDataStore {
        DATA_STORE_FILE_NAME
    }
    application {
        val state = rememberWindowState(
            size = DpSize(650.dp, 480.dp),
            position = WindowPosition(300.dp, 300.dp)
        )
        Window(
            onCloseRequest = ::exitApplication,
            title = "Pomodoro-KMP",
            state = state
        ) {
            App(
                prefs = prefs,
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false,
            )
        }
    }
}