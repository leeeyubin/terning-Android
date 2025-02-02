package com.terning.core.designsystem.component.button

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.terning.core.designsystem.R
import com.terning.core.designsystem.theme.Grey150
import com.terning.core.designsystem.theme.Grey350
import com.terning.core.designsystem.theme.TerningMain
import com.terning.core.designsystem.theme.TerningMain2
import com.terning.core.designsystem.theme.TerningPointTheme
import com.terning.core.designsystem.theme.TerningTheme
import com.terning.core.designsystem.theme.White
import com.terning.core.designsystem.util.NoRippleTheme

/**
 * 기본 버튼 함수입니다.
 *
 * @param shape 버튼의 모양을 설정합니다.
 * @param style 버튼 텍스트의 스타일을 정의합니다.
 * @param paddingVertical 버튼의 위아래 패딩을 설정합니다. 패딩 값은 Dp 단위입니다.
 * @param text 버튼에 표시될 텍스트입니다.
 * @param onButtonClick 버튼 클릭 시 호출될 콜백 함수입니다.
 * @param modifier 버튼에 적용할 Modifier입니다.
 * @param isEnabled 버튼의 활성화 상태를 정의합니다.
 */
@Composable
fun TerningBasicButton(
    shape: Shape,
    style: TextStyle,
    paddingVertical: Dp,
    @StringRes text: Int,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = if (isPressed) TerningMain2 else TerningMain

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Button(
            contentPadding = PaddingValues(vertical = paddingVertical),
            modifier = modifier.fillMaxWidth(),
            interactionSource = interactionSource,
            enabled = isEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = White,
                disabledContainerColor = Grey150,
                disabledContentColor = Grey350
            ),
            shape = shape,
            onClick = { onButtonClick() }
        ) {
            Text(
                text = stringResource(id = text),
                style = style,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TerningBasicButtonPreview() {
    TerningPointTheme {
        TerningBasicButton(
            text = R.string.button_preview,
            shape = ButtonDefaults.shape,
            style = TerningTheme.typography.button0,
            paddingVertical = 19.dp,
            onButtonClick = {}
        )
    }
}
