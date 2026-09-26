package com.studyflow.app.ui.screens.session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyflow.app.ui.components.CountdownRing
import com.studyflow.app.ui.components.rememberCountdown
import com.studyflow.app.ui.theme.ActionRed
import com.studyflow.app.ui.theme.BackgroundOffWhite
import com.studyflow.app.ui.theme.PurplePrimary
import com.studyflow.app.ui.theme.TextPrimary
import com.studyflow.app.ui.theme.TextSecondary
import com.studyflow.app.ui.theme.TrackGrey

@Composable
fun StudySessionScreen(
    task: StudyTask,
    pomodoroMinutes: Int,
    onCompleteEarly: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalSeconds = pomodoroMinutes * 60
    var isPaused by rememberSaveable { mutableStateOf(false) }
    // Al llegar a cero pasa a la alarma, igual que "Completar antes de tiempo".
    val remainingSeconds = rememberCountdown(
        initialSeconds = totalSeconds,
        isRunning = !isPaused,
        onFinished = onCompleteEarly
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundOffWhite)
            .systemBarsPadding()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Trabajando en:", color = TextSecondary, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = task.title,
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(60.dp))

        CountdownRing(
            remainingSeconds = remainingSeconds,
            totalSeconds = totalSeconds,
            color = ActionRed,
            size = 220.dp,
            timeFontSize = 36.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Progreso total de la tarea (Pomodoro ${task.currentPomodoro} de ${task.scheduledPomodoros})",
            color = TextSecondary,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = { task.currentPomodoro / task.scheduledPomodoros.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = ActionRed,
            trackColor = TrackGrey,
            strokeCap = StrokeCap.Round,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )

        Spacer(modifier = Modifier.height(56.dp))

        // Componente con dos estados en Figma (Pausar / Play).
        Button(
            onClick = { isPaused = !isPaused },
            modifier = Modifier
                .width(175.dp)
                .height(52.dp),
            shape = RoundedCornerShape(26.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
        ) {
            Text(
                text = if (isPaused) "Play" else "Pausar",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Completar antes de tiempo",
            color = TextSecondary,
            fontSize = 13.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .clickable(onClick = onCompleteEarly)
                .padding(horizontal = 8.dp, vertical = 6.dp)
        )
    }
}
