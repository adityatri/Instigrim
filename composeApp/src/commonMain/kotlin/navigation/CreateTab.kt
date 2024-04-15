package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object CreateTab : Tab {
    @Composable
    override fun Content() {
        Text(text = "Create")
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Create"
            val icon = rememberVectorPainter(Icons.Outlined.AddBox)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }
}