package com.terning.feature.calendar.calendar

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terning.core.designsystem.component.topappbar.CalendarTopAppBar
import com.terning.core.designsystem.theme.Grey200
import com.terning.core.designsystem.theme.White
import com.terning.feature.calendar.calendar.component.ScreenTransition
import com.terning.feature.calendar.calendar.component.WeekDaysHeader
import com.terning.feature.calendar.calendar.model.CalendarModel.Companion.getYearMonthByPage
import com.terning.feature.calendar.calendar.model.CalendarUiState
import com.terning.feature.calendar.list.CalendarListRoute
import com.terning.feature.calendar.month.CalendarMonthRoute
import com.terning.feature.calendar.week.CalendarWeekRoute
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun CalendarRoute(
    navigateToAnnouncement: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    CalendarScreen(
        modifier = modifier,
        uiState = uiState,
        navigateToAnnouncement = navigateToAnnouncement,
        updateSelectedDate = viewModel::updateSelectedDate,
        updatePage = viewModel::updatePage,
        disableListVisibility = { viewModel.updateListVisibility(false) },
        disableWeekVisibility = { viewModel.updateSelectedDate(uiState.selectedDate) },
        onClickListButton = {
            viewModel.updateListVisibility(!uiState.isListEnabled)
            if (uiState.isWeekEnabled) {
                viewModel.updateWeekVisibility(false)
            }
        }
    )
}

@Composable
private fun CalendarScreen(
    uiState: CalendarUiState,
    navigateToAnnouncement: (Long) -> Unit,
    updateSelectedDate: (LocalDate) -> Unit,
    disableListVisibility: () -> Unit,
    disableWeekVisibility: () -> Unit,
    updatePage: (Int) -> Unit,
    onClickListButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = uiState.calendarModel.initialPage
    )

    LaunchedEffect(key1 = listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect {
                updatePage(listState.firstVisibleItemIndex)
            }
    }

    Column(
        modifier = modifier,
    ){
        CalendarTopAppBar(
            date = getYearMonthByPage(uiState.currentPage),
            isListExpanded = uiState.isListEnabled,
            isWeekExpanded = uiState.isWeekEnabled,
            onListButtonClicked = onClickListButton,
            onMonthNavigationButtonClicked = { direction ->
                coroutineScope.launch {
                    listState.animateScrollToItem(
                        index = listState.firstVisibleItemIndex + direction,
                    )
                }
            }
        )
        ScreenTransition(
            targetState = !uiState.isListEnabled,
            transitionOne = slideInHorizontally { fullWidth -> -fullWidth } togetherWith
                    slideOutHorizontally { fullWidth -> fullWidth },
            transitionTwo = slideInHorizontally { fullWidth -> fullWidth } togetherWith
                    slideOutHorizontally { fullWidth -> -fullWidth },
            contentOne = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    WeekDaysHeader()

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Grey200
                    )

                    ScreenTransition(
                        targetState = !uiState.isWeekEnabled,
                        transitionOne = slideInVertically { fullHeight -> -fullHeight } togetherWith
                                slideOutVertically { fullHeight -> fullHeight },
                        transitionTwo = slideInVertically { fullHeight -> fullHeight } togetherWith
                                slideOutVertically { fullHeight -> -fullHeight },
                        contentOne = {
                            CalendarMonthRoute(
                                listState = listState,
                                pages = uiState.calendarModel.pageCount,
                                selectedDate = uiState.selectedDate,
                                updateSelectedDate = updateSelectedDate,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(White),
                            )
                        },
                        contentTwo = {
                            CalendarWeekRoute(
                                calendarUiState = uiState,
                                modifier = Modifier
                                    .fillMaxSize(),
                                navigateUp = disableWeekVisibility,
                                navigateToAnnouncement = navigateToAnnouncement,
                                updateSelectedDate = updateSelectedDate
                            )
                        }
                    )
                }
            },
            contentTwo = {
                CalendarListRoute(
                    listState = listState,
                    pages = uiState.calendarModel.pageCount,
                    navigateToAnnouncement = navigateToAnnouncement,
                    navigateUp = disableListVisibility,
                    modifier = Modifier
                        .fillMaxSize()
                )
            },
        )
    }
}

