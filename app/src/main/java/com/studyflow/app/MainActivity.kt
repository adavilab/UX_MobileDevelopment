package com.studyflow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.studyflow.app.navigation.StudyFlowNavHost
import com.studyflow.app.ui.theme.StudyFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyFlowTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    StudyFlowNavHost()
                }
            }
        }
    }
}
