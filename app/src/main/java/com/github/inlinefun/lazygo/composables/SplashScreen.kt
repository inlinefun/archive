package com.github.inlinefun.lazygo.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.Constants
import com.github.inlinefun.lazygo.common.PreviewTheme
import com.github.inlinefun.lazygo.common.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    toScreen: (NavKey) -> Unit
) {
    val splashResourceId = listOf(
        R.string.splash_label_one,
        R.string.splash_label_two,
        R.string.splash_label_three,
        R.string.splash_label_four,
        R.string.splash_label_five
    ).random()
    LaunchedEffect(Unit) {
        delay(500L)
        toScreen(Screens.Setup )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Constants.Padding.small),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .weight(1.0f)
        )
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .size(192.dp)
        )
        LinearProgressIndicator()
        Spacer(
            modifier = Modifier
                .weight(1.0f)
        )
        Text(
            text = stringResource(splashResourceId),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewSplashScreen() {
    PreviewTheme {
        SplashScreen(
            toScreen = { }
        )
    }
}
