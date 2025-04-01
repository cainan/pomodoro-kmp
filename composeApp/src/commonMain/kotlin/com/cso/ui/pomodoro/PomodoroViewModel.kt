package com.cso.ui.pomodoro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cso.ui.pomodoro.PomodoroState.PomodoroItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PomodoroViewModel(val prefs: DataStore<Preferences>) : ViewModel() {
    var state: PomodoroState by mutableStateOf(initialState())
        private set

    init {

        viewModelScope.launch {
            prefs.data.collect {

                val pomodoroPreference = intPreferencesKey("pomodoroTime")
                it[pomodoroPreference]?.let {
                    setState { copy(pomodoroDuration = it) }
                }

                val shortBreakPreference = intPreferencesKey("shortBreakDuration")
                it[shortBreakPreference]?.let {
                    setState { copy(shortBreakDuration = it) }
                }

                val longBreakPreference = intPreferencesKey("longBreakDuration")
                it[longBreakPreference]?.let {
                    setState { copy(longBreakDuration = it) }
                }
            }
        }

    }

    fun onPomodoroItemClicked(item: PomodoroItems) {
        setState { copy(selectedItem = item) }
    }

    fun onStartClicked() {

        val countdown = when (state.selectedItem) {
            PomodoroItems.POMODORO -> state.pomodoroDuration
            PomodoroItems.SHORT_BREAK -> state.shortBreakDuration
            PomodoroItems.LONG_BREAK -> state.longBreakDuration
        }

        setState { copy(isTimerRunning = true, timeLeft = countdown.toMillis()) }

        viewModelScope.launch {
            coroutineScope {
                startTimer(
                    total = countdown.toMillis(),
                    task = {
                        println(it)
                        setState { copy(timeLeft = it) }
                    },
                    onFinish = {
                        println("finished")
                        setState { copy(isTimerRunning = false) }
                        TODO()
                    }
                )
            }
        }
    }

    private fun CoroutineScope.startTimer(
        total: Long,
        delay: Long = 1000L,
        task: suspend (currentMillis: Long) -> Unit,
        onFinish: () -> Unit
    ): Job {
        return launch {
            for (i in total - 1000L downTo 0 step 1000) {
                delay(delay)
                task(i)
            }
            onFinish()
        }
    }

    private inline fun setState(update: PomodoroState.() -> PomodoroState) {
        state = state.update()
    }

    private fun initialState(): PomodoroState =
        PomodoroState()


}

fun Int.toMillis(): Long {
    return (this * 60) * 1000L
}

fun String.formatToTwoDigits(): String {
    return if (this.length == 1) {
        "0$this"
    } else {
        this
    }
}

fun Long.millisToText(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds.div(60).toString().formatToTwoDigits()
    val seconds = totalSeconds.rem(60).toString().formatToTwoDigits()
    return "${minutes}:${seconds}"
}

fun Int.toFormattedText(): String {
    val minutes = this.toString().formatToTwoDigits()
    return "${minutes}:00"
}
