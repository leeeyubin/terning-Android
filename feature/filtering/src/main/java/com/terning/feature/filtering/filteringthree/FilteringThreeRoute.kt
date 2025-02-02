package com.terning.feature.filtering.filteringthree

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.terning.core.analytics.EventType
import com.terning.core.analytics.LocalTracker
import com.terning.core.designsystem.component.button.RectangleButton
import com.terning.core.designsystem.component.topappbar.BackButtonTopAppBar
import com.terning.core.designsystem.extension.currentMonth
import com.terning.core.designsystem.extension.currentYear
import com.terning.core.designsystem.extension.toast
import com.terning.core.designsystem.theme.Grey300
import com.terning.core.designsystem.theme.TerningPointTheme
import com.terning.core.designsystem.theme.TerningTheme
import com.terning.core.designsystem.theme.White
import com.terning.feature.filtering.R
import com.terning.feature.filtering.filteringthree.component.FilteringYearMonthPicker
import java.util.Calendar

@Composable
fun FilteringThreeRoute(
    grade: String,
    workingPeriod: String,
    navigateUp: () -> Unit,
    navigateToStartHome: () -> Unit,
    viewModel: FilteringThreeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val amplitudeTracker = LocalTracker.current

    LaunchedEffect(key1 = true) {
        with(viewModel) {
            updateGrade(grade = grade)
            updateWorkingPeriod(workingPeriod = workingPeriod)
        }
    }

    LaunchedEffect(key1 = state.startYear, key2 = state.startMonth) {
        with(viewModel) {
            updateStartYear(state.startYear)
            updateStartMonth(state.startMonth)
        }
    }

    LaunchedEffect(viewModel.sideEffects, lifecycleOwner) {
        viewModel.sideEffects.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is FilteringThreeSideEffect.NavigateToStartHome -> navigateToStartHome()
                    is FilteringThreeSideEffect.ShowToast -> context.toast(sideEffect.message)
                    is FilteringThreeSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    FilteringThreeScreen(
        navigateUp = viewModel::navigateUp,
        chosenYear = Calendar.getInstance().currentYear,
        chosenMonth = Calendar.getInstance().currentMonth,
        onNextClick = {
            amplitudeTracker.track(
                type = EventType.CLICK,
                name = "onboarding_completed",
                properties = mapOf(
                    "grade" to state.grade,
                    "workingPeriod" to state.workingPeriod,
                    "startYear" to state.startYear,
                    "startMonth" to state.startMonth
                )
            )
            viewModel.postFilteringWithServer()
        },
        onYearChosen = viewModel::updateStartYear,
        onMonthChosen = viewModel::updateStartMonth
    )
}

@Composable
fun FilteringThreeScreen(
    navigateUp: () -> Unit,
    chosenYear: Int,
    chosenMonth: Int,
    onYearChosen: (Int) -> Unit,
    onMonthChosen: (Int) -> Unit,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(White),
    ) {
        BackButtonTopAppBar(
            onBackButtonClick = navigateUp
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_filtering_status3),
                modifier = Modifier.padding(
                    top = 28.dp,
                    start = 24.dp
                ),
                contentDescription = "filtering three status"
            )
            Text(
                text = stringResource(id = R.string.filtering_status3_title),
                style = TerningTheme.typography.title3,
                modifier = Modifier.padding(
                    top = 20.dp,
                    start = 24.dp
                )
            )
            Text(
                text = stringResource(id = R.string.filtering_status3_sub),
                style = TerningTheme.typography.body5,
                color = Grey300,
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 24.dp,
                )
            )
            Spacer(modifier = Modifier.height(61.dp))
            FilteringYearMonthPicker(
                chosenYear = chosenYear,
                chosenMonth = chosenMonth,
                onYearChosen = { onYearChosen(it) },
                onMonthChosen = { onMonthChosen(it) },
            )
            Spacer(modifier = Modifier.weight(1f))
            RectangleButton(
                style = TerningTheme.typography.button0,
                paddingVertical = 20.dp,
                text = R.string.filtering_button,
                onButtonClick = onNextClick,
                modifier = Modifier.padding(bottom = 12.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilteringThreeScreenPreview() {
    TerningPointTheme {
        FilteringThreeScreen(
            navigateUp = { },
            chosenYear = 2024,
            chosenMonth = 8,
            onYearChosen = {},
            onMonthChosen = {},
            onNextClick = {}
        )
    }
}