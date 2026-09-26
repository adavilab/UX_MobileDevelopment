package com.studyflow.app.ui.components

import android.content.Context
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

private val VIBRATION_PATTERN = longArrayOf(0, 600, 400)

/**
 * Hace sonar el tono de alarma del dispositivo (y vibra, si está activo en Ajustes)
 * mientras la pantalla que lo llama esté visible.
 */
@Composable
fun AlarmSignal(soundEnabled: Boolean, vibrationEnabled: Boolean) {
    val context = LocalContext.current

    DisposableEffect(soundEnabled, vibrationEnabled) {
        val ringtone = if (soundEnabled) startAlarmSound(context) else null
        val vibrator = if (vibrationEnabled) startVibration(context) else null

        onDispose {
            ringtone?.stop()
            vibrator?.cancel()
        }
    }
}

private fun startAlarmSound(context: Context): Ringtone? {
    val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        ?: return null

    return RingtoneManager.getRingtone(context, uri)?.apply {
        audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            isLooping = true
        }
        play()
    }
}

private fun startVibration(context: Context): Vibrator? {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        context.getSystemService(VibratorManager::class.java)?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Vibrator::class.java)
    }
    if (vibrator?.hasVibrator() != true) return null

    vibrator.vibrate(VibrationEffect.createWaveform(VIBRATION_PATTERN, 0))
    return vibrator
}
