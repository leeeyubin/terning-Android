package com.terning.feature.filtering.startfiltering

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.terning.core.designsystem.component.button.RectangleButton
import com.terning.core.designsystem.theme.TerningPointTheme
import com.terning.core.designsystem.theme.TerningTheme
import com.terning.feature.R
import com.terning.feature.filtering.filtering.navigation.navigateFilteringOne
import kotlinx.coroutines.delay

@Composable
fun StartFilteringScreen(
    name: String,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var isVisible by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenHeight = 780f / configuration.screenHeightDp

    LaunchedEffect(key1 = true) {
        delay(1000)
        isVisible = true
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height((128 * screenHeight).dp))
            Text(
                text = stringResource(id = R.string.start_filtering_title),
                style = TerningTheme.typography.title1,
                modifier = Modifier.padding(bottom = 35.dp),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.ic_terning_onboarding),
                contentDescription = stringResource(id = R.string.start_filtering_main_image),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.weight(2f))
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(initialAlpha = 0.3f),
            ) {
                RectangleButton(
                    style = TerningTheme.typography.button0,
                    paddingVertical = 20.dp,
                    text = R.string.start_filtering_button,
                    onButtonClick = { navController.navigateFilteringOne(name) },
                )
            }
        }
    }
}
