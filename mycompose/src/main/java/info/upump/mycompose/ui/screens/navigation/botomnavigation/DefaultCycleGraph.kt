package info.upump.mycompose.ui.screens.navigation.botomnavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.upump.mycompose.ui.screens.defaultscreen.DefaultCycleScreen

const val DEFAULT_CYCLE_ROOT_ROUTE = "default_cycle_root_route"

fun NavGraphBuilder.defaultCycleGraph(navHostController: NavHostController) {
    navigation(
        startDestination = NavigationItem.DefaultCycleNavigationItem.rout,
        route = DEFAULT_CYCLE_ROOT_ROUTE
    ) {
        composable(
            route = NavigationItem.DefaultCycleNavigationItem.rout
        ) {
            DefaultCycleScreen(navHostController)
        }
    }
}