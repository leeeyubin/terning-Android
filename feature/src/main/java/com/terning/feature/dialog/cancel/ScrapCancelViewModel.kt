package com.terning.feature.dialog.cancel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terning.domain.entity.CalendarScrapRequest
import com.terning.domain.repository.ScrapRepository
import com.terning.feature.R
import com.terning.feature.calendar.list.CalendarListSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScrapCancelViewModel @Inject constructor(
    private val scrapRepository: ScrapRepository
) : ViewModel() {
    private var _sideEffect: MutableSharedFlow<ScrapCancelSideEffect> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    fun deleteScrap(
        scrapId: Long
    ) = viewModelScope.launch {
        scrapRepository.deleteScrap(CalendarScrapRequest(scrapId, null))
            .onSuccess {
                _sideEffect.emit(ScrapCancelSideEffect.DismissDialog)
            }.onFailure {
                _sideEffect.emit(ScrapCancelSideEffect.ShowToast(R.string.server_failure))
            }
    }
}