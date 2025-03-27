package com.cso.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cso.ui.PomodoroState.PomodoroItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class PomodoroViewModel(val prefs: DataStore<Preferences>?) : ViewModel() {
    var state: PomodoroState by mutableStateOf(initialState())
        private set

    init {
        viewModelScope.launch {
            val pomodoroDurationLoaded = prefs?.data?.map {
                val key = stringPreferencesKey("pomodoroTime")
                it[key] ?: ""
            }?.collect {
                println("-----> $it")
                if (it.isNotEmpty()) {
                    setState { copy(pomodoroDuration = it) }
                }
            }

            val shortBreakDurationLoaded = prefs?.data?.map {
                val key = stringPreferencesKey("shortBreakDuration")
                it[key] ?: ""
            }?.collect {
                println("-----> $it")
                if (it.isNotEmpty()) {
                    setState { copy(pomodoroDuration = it) }
                }
            }

            val longBreakDurationLoaded = prefs?.data?.map {
                val key = stringPreferencesKey("longBreakDuration")
                it[key] ?: ""
            }?.collect {
                println("-----> $it")
                if (it.isNotEmpty()) {
                    setState { copy(pomodoroDuration = it) }
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

        setState { copy(isTimerRunning = true, timeLeft = countdown) }

        viewModelScope.launch {
            coroutineScope {
                startTimer(
                    total = countdown.textToMillis(),
                    task = {
                        println(it.millisToText())
                        setState { copy(timeLeft = it.millisToText()) }
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


    fun dataStore() {
//        val scope = rememberCoroutineScope()

        // save
//        viewModelScope.launch {
//            prefs.edit { dataStore ->
//                val key = stringPreferencesKey("pomodoro")
//                dataStore[key] = "xxxxxxx"
//            }
//        }


        // load
//        val loadedFromDataStore by prefs.data.map {
//            val key = stringPreferencesKey("pomodoro")
//            it[key] ?: ""
//        }.collectAsState(initial = "")
//        println("-----> $loadedFromDataStore")
    }

}

fun String.textToMillis(): Long {
    val split = this.split(":")
    return (split[0].toInt() * 60 + split[1].toInt()) * 1000L
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
