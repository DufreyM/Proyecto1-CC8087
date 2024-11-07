package uvg.edu.myapp.navigation.models

import uvg.edu.myapp.R

sealed class MainScreen(
    val route: String,
    val icon: Int
) {
    data object Home : MainScreen(
        route = "home",
        icon = R.drawable.huella_bn_icon
    )

    data object Achievements : MainScreen(
        route = "achievements",
        icon = R.drawable.trofeo_bn_icon
    )

    data object Shop : MainScreen(
        route = "shop",
        icon = R.drawable.tienda_bn_icon
    )

    data object Activity : MainScreen(
        route = "activity",
        icon = R.drawable.actividad_bn_icon
    )

    data object Profile : MainScreen(
        route = "profile",
        icon = R.drawable.perfil_bn_icon
    )
}