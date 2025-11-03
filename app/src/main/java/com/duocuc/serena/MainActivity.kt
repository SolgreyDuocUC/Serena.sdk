package com.duocuc.serena

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.duocuc.serena.navigation.AppNav
import com.duocuc.serena.ui.theme.theme.SerenaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SerenaTheme {
                AppNav()
            }
        }
    }
}
