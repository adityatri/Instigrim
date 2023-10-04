package navigation

sealed class AppDestination {
    object Home : AppDestination()
    object Explore : AppDestination()
    object Create : AppDestination()
    object Reels : AppDestination()
    object Profile : AppDestination()
}