package com.terning.core.designsystem.component.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.terning.core.R
import com.terning.core.designsystem.theme.TerningPointTheme

@Composable
fun TerningImage(
    painter: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = painter),
        contentDescription = stringResource(id = R.string.image_content_descriptin),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TerningBasicImagePreview() {
    TerningPointTheme {
        TerningImage(
            painter = R.drawable.ic_back
        )
    }
}