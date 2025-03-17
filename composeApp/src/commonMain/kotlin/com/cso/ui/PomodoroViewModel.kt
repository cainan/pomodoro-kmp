package com.cso.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cso.ui.PomodoroState.PomodoroItems

class PomodoroViewModel {
    var state: PomodoroState by mutableStateOf(initialState())
        private set

    fun onPomodoroItemClicked(item: PomodoroItems) {
        setState { copy(selectedItem = item) }
    }

    fun onStartPomodoroClicked() {}

    private inline fun setState(update: PomodoroState.() -> PomodoroState) {
        state = state.update()
    }

    private fun initialState(): PomodoroState =
        PomodoroState()
}
