package mvi.action

sealed interface HomeAction {
    data object ToggleLike : HomeAction
}