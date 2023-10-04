@file:OptIn(ExperimentalFoundationApi::class)

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.chrynan.navigation.ExperimentalNavigationApi
import com.chrynan.navigation.compose.NavigationContainer
import com.chrynan.navigation.compose.rememberNavigator
import com.chrynan.navigation.push
import navigation.AppContext
import navigation.AppDestination.*

@OptIn(ExperimentalNavigationApi::class)
@Composable
fun App() {
    val navigator = rememberNavigator(initialDestination = AppContext.HOME)

    Column {
        Box(modifier = Modifier.weight(1f)) {
            NavigationContainer(
                navigator = navigator,
                modifier = Modifier.fillMaxSize()
            ) { (destination, _) ->
                when (destination) {
                    AppContext.HOME -> HomeScreen()
                    AppContext.EXPLORE -> ExploreScreen()
                    AppContext.CREATE -> ExploreScreen()
                    AppContext.REELS -> ExploreScreen()
                    AppContext.PROFILE -> ExploreScreen()
                }

            }
        }
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            BottomNavigation(backgroundColor = Color.White) {
                AppContext.values().forEach {
                    it.name
                    BottomNavigationItem(
                        selected = false,
                        onClick = { navigator.push(it) },
                        icon = { Image(imageVector = it.icon, contentDescription = null) }
                    )
                }
            }
        }
    }
}

expect val billabongFontFamily: FontFamily

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}