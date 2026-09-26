package com.studyflow.app.ui.screens.today

import com.studyflow.app.ui.components.TimelineEvent
import com.studyflow.app.ui.theme.EventBlue
import com.studyflow.app.ui.theme.EventCoral
import com.studyflow.app.ui.theme.EventGreen
import com.studyflow.app.ui.theme.EventMustard

const val TODAY_WEEKDAY = "Miércoles"
const val TODAY_DATE = "20 de Agosto"
const val COMPLETED_POMODOROS = 2
const val TOTAL_POMODOROS = 5

val mockTodayEvents = listOf(
    TimelineEvent(
        id = "gimnasio",
        title = "Gimnasio",
        startHour = 8f,
        endHour = 9f,
        color = EventBlue
    ),
    TimelineEvent(
        id = "calculo-vectorial",
        title = "Cálculo Vectorial",
        startHour = 10f,
        endHour = 11f,
        color = EventGreen
    ),
    TimelineEvent(
        id = "estructuras-datos",
        title = "Estructuras de datos",
        startHour = 13f,
        endHour = 14f,
        color = EventMustard
    ),
    TimelineEvent(
        id = "pomodoro",
        title = "Pomodoro?",
        startHour = 16f,
        endHour = 17f,
        color = EventCoral
    )
)
