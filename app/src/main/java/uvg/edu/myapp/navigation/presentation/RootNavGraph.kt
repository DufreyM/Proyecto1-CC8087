package uvg.edu.myapp.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uvg.edu.myapp.mainapp.presentation.MainAppView
import uvg.edu.myapp.mainapp.presentation.TestScreen
import uvg.edu.myapp.navigation.models.Graph

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        route = Graph.Root,
        startDestination = "splash"
    ) {
        composable(route = "splash") {
            TestScreen(screenTitle = "SPLASH") {
                navController.navigate(route = Graph.Authentication) {
                    popUpTo(route = "splash") {
                        inclusive = true
                    }
                }
            }
        }

        authNavGraph(navController = navController)

        composable(route = Graph.Main) {
            MainAppView()
        }
    }
}