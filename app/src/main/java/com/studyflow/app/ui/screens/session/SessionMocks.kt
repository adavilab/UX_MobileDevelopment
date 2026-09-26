package com.studyflow.app.ui.screens.session

data class StudyTask(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: String,
    val progressPercent: Int,
    val scheduledPomodoros: Int,
    val currentPomodoro: Int
)

// Ordenadas de más urgente a menos urgente, tal como en el wireframe.
val mockSuggestedTasks = listOf(
    StudyTask(
        id = "historia-arte",
        title = "Historia del arte",
        description = "Leer el capítulo 4 de Gombrich",
        dueDate = "28/08",
        progressPercent = 15,
        scheduledPomodoros = 2,
        currentPomodoro = 1
    ),
    StudyTask(
        id = "estructuras-datos",
        title = "Estructuras de datos",
        description = "Realizar taller de listas enlazadas",
        dueDate = "26/08",
        progressPercent = 30,
        scheduledPomodoros = 3,
        currentPomodoro = 2
    ),
    StudyTask(
        id = "calculo-vectorial",
        title = "Cálculo Vectorial",
        description = "Resolver taller de integrales dobles",
        dueDate = "23/08",
        progressPercent = 60,
        scheduledPomodoros = 4,
        currentPomodoro = 3
    )
)

val defaultSessionTask = mockSuggestedTasks[1]

fun findTaskById(id: String): StudyTask =
    mockSuggestedTasks.firstOrNull { it.id == id } ?: defaultSessionTask

const val BREAK_TOTAL_SECONDS = 5 * 60
