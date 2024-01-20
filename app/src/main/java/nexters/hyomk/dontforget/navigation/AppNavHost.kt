package nexters.hyomk.dontforget.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nexters.hyomk.dontforget.presentation.feature.create.CreateScreen
import nexters.hyomk.dontforget.presentation.feature.detail.DetailScreen
import nexters.hyomk.dontforget.presentation.feature.edit.EditScreen
import nexters.hyomk.dontforget.presentation.feature.home.HomeScreen
import nexters.hyomk.dontforget.presentation.feature.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = NavigationItem.Splash.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen()
        }
        composable(NavigationItem.Home.route) { HomeScreen() }
        composable(NavigationItem.Detail.route) { DetailScreen() }
        composable(NavigationItem.Create.route) { CreateScreen() }
        composable(NavigationItem.Edit.route) { EditScreen() }
    }
}
