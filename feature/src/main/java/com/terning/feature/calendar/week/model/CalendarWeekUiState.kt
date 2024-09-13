package com.terning.feature.calendar.week.model

import com.terning.core.state.UiState
import com.terning.domain.entity.calendar.CalendarScrapDetail

data class CalendarWeekUiState(
    val loadState: UiState<List<CalendarScrapDetail>> = UiState.Loading,
    val scrapDialogVisibility: Boolean = false,
    val internDialogVisibility: Boolean = false,
    val internshipAnnouncementId: Long? = null,
    val internshipModel: CalendarScrapDetail? = null
)