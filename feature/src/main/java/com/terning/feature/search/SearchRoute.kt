package com.terning.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.terning.core.designsystem.component.textfield.SearchTextField
import com.terning.core.designsystem.theme.Black
import com.terning.core.designsystem.theme.Grey100
import com.terning.core.designsystem.theme.TerningTheme
import com.terning.feature.R
import com.terning.feature.search.component.ImageSlider
import com.terning.feature.search.component.SearchInternList
import com.terning.feature.searchprocess.navigation.navigateSearchProcess

@Composable
fun SearchRoute(
    navController: NavHostController,
) {
    SearchScreen(navController = navController)
}

@Composable
fun SearchScreen(
    navController: NavHostController,
) {
    val images = listOf(
        R.drawable.ic_nav_search,
        R.drawable.ic_check,
        R.drawable.ic_nav_my_page,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    navController.navigateSearchProcess()
                },
        ) {
            SearchTextField(
                readOnly = true,
                hint = stringResource(R.string.search_text_field_hint),
                leftIcon = R.drawable.ic_nav_search,
            )
        }

        ImageSlider(
            images = images
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(id = R.string.search_today_popular),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            style = TerningTheme.typography.title1,
            color = Black
        )

        SearchInternList(type = "view")
        HorizontalDivider(
            thickness = 4.dp,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Grey100,
        )
        SearchInternList(type = "scrap")
    }
}