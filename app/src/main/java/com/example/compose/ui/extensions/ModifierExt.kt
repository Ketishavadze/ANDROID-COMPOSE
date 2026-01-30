package com.example.compose.ui.extensions


import androidx.compose.ui.Modifier

inline fun Modifier.applyIf(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (condition) this.block() else this
}
