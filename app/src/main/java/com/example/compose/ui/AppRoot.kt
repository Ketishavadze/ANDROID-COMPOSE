package com.example.compose.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.compose.navigation.NavHost
import com.example.compose.ui.theme.AppTheme

@Composable
fun AppRoot() {
    val colors = AppTheme.colorScheme

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colors.background
    ) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            /* navController, startDestination, etc */
        )
    }
}
