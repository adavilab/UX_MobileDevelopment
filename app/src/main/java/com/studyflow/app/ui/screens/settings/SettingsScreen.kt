package com.studyflow.app.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyflow.app.ui.components.BottomNavBar
import com.studyflow.app.ui.components.BottomNavItem
import com.studyflow.app.ui.theme.BackgroundOffWhite
import com.studyflow.app.ui.theme.DangerRed
import com.studyflow.app.ui.theme.DividerColor
import com.studyflow.app.ui.theme.PurplePrimary
import com.studyflow.app.ui.theme.SurfaceWhite
import com.studyflow.app.ui.theme.TextPrimary
import com.studyflow.app.ui.theme.TextSecondary

private const val MOCK_USER_NAME = "Valentina Rueda"
private const val MOCK_USER_EMAIL = "valentina.rueda@uniandes.edu.co"
// Incluye 1 min para poder probar rápido el fin del temporizador.
private val pomodoroDurationOptions = listOf(1, 15, 25, 45, 50)

@Composable
fun SettingsScreen(
    preferences: SessionPreferences,
    onPreferencesChange: (SessionPreferences) -> Unit,
    onLogoutClick: () -> Unit,
    onBottomNavSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = BackgroundOffWhite,
        bottomBar = {
            BottomNavBar(
                selectedItem = BottomNavItem.Settings,
                onItemSelected = onBottomNavSelected
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Text(
                text = "Ajustes",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(PurplePrimary)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(69.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = MOCK_USER_NAME,
                modifier = Modifier.fillMaxWidth(),
                color = TextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = MOCK_USER_EMAIL,
                modifier = Modifier.fillMaxWidth(),
                color = TextSecondary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(thickness = 1.dp, color = DividerColor)

            SwitchRow("Notificaciones", preferences.notificationsEnabled) {
                onPreferencesChange(preferences.copy(notificationsEnabled = it))
            }
            SwitchRow("Sonido de alarma", preferences.alarmSoundEnabled) {
                onPreferencesChange(preferences.copy(alarmSoundEnabled = it))
            }
            SwitchRow("Vibración", preferences.vibrationEnabled) {
                onPreferencesChange(preferences.copy(vibrationEnabled = it))
            }
            DurationDropdownRow(
                selectedMinutes = preferences.pomodoroMinutes,
                onSelected = { onPreferencesChange(preferences.copy(pomodoroMinutes = it)) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(1.dp, DangerRed),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = SurfaceWhite,
                    contentColor = DangerRed
                )
            ) {
                Text(text = "Cerrar Sesión", fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun SwitchRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    SettingsRow(label = label) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PurplePrimary,
                uncheckedThumbColor = TextSecondary,
                uncheckedTrackColor = SurfaceWhite,
                uncheckedBorderColor = TextSecondary
            )
        )
    }
}

@Composable
private fun DurationDropdownRow(selectedMinutes: Int, onSelected: (Int) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    SettingsRow(label = "Duración del pomodoro") {
        Box {
            TextButton(onClick = { expanded = true }) {
                Text(text = "$selectedMinutes min", color = PurplePrimary)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Elegir duración",
                    tint = PurplePrimary
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                pomodoroDurationOptions.forEach { minutes ->
                    DropdownMenuItem(
                        text = { Text("$minutes min") },
                        onClick = {
                            onSelected(minutes)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsRow(label: String, trailing: @Composable () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, color = TextPrimary, fontSize = 15.sp)
            trailing()
        }
        HorizontalDivider(thickness = 1.dp, color = DividerColor)
    }
}
