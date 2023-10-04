package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import com.chrynan.navigation.NavigationContext

enum class AppContext(
    val icon: ImageVector,
    override val initialDestination: AppDestination
) : NavigationContext<AppDestination> {
    HOME(icon = Icons.Default.Home, initialDestination = AppDestination.Home),
    EXPLORE(icon = Icons.Default.Search, initialDestination = AppDestination.Explore),
    CREATE(icon = Icons.Outlined.AddBox, initialDestination = AppDestination.Create),
    REELS(icon = Icons.Outlined.Movie, initialDestination = AppDestination.Reels),
    PROFILE(icon = Icons.Default.AccountCircle, initialDestination = AppDestination.Profile)
}