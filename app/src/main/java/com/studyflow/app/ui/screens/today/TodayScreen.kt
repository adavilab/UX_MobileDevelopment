package com.studyflow.app.ui.screens.today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.studyflow.app.ui.components.BottomNavBar
import com.studyflow.app.ui.components.BottomNavItem
import com.studyflow.app.ui.components.DailyTimeline
import com.studyflow.app.ui.theme.BackgroundOffWhite
import com.studyflow.app.ui.theme.DividerColor
import com.studyflow.app.ui.theme.SurfaceWhite
import com.studyflow.app.ui.theme.TextPrimary
import com.studyflow.app.ui.theme.TextSecondary

@Composable
fun TodayScreen(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedNavItem by remember { mutableStateOf(BottomNavItem.Today) }
    var selectedEventId by remember { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = modifier,
        containerColor = BackgroundOffWhite,
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedNavItem,
                onItemSelected = { selectedNavItem = it }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            TodayHeader(onLogoutClick = onLogoutClick)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$COMPLETED_POMODOROS/$TOTAL_POMODOROS pomodoros hoy",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(thickness = 1.dp, color = DividerColor)

            Spacer(modifier = Modifier.height(20.dp))

            DailyTimeline(
                events = mockTodayEvents,
                selectedEventId = selectedEventId,
                onEventClick = { id ->
                    selectedEventId = if (selectedEventId == id) null else id
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun TodayHeader(onLogoutClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = TODAY_WEEKDAY,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            Text(
                text = TODAY_DATE,
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary
            )
        }

        IconButton(
            onClick = onLogoutClick,
            modifier = Modifier
                .size(40.dp)
                .shadow(1.dp, CircleShape)
                .clip(CircleShape)
                .background(SurfaceWhite)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = "Cerrar sesión",
                tint = TextSecondary
            )
        }
    }
}
