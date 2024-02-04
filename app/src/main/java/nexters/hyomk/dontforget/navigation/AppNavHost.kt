package nexters.hyomk.dontforget.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nexters.hyomk.dontforget.presentation.feature.create.CreateScreen
import nexters.hyomk.dontforget.presentation.feature.edit.EditScreen
import nexters.hyomk.dontforget.presentation.feature.home.HomeScreen
import nexters.hyomk.dontforget.presentation.feature.splash.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Splash.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen(navHostController = navController)
        }
        composable(NavigationItem.Home.route) { HomeScreen(navController) }
        composable(NavigationItem.Create.route) { CreateScreen(modifier, navController) }
        composable(NavigationItem.Edit.route) { EditScreen() }
    }
}
