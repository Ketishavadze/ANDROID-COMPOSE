package com.example.compose.ui.snackbar

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.AppTheme

@Composable
fun AppSnackbarHost(hostState: SnackbarHostState) {
    SnackbarHost(hostState = hostState) { data ->
        Snackbar(
            snackbarData = data,
            containerColor = AppTheme.colorScheme.error,
            contentColor = AppTheme.colorScheme.onError,
            shape = RoundedCornerShape(8.dp)
        )
    }
}
