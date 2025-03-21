package com.cso.ui

data class PomodoroState(
    val pomodoroDuration: String = "25:00",
    val shortBreakDuration: String = "05:00",
    val longBreakDuration: String = "15:00",
    val selectedItem: PomodoroItems = PomodoroItems.POMODORO,
    val isTimerRunning: Boolean = false,
    val timeLeft: String = "00:00"
) {

    enum class PomodoroItems {
        POMODORO, SHORT_BREAK, LONG_BREAK
    }

}