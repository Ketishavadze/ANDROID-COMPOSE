package com.example.compose.ui.graphics

import androidx.compose.ui.graphics.Color

object AppColors {

    // --- Base
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)

    // --- Backgrounds
    val ScreenBackground = Color(0xFFF3F4F8)     // light gray background like your reference
    val CardBackground = White                  // pure white cards

    // --- Text
    val TextPrimary = Color(0xFF111111)
    val TextSecondary = Color(0xFF8A8A8A)

    // --- Borders / Dividers
    val Outline = Color(0xFFDDDDDD)
    val Divider = Color(0xFFEAEAEA)

    // --- Status colors
    val Pending = Color(0xFFFF8A00)     // orange
    val Delivered = Color(0xFF2E7D32)   // green
    val Canceled = Color(0xFFC62828)    // red

    // --- Chips
    val ChipSelectedBg = Black
    val ChipSelectedText = White
    val ChipUnselectedBg = White
    val ChipUnselectedText = TextPrimary
    val ChipBorder = Outline

    // --- Buttons
    val DetailsButtonBorder = Outline
    val DetailsButtonText = TextPrimary
}
