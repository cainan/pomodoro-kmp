package com.cso.ui.pomodoro

data class PomodoroState(
    val pomodoroDuration: Int = 25,
    val shortBreakDuration: Int = 5,
    val longBreakDuration: Int = 15,
    val selectedItem: PomodoroItems = PomodoroItems.POMODORO,
    val isTimerRunning: Boolean = false,
    val timeLeft: Long = 0
) {
    enum class PomodoroItems {
        POMODORO, SHORT_BREAK, LONG_BREAK
    }
}