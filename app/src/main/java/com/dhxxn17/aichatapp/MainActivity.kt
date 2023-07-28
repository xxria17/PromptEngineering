package com.dhxxn17.aichatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dhxxn17.aichatapp.ui.theme.AIChatAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AIChatAppTheme {

            }
        }
    }
}

