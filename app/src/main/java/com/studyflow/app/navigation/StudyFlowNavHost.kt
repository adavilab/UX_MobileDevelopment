package com.studyflow.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studyflow.app.ui.components.BottomNavItem
import com.studyflow.app.ui.screens.login.LoginScreen
import com.studyflow.app.ui.screens.session.AlarmScreen
import com.studyflow.app.ui.screens.session.AlarmVariant
import com.studyflow.app.ui.screens.session.BreakScreen
import com.studyflow.app.ui.screens.session.EvidenceScreen
import com.studyflow.app.ui.screens.session.StudySessionScreen
import com.studyflow.app.ui.screens.session.defaultSessionTask
import com.studyflow.app.ui.screens.session.findTaskById
import com.studyflow.app.ui.screens.settings.SessionPreferences
import com.studyflow.app.ui.screens.settings.SettingsScreen
import com.studyflow.app.ui.screens.tasks.TasksScreen
import com.studyflow.app.ui.screens.today.TodayScreen

object StudyFlowDestinations {
    const val LOGIN = "login"
    const val TODAY = "today"
    const val TASKS = "tasks"
    const val SETTINGS = "settings"
    const val STUDY = "study"
    const val EVIDENCE = "evidence"
    const val BREAK = "break"
    const val ALARM = "alarm/{variant}"

    fun alarm(variant: AlarmVariant) = "alarm/${variant.route}"
}

@Composable
fun StudyFlowNavHost(
    navController: NavHostController = rememberNavController()
) {
    // Tarea de la sesión en curso; solo vive en memoria (maqueta sin backend).
    var activeTaskId by rememberSaveable { mutableStateOf(defaultSessionTask.id) }
    val activeTask = findTaskById(activeTaskId)
    var preferences by rememberSaveable { mutableStateOf(SessionPreferences()) }

    val onBottomNavSelected: (BottomNavItem) -> Unit = { item ->
        val route = when (item) {
            BottomNavItem.Today -> StudyFlowDestinations.TODAY
            BottomNavItem.StartSession -> StudyFlowDestinations.TASKS
            BottomNavItem.Settings -> StudyFlowDestinations.SETTINGS
        }
        navController.navigate(route) {
            popUpTo(StudyFlowDestinations.TODAY)
            launchSingleTop = true
        }
    }

    val goToLogin: () -> Unit = {
        navController.navigate(StudyFlowDestinations.LOGIN) {
            popUpTo(0) { inclusive = true }
        }
    }

    val goToToday: () -> Unit = {
        navController.navigate(StudyFlowDestinations.TODAY) {
            popUpTo(StudyFlowDestinations.TODAY) { inclusive = true }
        }
    }

    // Los pasos de la sesión reemplazan al anterior para que "atrás" vuelva a Tareas.
    fun navigateSessionStep(route: String) {
        navController.navigate(route) {
            popUpTo(StudyFlowDestinations.TASKS)
        }
    }

    NavHost(
        navController = navController,
        startDestination = StudyFlowDestinations.LOGIN
    ) {
        composable(StudyFlowDestinations.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(StudyFlowDestinations.TODAY) {
                        popUpTo(StudyFlowDestinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(StudyFlowDestinations.TODAY) {
            TodayScreen(
                onBottomNavSelected = onBottomNavSelected
            )
        }
        composable(StudyFlowDestinations.TASKS) {
            TasksScreen(
                onStartTask = { task ->
                    activeTaskId = task.id
                    navController.navigate(StudyFlowDestinations.STUDY)
                },
                onBottomNavSelected = onBottomNavSelected
            )
        }
        composable(StudyFlowDestinations.SETTINGS) {
            SettingsScreen(
                preferences = preferences,
                onPreferencesChange = { preferences = it },
                onLogoutClick = goToLogin,
                onBottomNavSelected = onBottomNavSelected
            )
        }
        composable(StudyFlowDestinations.STUDY) {
            StudySessionScreen(
                task = activeTask,
                pomodoroMinutes = preferences.pomodoroMinutes,
                onCompleteEarly = {
                    navigateSessionStep(StudyFlowDestinations.alarm(AlarmVariant.AfterPomodoro))
                }
            )
        }
        composable(StudyFlowDestinations.ALARM) { backStackEntry ->
            AlarmScreen(
                variant = AlarmVariant.fromRoute(backStackEntry.arguments?.getString("variant")),
                task = activeTask,
                soundEnabled = preferences.alarmSoundEnabled,
                vibrationEnabled = preferences.vibrationEnabled,
                onTakeEvidence = { navigateSessionStep(StudyFlowDestinations.EVIDENCE) },
                onStartBreak = { navigateSessionStep(StudyFlowDestinations.BREAK) },
                onStartWork = { navigateSessionStep(StudyFlowDestinations.STUDY) },
                onEndSession = goToToday
            )
        }
        composable(StudyFlowDestinations.EVIDENCE) {
            EvidenceScreen(
                onSaveAndContinue = {
                    navigateSessionStep(StudyFlowDestinations.alarm(AlarmVariant.AfterPhoto))
                }
            )
        }
        composable(StudyFlowDestinations.BREAK) {
            BreakScreen(
                onSkipBreak = {
                    navigateSessionStep(StudyFlowDestinations.alarm(AlarmVariant.AfterBreak))
                }
            )
        }
    }
}
