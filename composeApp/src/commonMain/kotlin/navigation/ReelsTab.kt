package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object ReelsTab : Tab {
    @Composable
    override fun Content() {
        Text(text = "Reels")
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Reels"
            val icon = rememberVectorPainter(Icons.Outlined.Movie)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }
}