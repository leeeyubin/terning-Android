package com.terning.feature.home.home

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terning.core.designsystem.theme.CalRed
import com.terning.core.state.UiState
import com.terning.domain.entity.CalendarScrapRequest
import com.terning.domain.entity.request.ChangeFilteringRequestModel
import com.terning.domain.entity.response.HomeFilteringInfoModel
import com.terning.domain.entity.response.HomeRecommendInternModel
import com.terning.domain.entity.response.HomeTodayInternModel
import com.terning.domain.repository.HomeRepository
import com.terning.domain.repository.MyPageRepository
import com.terning.domain.repository.ScrapRepository
import com.terning.feature.R
import com.terning.feature.home.home.model.HomeDialogState
import com.terning.feature.home.home.model.SortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val myPageRepository: MyPageRepository,
    private val scrapRepository: ScrapRepository,
) : ViewModel() {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

    private val _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val homeState get() = _homeState.asStateFlow()

    private val _homeSideEffect = MutableSharedFlow<HomeSideEffect>()
    val homeSideEffect get() = _homeSideEffect.asSharedFlow()

    private val _homeTodayState =
        MutableStateFlow<UiState<List<HomeTodayInternModel>>>(UiState.Loading)
    val homeTodayState get() = _homeTodayState.asStateFlow()

    private val _homeRecommendInternState =
        MutableStateFlow<UiState<List<HomeRecommendInternModel>>>(UiState.Loading)
    val homeRecommendInternState get() = _homeRecommendInternState.asStateFlow()

    private val _homeFilteringState =
        MutableStateFlow<UiState<HomeFilteringInfoModel>>(UiState.Loading)
    val homeFilteringState get() = _homeFilteringState.asStateFlow()

    private val _homeSortByState = MutableStateFlow(0)
    val homeSortByState get() = _homeSortByState.asStateFlow()

    private val _homeDialogState: MutableStateFlow<HomeDialogState> =
        MutableStateFlow(HomeDialogState())
    val homeDialogState get() = _homeDialogState.asStateFlow()

    init {
        getProfile()
        getFilteringInfo()
    }

    fun getRecommendInternsData(sortBy: Int, startYear: Int, startMonth: Int) {
        _homeRecommendInternState.value = UiState.Loading
        viewModelScope.launch {
            homeRepository.getRecommendIntern(SortBy.entries[sortBy].sortBy, startYear, startMonth)
                .onSuccess { internList ->
                    _homeRecommendInternState.value = UiState.Success(internList)
                }.onFailure { exception: Throwable ->
                    _homeRecommendInternState.value = UiState.Failure(exception.message ?: "")
                    _homeSideEffect.emit(HomeSideEffect.ShowToast(R.string.server_failure))
                }
        }
    }

    fun getHomeTodayInternList() {
        _homeTodayState.value = UiState.Loading
        viewModelScope.launch {
            homeRepository.getHomeTodayInternList().onSuccess { internList ->
                _homeTodayState.value = UiState.Success(internList)
            }.onFailure { exception: Throwable ->
                _homeTodayState.value = UiState.Failure(exception.message ?: "")
                _homeSideEffect.emit(HomeSideEffect.ShowToast(R.string.server_failure))
            }
        }
    }

    fun getFilteringInfo() {
        _homeFilteringState.value = UiState.Loading
        viewModelScope.launch {
            homeRepository.getFilteringInfo().onSuccess { filteringInfo ->
                _homeFilteringState.value = UiState.Success(filteringInfo)
            }.onFailure { exception: Throwable ->
                _homeFilteringState.value = UiState.Failure(exception.message ?: "")
                _homeSideEffect.emit(HomeSideEffect.ShowToast(R.string.server_failure))
            }
        }
    }

    fun putFilteringInfo(changeFilterRequest: ChangeFilteringRequestModel) {
        viewModelScope.launch {
            homeRepository.putFilteringInfo(
                changeFilterRequest
            ).onSuccess {
                _homeSideEffect.emit(HomeSideEffect.NavigateToHome)
            }
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            myPageRepository.getProfile().onSuccess { response ->
                _homeState.value = _homeState.value.copy(
                    homeUserNameState = UiState.Success(response.name)
                )
            }.onFailure { exception: Throwable ->
                _homeState.value =
                    _homeState.value.copy(homeUserNameState = UiState.Failure(exception.toString()))
                _homeSideEffect.emit(HomeSideEffect.ShowToast(R.string.server_failure))
            }
        }
    }

    fun postScrap(
        internshipAnnouncementId: Long,
        colorIndex: Int,
    ) {
        viewModelScope.launch {
            scrapRepository.postScrap(
                CalendarScrapRequest(
                    id = internshipAnnouncementId,
                    color = colorIndex,
                )
            ).onSuccess {
                updateScrapDialogVisible(visible = false)
                updateScrapped(scrapped = true)
                updateSelectColor(CalRed)
                getHomeTodayInternList()
                getFilteringInfo()
                _homeSideEffect.emit(HomeSideEffect.ShowToast(R.string.intern_scrap_add_toast_message))
            }.onFailure {
                _homeSideEffect.emit(HomeSideEffect.ShowToast(R.string.server_failure))
            }
        }
    }

    fun deleteScrap(scrapId: Long) {
        viewModelScope.launch {
            scrapRepository.deleteScrap(
                CalendarScrapRequest(id = scrapId)
            ).onSuccess {
                updateScrapDialogVisible(visible = false)
                updateScrapped(scrapped = true)
                getHomeTodayInternList()
                getFilteringInfo()
                _homeSideEffect.emit(HomeSideEffect.ShowToast(R.string.intern_scrap_delete_toast_message))
            }.onFailure {
                _homeSideEffect.emit(HomeSideEffect.ShowToast(R.string.server_failure))
            }
        }
    }

    fun patchScrap(
        scrapId: Long,
        colorIndex: Int,
    ) {
        viewModelScope.launch {
            scrapRepository.patchScrap(
                CalendarScrapRequest(
                    id = scrapId,
                    color = colorIndex,
                )
            ).onSuccess {
                updateScrapDialogVisible(visible = false)
                updateScrapped(scrapped = true)
                updateSelectColor(CalRed)
                getHomeTodayInternList()
            }.onFailure {
                _homeSideEffect.emit(HomeSideEffect.ShowToast(R.string.server_failure))
            }
        }
    }

    fun updateSelectColor(newColor: Color) {
        _homeDialogState.update {
            it.copy(selectedColor = newColor)
        }
    }

    fun updateScrapDialogVisible(visible: Boolean) {
        _homeDialogState.update {
            it.copy(isScrapDialogVisible = visible)
        }
    }

    fun updateScrapped(scrapped: Boolean) {
        _homeDialogState.update {
            it.copy(isScrappedState = scrapped)
        }
    }

    fun updatePaletteOpen(open: Boolean) {
        _homeDialogState.update {
            it.copy(isPaletteOpen = open)
        }
    }

    fun updateColorChange(change: Boolean) {
        _homeDialogState.update {
            it.copy(isColorChange = change)
        }
    }

    fun updateIsToday(change: Boolean) {
        _homeDialogState.update {
            it.copy(isToday = change)
        }
    }
}