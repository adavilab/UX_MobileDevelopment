package com.studyflow.app.ui.screens.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyflow.app.ui.components.AlarmSignal
import com.studyflow.app.ui.components.PillButton
import com.studyflow.app.ui.theme.ActionBrown
import com.studyflow.app.ui.theme.ActionGreen
import com.studyflow.app.ui.theme.ActionRed
import com.studyflow.app.ui.theme.AlarmPurple
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Las tres variantes de alarma del wireframe (pantallas 4). */
enum class AlarmVariant(val route: String) {
    AfterPomodoro("after-pomodoro"),
    AfterPhoto("after-photo"),
    AfterBreak("after-break");

    companion object {
        fun fromRoute(route: String?): AlarmVariant =
            entries.firstOrNull { it.route == route } ?: AfterPomodoro
    }
}

@Composable
fun AlarmScreen(
    variant: AlarmVariant,
    task: StudyTask,
    soundEnabled: Boolean,
    vibrationEnabled: Boolean,
    onTakeEvidence: () -> Unit,
    onStartBreak: () -> Unit,
    onStartWork: () -> Unit,
    onEndSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlarmSignal(soundEnabled = soundEnabled, vibrationEnabled = vibrationEnabled)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AlarmPurple)
            .systemBarsPadding()
            .padding(horizontal = 40.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (variant == AlarmVariant.AfterPomodoro) {
            Spacer(modifier = Modifier.height(60.dp))
            PillButton(
                text = "Tomar evidencia",
                onClick = onTakeEvidence,
                containerColor = Color.White,
                contentColor = AlarmPurple
            )
            Spacer(modifier = Modifier.height(100.dp))
        } else {
            Spacer(modifier = Modifier.height(60.dp))
        }

        AlarmHeader(task = task)

        Spacer(modifier = Modifier.weight(1f))

        when (variant) {
            AlarmVariant.AfterPomodoro -> Unit
            AlarmVariant.AfterPhoto -> {
                PillButton(text = "Empezar descanso", onClick = onStartBreak, containerColor = ActionGreen)
                Spacer(modifier = Modifier.height(20.dp))
                PillButton(text = "Terminar sesión", onClick = onEndSession, containerColor = ActionBrown)
            }
            AlarmVariant.AfterBreak -> {
                PillButton(text = "Empezar trabajo", onClick = onStartWork, containerColor = ActionRed)
                Spacer(modifier = Modifier.height(20.dp))
                PillButton(text = "Terminar sesión", onClick = onEndSession, containerColor = ActionBrown)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Posponer no disponible",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
private fun AlarmHeader(task: StudyTask) {
    // Hora en la que "sonó" la alarma, es decir, cuando se abrió la pantalla.
    val alarmTime = remember {
        LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a", Locale.US))
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "ALARMA", color = Color.White, fontSize = 14.sp)
        Text(
            text = alarmTime,
            color = Color.White,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = task.title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = task.description,
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${task.scheduledPomodoros} pomodoros programados",
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 13.sp
        )
    }
}
