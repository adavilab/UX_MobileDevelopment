package com.studyflow.app.ui.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.studyflow.app.ui.components.BottomNavBar
import com.studyflow.app.ui.components.BottomNavItem
import com.studyflow.app.ui.screens.session.StudyTask
import com.studyflow.app.ui.screens.session.mockSuggestedTasks
import com.studyflow.app.ui.theme.BackgroundOffWhite
import com.studyflow.app.ui.theme.InputBorder
import com.studyflow.app.ui.theme.PurplePrimary
import com.studyflow.app.ui.theme.SurfaceWhite
import com.studyflow.app.ui.theme.TextPrimary
import com.studyflow.app.ui.theme.TextSecondary

@Composable
fun TasksScreen(
    onStartTask: (StudyTask) -> Unit,
    onBottomNavSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = BackgroundOffWhite,
        bottomBar = {
            BottomNavBar(
                selectedItem = BottomNavItem.StartSession,
                onItemSelected = onBottomNavSelected
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "Tareas Sugeridas",
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tus tareas se organizan de más urgente a menos urgente",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(14.dp))
            }
            items(mockSuggestedTasks, key = { it.id }) { task ->
                TaskCard(task = task, onStartClick = { onStartTask(task) })
            }
        }
    }
}

@Composable
private fun TaskCard(
    task: StudyTask,
    onStartClick: () -> Unit
) {
    val shape = RoundedCornerShape(8.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(shape)
            .background(SurfaceWhite)
            .border(1.dp, InputBorder, shape)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(PurplePrimary)
        )
        Column(modifier = Modifier.padding(start = 12.dp, top = 14.dp, end = 16.dp, bottom = 12.dp)) {
            Text(
                text = task.title,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Entrega: ${task.dueDate}", color = TextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Progreso: ${task.progressPercent}%", color = TextSecondary, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .width(110.dp)
                    .height(30.dp),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
            ) {
                Text(text = "Empezar", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
