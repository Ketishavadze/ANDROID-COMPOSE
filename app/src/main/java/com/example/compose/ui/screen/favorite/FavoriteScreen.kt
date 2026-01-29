package com.example.compose.ui.screen.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import com.example.compose.ui.theme.AppTheme


@Composable
fun FavoriteScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Favorite Screen",
            style = AppTheme.typography.headlineMedium,
            color = AppTheme.colorScheme.onBackground
        )
    }
}
