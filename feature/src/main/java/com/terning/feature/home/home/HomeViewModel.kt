package com.terning.feature.home.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.terning.core.designsystem.theme.CalBlue1
import com.terning.core.designsystem.theme.CalBlue2
import com.terning.core.designsystem.theme.CalGreen1
import com.terning.core.designsystem.theme.CalGreen2
import com.terning.core.designsystem.theme.CalOrange1
import com.terning.core.designsystem.theme.CalPink
import com.terning.core.designsystem.theme.CalYellow
import com.terning.feature.home.home.model.InternData
import com.terning.feature.home.home.model.ScrapData
import com.terning.feature.home.home.model.UserNameState
import com.terning.feature.home.home.model.UserScrapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    private val _userName by mutableStateOf(
        UserNameState(
            userName = "남지우자랑스러운티엘이되",
            internFilter =
            null
//            InternFilterData(
//                grade = 3,
//                workingPeriod = 3,
//                startYear = 2024,
//                startMonth = 7,
//            )
        )
    )
    val userName get() = _userName

    private val _scrapState = MutableStateFlow(
        UserScrapState(
            isScrapExist = true,
            scrapData = getScrapData()
        )
    )
    val scrapData get() = _scrapState.asStateFlow()

    private val _recommendInternState = MutableStateFlow(
        getRecommendData()
    )
    val recommendInternData get() = _recommendInternState.asStateFlow()

    fun setGrade(grade: Int) {
        userName.internFilter?.grade = grade
    }

    fun setWorkingPeriod(workingPeriod: Int) {
        userName.internFilter?.workingPeriod = workingPeriod
    }
}

private fun getScrapData(): List<ScrapData> = listOf(
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        scrapColor = CalBlue1,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 숲 활동가",
        scrapColor = CalPink,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        scrapColor = CalYellow,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 숲 활동가",
        scrapColor = CalBlue2,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        scrapColor = CalGreen1,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 숲 활동가",
        scrapColor = CalOrange1,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        scrapColor = CalGreen2,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 숲 활동가",
        scrapColor = CalPink,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        scrapColor = CalBlue1,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 숲 활동가",
        scrapColor = CalPink,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        scrapColor = CalBlue1,
    ),
    ScrapData(
        internTitle = "[유한킴벌리] 그린캠프 숲 활동가",
        scrapColor = CalPink,
    ),
)

private fun getRecommendData(): List<InternData> = listOf(
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        dDay = 22,
        workingPeriod = 2,
        isScrapped = true,
    ),
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "ㅇㄻㅇㅁㄻㄹㅇㅁㅇㄹ",
        dDay = 9,
        workingPeriod = 6,
        isScrapped = false,
    ),
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        dDay = 2,
        workingPeriod = 4,
        isScrapped = true,
    ),
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        dDay = 2,
        workingPeriod = 4,
        isScrapped = false,
    ),
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        dDay = 2,
        workingPeriod = 4,
        isScrapped = true,
    ),
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        dDay = 2,
        workingPeriod = 4,
        isScrapped = true,
    ),
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        dDay = 2,
        workingPeriod = 4,
        isScrapped = false,
    ),
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        dDay = 2,
        workingPeriod = 4,
        isScrapped = true,
    ),
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        dDay = 2,
        workingPeriod = 4,
        isScrapped = false,
    ),
    InternData(
        imgUrl = "https://reqres.in/img/faces/7-image.jpg",
        title = "[유한킴벌리] 그린캠프 w.대학생 숲 활동가",
        dDay = 2,
        workingPeriod = 4,
        isScrapped = true,
    ),
)