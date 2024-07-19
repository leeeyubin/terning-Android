package com.terning.core.designsystem.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.terning.core.R
import com.terning.core.designsystem.component.button.RoundButton
import com.terning.core.designsystem.component.item.TerningLottieAnimation
import com.terning.core.designsystem.theme.Grey200
import com.terning.core.designsystem.theme.Grey350
import com.terning.core.designsystem.theme.Grey500
import com.terning.core.designsystem.theme.TerningMain
import com.terning.core.designsystem.theme.TerningTheme

@Composable
fun ScrapCancelDialogContent(
    onClickScrapCancel: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TerningLottieAnimation(
                jsonString = "terning_scrap_cancel.json",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Text(
                text = stringResource(id = R.string.dialog_content_scrap_cancel_main_title),
                textAlign = TextAlign.Center,
                style = TerningTheme.typography.title4,
                color = Grey500,
            )
            Text(
                text = stringResource(id = R.string.dialog_content_scrap_cancel_sub_title),
                style = TerningTheme.typography.body5,
                color = Grey350,
                modifier = Modifier.padding(
                    top = 5.dp
                )
            )
            RoundButton(
                style = TerningTheme.typography.button3,
                paddingVertical = 12.dp,
                cornerRadius = 8.dp,
                text = R.string.dialog_scrap_cancel_button,
                onButtonClick = onClickScrapCancel,
                modifier = Modifier.padding(bottom = 8.dp, top = 40.dp)
            )
        }
    }
}