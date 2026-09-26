package com.studyflow.app.ui.screens.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyflow.app.ui.components.CountdownRing
import com.studyflow.app.ui.components.PillButton
import com.studyflow.app.ui.components.rememberCountdown
import com.studyflow.app.ui.theme.ActionBrown
import com.studyflow.app.ui.theme.ActionGreen
import com.studyflow.app.ui.theme.BackgroundOffWhite
import com.studyflow.app.ui.theme.TextPrimary
import com.studyflow.app.ui.theme.TextSecondary

@Composable
fun BreakScreen(
    onSkipBreak: () -> Unit,
    modifier: Modifier = Modifier
) {
    val remainingSeconds = rememberCountdown(
        initialSeconds = BREAK_TOTAL_SECONDS,
        isRunning = true,
        onFinished = onSkipBreak
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundOffWhite)
            .systemBarsPadding()
            .padding(horizontal = 40.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Descanso",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Estírate y respira profundo antes de tu próximo bloque",
            color = TextSecondary,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(56.dp))

        CountdownRing(
            remainingSeconds = remainingSeconds,
            totalSeconds = BREAK_TOTAL_SECONDS,
            color = ActionGreen,
            size = 180.dp,
            timeFontSize = 30.sp,
            caption = "restante"
        )

        Spacer(modifier = Modifier.height(80.dp))

        PillButton(
            text = "Saltar Descanso",
            onClick = onSkipBreak,
            containerColor = ActionBrown,
            modifier = Modifier.width(200.dp)
        )
    }
}
