package se.grayson.sfibuddy.presentation.home

import androidx.navigation.NavHostController
import se.grayson.sfibuddy.presentation.home.AppDestinations.requireLogin

class AppNavActions(
    private val navController: NavHostController
) {

    val popBackStack: (route: String) -> Unit = {
        navController.popBackStack(it, false)
    }

    val navigateUp: () -> Unit = {
        if (!navController.navigateUp()) {
            navigate(AppDestinations.HOME_ROUTE)
        }
    }

    /**
     * Navigate to a route.
     *
     * @return false if the route is not found or not allowed
     */
    fun navigate(route: String): Boolean {
        navController.graph.findNode(route) ?: return false
        if (requireLogin(route)) {
            navController.navigate(AppDestinations.LOGIN_ROUTE)
            return false
        } else {
            navController.navigate(route)
            return true
        }
    }
}