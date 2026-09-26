package com.studyflow.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val StudyFlowColorScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = SurfaceWhite,
    primaryContainer = PurplePrimaryVariant,
    onPrimaryContainer = SurfaceWhite,
    background = BackgroundOffWhite,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundOffWhite,
    onSurfaceVariant = TextSecondary,
    outline = DividerColor
)

@Composable
fun StudyFlowTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = StudyFlowColorScheme,
        typography = StudyFlowTypography,
        shapes = StudyFlowShapes,
        content = content
    )
}
