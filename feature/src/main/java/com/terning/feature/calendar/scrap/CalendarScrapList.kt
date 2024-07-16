package com.terning.feature.calendar.scrap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terning.core.designsystem.theme.Back
import com.terning.core.designsystem.theme.Black
import com.terning.core.designsystem.theme.Grey200
import com.terning.core.designsystem.theme.TerningTheme
import com.terning.core.designsystem.theme.White
import com.terning.core.extension.getDateStringInKorean
import com.terning.core.extension.isListNotEmpty
import com.terning.core.state.UiState
import com.terning.domain.entity.response.CalendarScrapDetailModel
import com.terning.feature.calendar.calendar.CalendarViewModel
import com.terning.feature.calendar.calendar.model.CalendarDefaults.flingBehavior
import com.terning.feature.calendar.calendar.model.CalendarState.Companion.getDateByPage
import com.terning.feature.calendar.scrap.component.CalendarScrap
import com.terning.feature.calendar.scrap.model.Scrap
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarScrapList(
    scrapList: List<List<Scrap>>,
    pages: Int,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scrapState by viewModel.calendarListState.collectAsStateWithLifecycle(lifecycleOwner)

    LaunchedEffect(key1 = listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect {
                val page = listState.firstVisibleItemIndex
                val date = getDateByPage(page)
                viewModel.getScrapMonthList(date.year, date.monthValue)
            }
    }

    when (scrapState.loadState) {
        UiState.Loading -> {}
        UiState.Empty -> {}
        is UiState.Failure -> {}
        is UiState.Success -> {
            val scrapMap = (scrapState.loadState as UiState.Success).data

            LazyRow(
                modifier = modifier
                    .background(White),
                state = listState,
                userScrollEnabled = true,
                flingBehavior = flingBehavior(
                    state = listState
                )
            ) {
                items(pages) { page ->
                    val getDate = getDateByPage(page)

                    LazyColumn(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .fillMaxHeight()
                            .background(Back)
                    ) {
                        items(scrapList.size) { day ->
                            runCatching {
                                LocalDate.of(getDate.year, getDate.monthValue, day + 1)
                            }.onSuccess {
                                CalendarScrapList(
                                    selectedDate = it,
                                    //scrapLists = scrapList,
                                    scrapMap = scrapMap,
                                    isFromList = true,
                                    noScrapScreen = {})


                                if (scrapList[day].isNotEmpty()) {
                                    Spacer(
                                        modifier = Modifier
                                            .height(4.dp)
                                            .fillMaxWidth()
                                            .background(Grey200)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarScrapList(
    selectedDate: LocalDate,
    scrapLists: List<List<Scrap>>,
    isFromList: Boolean = false,
    noScrapScreen: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    if (scrapLists[selectedDate.dayOfMonth - 1].isNotEmpty()) {
        Text(
            text = selectedDate.getDateStringInKorean(),
            style = TerningTheme.typography.title5,
            color = Black,
            modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 15.dp)
        )
    }
    val topModifier = if (!isFromList) {
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
    } else {
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    }

    if (scrapLists[selectedDate.dayOfMonth - 1].isEmpty()) {
        noScrapScreen()
    } else {
        Column(
            modifier = topModifier
        ) {
            for (scrap in scrapLists[selectedDate.dayOfMonth - 1]) {
                CalendarScrap(
                    scrap = scrap
                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}

@Composable
fun CalendarScrapList(
    selectedDate: LocalDate,
    scrapMap: Map<String, List<CalendarScrapDetailModel>>,
    isFromList: Boolean = false,
    noScrapScreen: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val scrapList = scrapMap[selectedDate.format(formatter)]

    if (scrapList.isListNotEmpty()) {
        Text(
            text = selectedDate.getDateStringInKorean(),
            style = TerningTheme.typography.title5,
            color = Black,
            modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 15.dp)
        )
    }
    val topModifier = if (!isFromList) {
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
    } else {
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    }

    if (!scrapList.isListNotEmpty()) {
        noScrapScreen()
    } else {
        Column(
            modifier = topModifier
        ) {
            for (scrap in scrapMap[selectedDate.format(formatter)].orEmpty()) {
                CalendarScrap(
                    scrap = scrap
                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}
