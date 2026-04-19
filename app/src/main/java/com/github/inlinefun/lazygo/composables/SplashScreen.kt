package com.github.inlinefun.lazygo.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.inlinefun.lazygo.common.PreviewTheme

@Composable
fun SplashScreen() {

}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewSplashScreen() {
    PreviewTheme {
        SplashScreen()
    }
}
