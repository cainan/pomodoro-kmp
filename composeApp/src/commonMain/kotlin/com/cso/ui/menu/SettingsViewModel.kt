package com.cso.ui.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(val prefs: DataStore<Preferences>) : ViewModel() {
    var state: SettingsState by mutableStateOf(SettingsState())
        private set

    private inline fun setState(update: SettingsState.() -> SettingsState) {
        state = state.update()
    }

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

    fun onPomodoroTimeChange(value: Int) {
        setState { copy(pomodoroDuration = value) }
    }

    fun onShortBreakTimeChange(value: Int) {
        setState { copy(shortBreakDuration = value) }
    }

    fun onLongBreakTimeChange(value: Int) {
        setState { copy(longBreakDuration = value) }
    }

    fun onConfirm() {
        viewModelScope.launch {
            savePreference(
                key = "pomodoroTime",

                value = state.pomodoroDuration,
                prefs = prefs
            )

            savePreference(
                key = "shortBreakDuration",
                value = state.shortBreakDuration,
                prefs = prefs
            )

            savePreference(
                key = "longBreakDuration",
                value = state.longBreakDuration,
                prefs = prefs
            )
        }
    }

}

suspend fun savePreference(key: String, value: Int, prefs: DataStore<Preferences>?) {
    prefs?.edit { dataStore ->
        val keyPref = intPreferencesKey(key)
        dataStore[keyPref] = value
    }
}