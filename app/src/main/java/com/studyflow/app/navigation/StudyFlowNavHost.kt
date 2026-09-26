package com.studyflow.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.studyflow.app.ui.screens.login.LoginScreen
import com.studyflow.app.ui.screens.today.TodayScreen

object StudyFlowDestinations {
    const val LOGIN = "login"
    const val TODAY = "today"
}

@Composable
fun StudyFlowNavHost(
    navController: NavHostController = rememberNavController()
) {
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
                onLogoutClick = {
                    navController.navigate(StudyFlowDestinations.LOGIN) {
                        popUpTo(StudyFlowDestinations.TODAY) { inclusive = true }
                    }
                }
            )
        }
    }
}
