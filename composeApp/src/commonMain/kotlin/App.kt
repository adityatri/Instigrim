import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import component.BottomSheetComments
import navigation.CreateTab
import navigation.ExploreTab
import navigation.HomeTab
import navigation.ProfileTab
import navigation.ReelsTab
import org.jetbrains.compose.ui.tooling.preview.Preview

val shouldShowBottomSheet = mutableStateOf(false)
val selectedCommentId = mutableStateOf("")
val hasComment = mutableStateOf(false)
val creatorProfilePic = mutableStateOf("")

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.weight(1f)) {
                TabNavigator(HomeTab) {
                    Scaffold(
                        content = {
                            Box(
                                modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                            ) {
                                CurrentTab()
                            }
                        },
                        bottomBar = {
                            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                                BottomNavigation(
                                    backgroundColor = Color.White,
                                    elevation = 8.dp
                                ) {
                                    TabNavigationItem(HomeTab)
                                    TabNavigationItem(ExploreTab)
                                    TabNavigationItem(CreateTab)
                                    TabNavigationItem(ReelsTab)
                                    TabNavigationItem(ProfileTab)
                                }
                            }
                        }
                    )
                }
            }

            /*
            When putting the BottomSheet inside of other child component, somehow it's buggy in iOS, while it's working perfectly in Android.
            In iOS, the bottom sheet won't appear from the bottom of the screen, instead it will reflect on the bottom of each "PostItem".
            Solution: move the BottomSheet to root component here, and use a mutableState variable to update it.
             */
            if (shouldShowBottomSheet.value) {
                BottomSheetComments(
                    selectedCommentId.value,
                    hasComment.value,
                    creatorProfilePic.value
                ) {
                    shouldShowBottomSheet.value = !shouldShowBottomSheet.value
                }
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { painter ->
                /*
                Bottom nav using an image for Profile icon, hence need to set the tint as Color.Unspecified
                Otherwise, the image won't be displayed properly as it's overlapped with the default tint color
                 */
                if (tab.options.title.equals("Profile", true)) {
                    Icon(
                        painter,
                        contentDescription = tab.options.title,
                        modifier = Modifier.size(32.dp),
                        tint = Color.Unspecified
                    )
                } else {
                    Icon(
                        painter,
                        contentDescription = tab.options.title,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    )
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}

expect val billabongFontFamily: FontFamily