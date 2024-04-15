package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import ui.ProfileScreen
import util.Constant

object ProfileTab : Tab {
    @Composable
    override fun Content() {
        ProfileScreen()
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Profile"
            var icon: Painter = rememberVectorPainter(Icons.Default.AccountCircle)

            when (val resource = asyncPainterResource(Constant.MY_PROFILE_URL)) {
                is Resource.Loading -> {
                    // show default profile icon
                }

                is Resource.Success -> {
                    // update profile icon
                    icon = resource.value
                }

                is Resource.Failure -> {
                    // show default profile icon
                }
            }

            return TabOptions(
                    index = 4u,
                    title = title,
                    icon = icon
                )
        }
}