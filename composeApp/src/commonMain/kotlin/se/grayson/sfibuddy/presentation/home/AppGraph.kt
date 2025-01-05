package se.grayson.sfibuddy.presentation.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.stringResource
import se.grayson.sfibuddy.domain.model.User
import se.grayson.sfibuddy.presentation.home.AppDestinations.topLevelRoutes
import se.grayson.sfibuddy.presentation.profile.ProfileScreen
import sfibuddy.composeapp.generated.resources.Res
import sfibuddy.composeapp.generated.resources.top_level_route_home
import sfibuddy.composeapp.generated.resources.top_level_route_profile

private const val TRANSITION_TIME = 350

@Composable
fun SFIBuddyGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    appNavActions: AppNavActions
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { route ->
                    BottomNavigationItem(
                        icon = { Icon(route.icon, contentDescription = stringResource(route.name))},
                        label = { Text(stringResource(route.name))},
                        onClick = {
                            navController.navigate(route.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        selected = currentDestination?.route == route.route
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            modifier = modifier,
            startDestination = AppDestinations.HOME_ROUTE,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(TRANSITION_TIME)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(TRANSITION_TIME)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(TRANSITION_TIME)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(TRANSITION_TIME)
                )
            }
        ) {
            composable(AppDestinations.HOME_ROUTE) {
                HomeScreen(navigateToPickPhoto = { })
            }
            composable(AppDestinations.PROFILE_ROUTE) {
                ProfileScreen(navigateToLogin = {
                })
            }
        }
    }
}


object AppDestinations {
    const val HOME_ROUTE = "home_route"
    const val IMAGE_PICK_ROUTE = "image_pick_route"
    const val WORD_DEFINITION_ROUTE = "word_definition_route"
    const val LOGIN_ROUTE = "login_route"
    const val PROFILE_ROUTE = "profile_route"

    private val requiredLoginRoutes = setOf<String>(

    )

    val topLevelRoutes = listOf(
        TopLevelRoute(Res.string.top_level_route_home, HOME_ROUTE, Icons.Filled.Home),
        TopLevelRoute(Res.string.top_level_route_profile, PROFILE_ROUTE, Icons.Filled.Person)
    )

    fun requireLogin(route: String, userInfo: User? = null): Boolean {
        return requiredLoginRoutes.contains(route) &&
                (userInfo?.username.isNullOrBlank() || userInfo?.token.isNullOrBlank())
    }
}

