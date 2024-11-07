package uvg.edu.myapp.navigation.models

sealed class AuthScreen(val route: String) {
    data object SignUp : AuthScreen(route = "sign_up")
    data object SignIn : AuthScreen(route = "sign_in")
    data object Forgot : AuthScreen(route = "forgot")
}