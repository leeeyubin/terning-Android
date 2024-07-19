package com.terning.feature.search.searchprocess.models

import androidx.compose.ui.graphics.Color
import com.terning.core.designsystem.theme.CalRed

data class SearchDialogState(
    val isColorChange: Boolean = false,
    val isPaletteOpen: Boolean = false,
    val selectedColor: Color = CalRed,
    val isScrapDialogVisible: Boolean = false,
    val scrapped: Boolean = false,
    val isScrappedState: Boolean = false,
)