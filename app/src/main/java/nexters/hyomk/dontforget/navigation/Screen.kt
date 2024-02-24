package nexters.hyomk.dontforget.navigation

enum class Screen {
    SPLASH,
    HOME,
    DETAIL,
    EDIT,
    CREATE,
}

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Home : NavigationItem(Screen.HOME.name)
    object Detail : NavigationItem(Screen.DETAIL.name)
    object Edit : NavigationItem(Screen.EDIT.name)
    object Create : NavigationItem(Screen.CREATE.name)
}
