package nexters.hyomk.dontforget.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import nexters.hyomk.domain.model.DetailAnniversary
import nexters.hyomk.dontforget.presentation.feature.create.CreateScreen
import nexters.hyomk.dontforget.presentation.feature.detail.DetailScreen
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
        composable(
            route = NavigationItem.Detail.route + "/{eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.LongType
                    defaultValue = 0
                },
            ),
        ) { navBackStackEntry ->
            DetailScreen(navController, navBackStackEntry.arguments?.getLong("eventId") ?: 0)
        }

        composable(NavigationItem.Home.route) { HomeScreen(navController) }
        composable(NavigationItem.Create.route) { CreateScreen(modifier, navController) }
        composable(
            route = NavigationItem.Edit.route + "/{anniversary}",
            arguments = listOf(
                navArgument("anniversary") {
                    type = NavType.StringType
                    defaultValue = ""
                },
            ),
        ) { navBackStackEntry ->
            val anniversaryDataJson = navBackStackEntry.arguments?.getString("anniversary")
            val anniversary = Gson().fromJson<DetailAnniversary>(anniversaryDataJson, DetailAnniversary::class.java)
            EditScreen(
                anniversary = anniversary,
                navHostController = navController,
            )
        }
    }
}
