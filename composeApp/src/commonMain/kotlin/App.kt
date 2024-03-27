import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.chrynan.navigation.ExperimentalNavigationApi
import com.chrynan.navigation.compose.NavigationContainer
import com.chrynan.navigation.compose.rememberNavigator
import com.chrynan.navigation.push
import navigation.AppContext
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.ExploreScreen
import ui.HomeScreen

@OptIn(ExperimentalNavigationApi::class)
@Composable
@Preview
fun App() {
    val navigator = rememberNavigator(initialDestination = AppContext.HOME)

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                    AppContext.entries.forEach {
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
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

expect val billabongFontFamily: FontFamily