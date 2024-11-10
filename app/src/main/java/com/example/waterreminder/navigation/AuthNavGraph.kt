package com.example.waterreminder.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.waterreminder.authentication.presentation.AuthViewModel
import com.example.waterreminder.authentication.presentation.SignInScreen
import com.example.waterreminder.authentication.presentation.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = "auth_graph",
        startDestination = "signIn"
    ) {
        composable(route = "signIn") {
            SignInScreen(
                onSignInSuccess = {
                    navController.popBackStack()
                    navController.navigate("main_graph")
                },
                onSignUpClick = {
                    navController.navigate("signUp")
                }
            )
        }

        composable(route = "signUp") {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate("main_graph") {
                        popUpTo(route = "signUp") {
                            inclusive = true
                        }
                    }
                },
                onSignInClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}