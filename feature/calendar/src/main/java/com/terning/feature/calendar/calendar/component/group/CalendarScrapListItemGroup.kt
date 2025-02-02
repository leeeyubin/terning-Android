package com.terning.feature.calendar.calendar.component.group

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.terning.core.designsystem.component.item.InternItem
import com.terning.core.designsystem.component.item.ScrapBox
import com.terning.core.designsystem.extension.customShadow
import com.terning.core.designsystem.extension.noRippleClickable
import com.terning.core.designsystem.theme.Grey200
import com.terning.domain.calendar.entity.CalendarScrapDetail

@Composable
fun CalendarScrapListItemGroup(
    scrap: CalendarScrapDetail,
    onScrapButtonClicked: (Long) -> Unit,
    onInternshipClicked: (CalendarScrapDetail) -> Unit,
    modifier: Modifier = Modifier,
) {
    ScrapBox(
        modifier = modifier.customShadow(
            color = Grey200,
            shadowRadius = 5.dp,
            shadowWidth = 1.dp,
        ).noRippleClickable {
            onInternshipClicked(scrap)
        },
        cornerRadius = 10.dp,
        scrapColor = Color(android.graphics.Color.parseColor(scrap.color))
    ) {
        InternItem(
            scrapId = scrap.internshipAnnouncementId,
            imageUrl = scrap.companyImage,
            title = scrap.title,
            dateDeadline = scrap.dDay,
            workingPeriod = scrap.workingPeriod,
            isScraped = scrap.isScrapped,
            onScrapButtonClicked = onScrapButtonClicked
        )
    }
}