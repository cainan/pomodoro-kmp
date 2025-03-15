package com.cso

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cso.ui.PomodoroTimerScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Pomodoro") },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "More Options")
                        }
                    },
                    backgroundColor = Color(0xFFE57373)
                )
            },
            backgroundColor = Color(0xFFE57373)
        ) { paddingValues ->
            PomodoroTimerScreen(modifier = Modifier.padding(paddingValues))
        }
    }
}