package com.studyflow.app.ui.components

import android.os.SystemClock
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.studyflow.app.ui.theme.TextPrimary
import com.studyflow.app.ui.theme.TextSecondary
import com.studyflow.app.ui.theme.TrackGrey
import kotlinx.coroutines.delay

/**
 * Cuenta regresiva en memoria: descuenta mientras [isRunning] sea true y avisa con
 * [onFinished] al llegar a cero. Se mide contra el reloj del sistema para no acumular
 * desfase entre ticks.
 */
@Composable
fun rememberCountdown(
    initialSeconds: Int,
    isRunning: Boolean,
    onFinished: () -> Unit = {}
): Int {
    var remaining by rememberSaveable { mutableIntStateOf(initialSeconds) }
    val currentOnFinished by rememberUpdatedState(onFinished)

    LaunchedEffect(isRunning) {
        if (!isRunning || remaining <= 0) return@LaunchedEffect
        val startedAt = SystemClock.elapsedRealtime()
        val remainingAtStart = remaining
        while (remaining > 0) {
            delay(200L)
            val elapsedSeconds = ((SystemClock.elapsedRealtime() - startedAt) / 1_000L).toInt()
            remaining = (remainingAtStart - elapsedSeconds).coerceAtLeast(0)
        }
        currentOnFinished()
    }
    return remaining
}

@Composable
fun CountdownRing(
    remainingSeconds: Int,
    totalSeconds: Int,
    color: Color,
    size: Dp,
    timeFontSize: TextUnit,
    modifier: Modifier = Modifier,
    caption: String? = null
) {
    val targetFraction = if (totalSeconds == 0) 0f else remainingSeconds / totalSeconds.toFloat()
    // El arco de color se va vaciando en sentido horario conforme pasa el tiempo.
    val fraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(durationMillis = 1_000, easing = LinearEasing),
        label = "countdownFraction"
    )

    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            val inset = stroke.width / 2
            val arcSize = Size(
                this.size.width - stroke.width,
                this.size.height - stroke.width
            )
            val topLeft = Offset(inset, inset)
            drawArc(TrackGrey, 0f, 360f, false, topLeft, arcSize, style = stroke)
            drawArc(color, -90f, 360f * fraction, false, topLeft, arcSize, style = stroke)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatSeconds(remainingSeconds),
                color = TextPrimary,
                fontSize = timeFontSize,
                fontWeight = FontWeight.Bold
            )
            if (caption != null) {
                Text(text = caption, color = TextSecondary)
            }
        }
    }
}

private fun formatSeconds(totalSeconds: Int): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}
