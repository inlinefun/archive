package com.github.inlinefun.lazygo.composables

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.github.inlinefun.lazygo.R
import com.github.inlinefun.lazygo.common.Constants
import com.github.inlinefun.lazygo.common.PreviewTheme
import com.github.inlinefun.lazygo.common.Screens
import com.github.inlinefun.lazygo.data.PermissionStep
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun SetupScreen(
    navigate: (NavKey) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val steps = listOf(
        PermissionStep(
            titleId = R.string.permission_location_title,
            descriptionId = R.string.permission_location_description,
            buttonLabel = R.string.permission_location_button,
            icon = Icons.Default.LocationOn,
            action = {
                when {
                    locationPermissionState.status.isGranted -> { }
                    locationPermissionState.status.shouldShowRationale -> {
                        val packageData = Uri.fromParts("package", context.applicationInfo.packageName, null)
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(packageData)
                        context.startActivity(intent)
                    }
                    else -> locationPermissionState.launchPermissionRequest()
                }
            },
            requirementSatisfied = {
                locationPermissionState.status.isGranted
            }
        )
    )
    val state = rememberPagerState { steps.size }
    val item by remember {
        derivedStateOf {
            steps[state.currentPage]
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.Padding.medium)
    ) {
        HorizontalPager(
            state = state,
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1.0f)
                )
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                )
                Text(
                    text = stringResource(item.titleId),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(item.descriptionId),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(
                    modifier = Modifier
                        .weight(1.0f)
                )
            }
        }
        Button(
            onClick = {
                scope.launch {
                    when {
                        item.requirementSatisfied() -> {
                            if (state.canScrollForward) {
                                state.animateScrollToPage(state.currentPage + 1)
                            } else {
                                navigate(Screens.Map)
                            }
                        }
                        else -> item.action()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AnimatedContent(
                targetState = when {
                    item.requirementSatisfied() -> {
                        R.string.label_next.takeIf {
                            state.canScrollForward
                        } ?: R.string.label_complete
                    }
                    else -> item.buttonLabel
                }
            ) { resourceId ->
                Text(
                    text = stringResource(resourceId),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewSplashScreen() {
    PreviewTheme {
        SetupScreen(
            navigate = { }
        )
    }
}
