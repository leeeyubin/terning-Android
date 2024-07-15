package com.terning.feature.calendar.scrap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.terning.core.designsystem.theme.TerningTheme
import com.terning.core.designsystem.theme.White
import com.terning.domain.entity.response.ScrapModel
import com.terning.feature.calendar.scrap.model.Scrap

@Composable
fun CalendarScrapStrip(
    modifier: Modifier = Modifier,
    scrapLists: List<ScrapModel>
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(top = 3.dp)
    ) {
        items(scrapLists.subList(0, MAX_SCRAP_COUNT.coerceAtMost(scrapLists.size))) { scrap ->
            Text(
                text = scrap.title,
                style = TerningTheme.typography.body5,
                textAlign = TextAlign.Center,
                color = White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(android.graphics.Color.parseColor(scrap.color)))
            )
        }

        item{
            if(scrapLists.size > MAX_SCRAP_COUNT) {
                Text(
                    text = "+${(scrapLists.size - MAX_SCRAP_COUNT)}",
                    style = TerningTheme.typography.detail4,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

private const val MAX_SCRAP_COUNT = 3

