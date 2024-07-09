package com.terning.core.designsystem.topappbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.terning.core.R
import com.terning.core.designsystem.theme.TerningTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerningBasicTopAppBar(
    title: String = "",
    showBackButton: Boolean = false,
    actions: List<@Composable () -> Unit> = emptyList(),
    onBackButtonClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {

            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = TerningTheme.typography.title2
            )

        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = {
                    onBackButtonClick.invoke()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(id = R.string.ic_back)
                    )
                }
            } else {
                actions.getOrNull(0)?.invoke()
            }
        },
        actions = {
            actions.drop(1).forEach { action ->
                action()
            }
        },
    )
}