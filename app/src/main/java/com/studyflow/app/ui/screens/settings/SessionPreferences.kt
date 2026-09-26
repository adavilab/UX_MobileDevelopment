package com.studyflow.app.ui.screens.settings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Preferencias de Ajustes; viven solo en memoria mientras la app está abierta. */
@Parcelize
data class SessionPreferences(
    val notificationsEnabled: Boolean = true,
    val alarmSoundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = false,
    val pomodoroMinutes: Int = 25
) : Parcelable
