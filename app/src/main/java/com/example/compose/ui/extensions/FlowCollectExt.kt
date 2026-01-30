package com.example.compose.ui.extensions


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

/**
 * Small convenience so screens read cleanly.
 */
@Composable
fun <T> Flow<T>.CollectInLaunchedEffect(key: Any? = Unit, onEach: suspend (T) -> Unit) {
    LaunchedEffect(key) {
        collect { onEach(it) }
    }
}
