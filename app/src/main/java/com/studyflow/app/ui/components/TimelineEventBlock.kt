package com.studyflow.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TimelineEvent(
    val id: String,
    val title: String,
    val startHour: Float,
    val endHour: Float,
    val color: Color
)

@Composable
fun TimelineEventBlock(
    event: TimelineEvent,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(event.color)
            .then(
                if (isSelected) {
                    Modifier.border(2.dp, Color.White, RoundedCornerShape(10.dp))
                } else {
                    Modifier
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = event.title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                maxLines = 1
            )
            Text(
                text = formatEventTimeRange(event.startHour, event.endHour),
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 11.sp
            )
        }
    }
}

private fun formatEventTimeRange(startHour: Float, endHour: Float): String {
    return "${formatClockHour(startHour)} - ${formatClockHour(endHour)}"
}

private fun formatClockHour(hour: Float): String {
    val wholeHour = hour.toInt()
    val minutes = ((hour - wholeHour) * 60).toInt()
    val period = if (wholeHour < 12) "AM" else "PM"
    val displayHour = when {
        wholeHour == 0 -> 12
        wholeHour > 12 -> wholeHour - 12
        else -> wholeHour
    }
    return if (minutes == 0) "$displayHour $period" else "$displayHour:${minutes.toString().padStart(2, '0')} $period"
}
