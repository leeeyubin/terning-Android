package com.terning.feature.onboarding.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import androidx.navigation.NavHostController
import com.terning.core.designsystem.theme.TerningTheme
import com.terning.core.extension.toast
import com.terning.feature.R
import com.terning.feature.home.navigation.navigateHome
import com.terning.feature.onboarding.signin.component.KakaoButton

@Composable
fun SignInRoute(
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val signInState by viewModel.signInState.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    LaunchedEffect(viewModel.signInSideEffects, lifecycleOwner) {
        viewModel.signInSideEffects.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SignInSideEffect.ShowToast -> context.toast(sideEffect.message)
                    is SignInSideEffect.NavigateToHome -> navController.navigateHome()
                }
            }
    }

    SignInScreen(
        onSignInClick = { viewModel.startKakaoLogIn(context) }
    )
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_terning_point),
            contentDescription = null,
            modifier = Modifier
                .size(500.dp),
        )
        KakaoButton(
            title = stringResource(id = R.string.sign_in_kakao_button),
            onSignInClick = { onSignInClick() },
            modifier = modifier.padding(horizontal = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    TerningTheme {
        SignInScreen()
    }
}