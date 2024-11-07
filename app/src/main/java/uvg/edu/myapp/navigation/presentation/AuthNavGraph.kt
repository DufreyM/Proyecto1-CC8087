package uvg.edu.myapp.navigation.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import uvg.edu.myapp.additionals.factory.sharedViewModel
import uvg.edu.myapp.additionals.injection.MyApp
import uvg.edu.myapp.authentication.presentation.AuthViewModel
import uvg.edu.myapp.authentication.presentation.SignInScreen
import uvg.edu.myapp.authentication.presentation.SignUpScreen
import uvg.edu.myapp.navigation.models.AuthScreen
import uvg.edu.myapp.navigation.models.Graph

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.Authentication,
        startDestination = AuthScreen.SignIn.route
    ) {
        composable(route = AuthScreen.SignIn.route) {
            val authViewModel = it.sharedViewModel<AuthViewModel>(navController){
                AuthViewModel(MyApp.appModule.authRepository)
            }
            SignInScreen(
                authViewModel = authViewModel,
                onSignInSuccess = {
                    navController.popBackStack()
                    navController.navigate(Graph.Main)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                }
            )
        }

        composable(route = AuthScreen.SignUp.route) {
            val authViewModel = it.sharedViewModel<AuthViewModel>(navController){
                AuthViewModel(MyApp.appModule.authRepository)
            }
            SignUpScreen(
                authViewModel = authViewModel,
                onSignUpSuccess = {
                    navController.popBackStack()
                    navController.navigate(Graph.Main)
                },
                onSignInClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}