package uvg.edu.myapp.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uvg.edu.myapp.additionals.factory.sharedViewModel
import uvg.edu.myapp.additionals.injection.MyApp
import uvg.edu.myapp.mainapp.presentation.MainViewModel
import uvg.edu.myapp.mainapp.presentation.TestScreen
import uvg.edu.myapp.navigation.models.Graph
import uvg.edu.myapp.navigation.models.MainScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        route = Graph.Main,
        startDestination = MainScreen.Home.route,
        modifier = modifier
    ) {
        composable(route = MainScreen.Home.route) {
            val mainViewModel = it.sharedViewModel<MainViewModel>(navController){
                MainViewModel(MyApp.appModule.authRepository)
            }
            TestScreen(screenTitle = "PRINCIPAL", mainViewModel = mainViewModel)
        }

        composable(route = MainScreen.Activity.route) {
            val mainViewModel = it.sharedViewModel<MainViewModel>(navController){
                MainViewModel(MyApp.appModule.authRepository)
            }
            TestScreen(screenTitle = "ACTIVIDAD", mainViewModel = mainViewModel)
        }

        composable(route = MainScreen.Achievements.route) {
            val mainViewModel = it.sharedViewModel<MainViewModel>(navController){
                MainViewModel(MyApp.appModule.authRepository)
            }
            TestScreen(screenTitle = "LOGROS", mainViewModel = mainViewModel)
        }

        composable(route = MainScreen.Shop.route) {
            val mainViewModel = it.sharedViewModel<MainViewModel>(navController){
                MainViewModel(MyApp.appModule.authRepository)
            }
            TestScreen(screenTitle = "TIENDA", mainViewModel = mainViewModel)
        }

        composable(route = MainScreen.Profile.route) {
            val mainViewModel = it.sharedViewModel<MainViewModel>(navController){
                MainViewModel(MyApp.appModule.authRepository)
            }
            TestScreen(screenTitle = "PERFIL", mainViewModel = mainViewModel)
        }
    }
}