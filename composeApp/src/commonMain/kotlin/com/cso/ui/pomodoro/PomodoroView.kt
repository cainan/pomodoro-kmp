package com.cso.ui.pomodoro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cso.ui.pomodoro.PomodoroState.PomodoroItems
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PomodoroTimerScreen(
    modifier: Modifier = Modifier,
    state: PomodoroState,
    onPomodoroItemClicked: (PomodoroItems) -> Unit = {},
    onStartClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .shadow(4.dp, RoundedCornerShape(8.dp)),
            color = Color.White,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FilledTonalButton(
                        contentPadding = PaddingValues(horizontal = 18.dp),
                        onClick = {
                            onPomodoroItemClicked(PomodoroItems.POMODORO)
                        }) {
                        Text(
                            text = "Pomodoro",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    FilledTonalButton(
                        contentPadding = PaddingValues(horizontal = 18.dp),
                        onClick = {
                            onPomodoroItemClicked(PomodoroItems.SHORT_BREAK)
                        }) {
                        Text(
                            text = "Short Break",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    FilledTonalButton(
                        contentPadding = PaddingValues(horizontal = 18.dp),
                        onClick = {
                            onPomodoroItemClicked(PomodoroItems.LONG_BREAK)
                        }) {
                        Text(
                            text = "Long Break",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                val displayedText = when (state.isTimerRunning) {
                    true -> state.timeLeft.millisToText()
                    false -> when (state.selectedItem) {
                        PomodoroItems.POMODORO -> state.pomodoroDuration.toFormattedText()
                        PomodoroItems.SHORT_BREAK -> state.shortBreakDuration.toFormattedText()
                        PomodoroItems.LONG_BREAK -> state.longBreakDuration.toFormattedText()
                    }
                }

                Text(
                    text = displayedText,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { onStartClicked() },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(text = "START")
                }
            }
        }
    }
}

@Preview
@Composable
fun PomodoroTimerScreenPreview() {
    val state = PomodoroState()
    PomodoroTimerScreen(state = state)
}