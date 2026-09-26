package com.studyflow.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyflow.app.ui.theme.DividerColor
import com.studyflow.app.ui.theme.PurplePrimary
import com.studyflow.app.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import java.time.LocalTime

private const val START_HOUR = 7
private const val END_HOUR = 21
private val HOUR_ROW_HEIGHT = 64.dp
private val HOUR_LABEL_WIDTH = 56.dp

@Composable
fun DailyTimeline(
    events: List<TimelineEvent>,
    selectedEventId: String?,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val hours = (START_HOUR..END_HOUR).toList()
    var currentHour by remember { mutableStateOf(currentHourOfDay()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000L)
            currentHour = currentHourOfDay()
        }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            hours.forEach { hour ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(HOUR_ROW_HEIGHT)
                ) {
                    Text(
                        text = formatHourLabel(hour),
                        modifier = Modifier
                            .width(HOUR_LABEL_WIDTH)
                            .padding(top = 2.dp, end = 8.dp),
                        color = TextSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.End
                    )
                    Box(modifier = Modifier.fillMaxWidth()) {
                        HorizontalDivider(
                            modifier = Modifier.align(Alignment.TopStart),
                            thickness = 1.dp,
                            color = DividerColor
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = HOUR_LABEL_WIDTH + 12.dp, end = 16.dp)
        ) {
            events.forEach { event ->
                val topOffset = ((event.startHour - START_HOUR) * HOUR_ROW_HEIGHT.value).dp
                val blockHeight = ((event.endHour - event.startHour) * HOUR_ROW_HEIGHT.value).dp

                TimelineEventBlock(
                    event = event,
                    isSelected = event.id == selectedEventId,
                    onClick = { onEventClick(event.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = topOffset)
                        .height(blockHeight - 6.dp)
                )
            }
        }

        if (currentHour in START_HOUR.toFloat()..END_HOUR.toFloat()) {
            CurrentTimeIndicator(
                topOffset = ((currentHour - START_HOUR) * HOUR_ROW_HEIGHT.value).dp
            )
        }
    }
}

private fun currentHourOfDay(): Float {
    val now = LocalTime.now()
    return now.hour + now.minute / 60f
}

@Composable
private fun CurrentTimeIndicator(topOffset: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = topOffset - 1.dp)
            .height(2.dp)
            .padding(start = HOUR_LABEL_WIDTH - 4.dp)
            .background(PurplePrimary)
    )
    Box(
        modifier = Modifier
            .offset(x = HOUR_LABEL_WIDTH - 9.dp, y = topOffset - 5.dp)
            .size(10.dp)
            .clip(CircleShape)
            .background(PurplePrimary)
    )
}

private fun formatHourLabel(hour: Int): String {
    val period = if (hour < 12) "am" else "pm"
    val displayHour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    return "$displayHour$period"
}
