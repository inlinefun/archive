package com.github.inlinefun.lazygo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.github.inlinefun.lazygo.common.LazyGoTheme
import com.github.inlinefun.lazygo.common.Screens
import com.github.inlinefun.lazygo.composables.SetupScreen
import com.github.inlinefun.lazygo.composables.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyGoTheme {
                Scaffold { paddingValues ->
                    AppContent(
                        modifier = Modifier
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun AppContent(
    modifier: Modifier = Modifier
) {
    val backstack = rememberNavBackStack(Screens.Splash)
    NavDisplay(
        backStack = backstack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Screens.Splash> {
                SplashScreen { screen ->
                    backstack[0] = screen
                }
            }
            entry<Screens.Setup> {
                SetupScreen { screen ->
                    backstack[0] = screen
                }
            }
            entry<Screens.Map> {

            }
        }
    )
}