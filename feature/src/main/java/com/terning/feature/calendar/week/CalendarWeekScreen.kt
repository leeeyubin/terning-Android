package com.terning.feature.calendar.week

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.terning.core.designsystem.theme.Back
import com.terning.core.designsystem.theme.Grey200
import com.terning.core.designsystem.theme.Grey400
import com.terning.core.designsystem.theme.TerningTheme
import com.terning.core.designsystem.theme.White
import com.terning.core.state.UiState
import com.terning.domain.entity.response.CalendarScrapDetailModel
import com.terning.feature.R
import com.terning.feature.calendar.calendar.CalendarViewModel
import com.terning.feature.calendar.scrap.component.CalendarScrapList
import java.time.LocalDate

@Composable
fun CalendarWeekScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.selectedDate.collectAsStateWithLifecycle(lifecycleOwner)
    val calendarWeekState by viewModel.calendarWeekState.collectAsStateWithLifecycle(lifecycleOwner)

    LaunchedEffect(uiState.selectedDate) {
        viewModel.getScrapWeekList()
    }

    Column(
        modifier = modifier
            .background(Back)
    ) {
        Card(
            modifier = Modifier
                .border(
                    width = 0.dp,
                    color = Grey200,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
                .shadow(
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                    elevation = 1.dp
                ),

            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        ) {
            HorizontalCalendarWeek(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White),
                selectedDate = uiState,
                onDateSelected = {
                    viewModel.updateSelectedDate(it)
                }
            )
        }

        when (calendarWeekState.loadState) {
            is UiState.Loading -> {}
            is UiState.Empty -> {
                CalendarWeekEmpty()
            }

            is UiState.Failure -> {}
            is UiState.Success -> {
                val scrapList = (calendarWeekState.loadState as UiState.Success).data
                CalendarWeekSuccess(scrapList = scrapList, selectedDate = uiState.selectedDate)
            }
        }
    }
}

@Composable
fun CalendarWeekEmpty(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center
    ) {


        Text(
            modifier = Modifier
                .padding(top = 42.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.calendar_empty_scrap),
            textAlign = TextAlign.Center,
            style = TerningTheme.typography.body5,
            color = Grey400
        )
    }
}

@Composable
fun CalendarWeekSuccess(
    scrapList: List<CalendarScrapDetailModel>,
    selectedDate: LocalDate,
) {

    CalendarScrapList(selectedDate = selectedDate, scrapList = scrapList) {}
}



