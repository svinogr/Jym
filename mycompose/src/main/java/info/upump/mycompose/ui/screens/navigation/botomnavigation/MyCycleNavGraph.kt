package info.upump.mycompose.ui.screens.navigation.botomnavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.upump.mycompose.ui.screens.myworkouts.MyCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.MyExerciseScreen
import info.upump.mycompose.ui.screens.myworkouts.MySetsScreen
import info.upump.mycompose.ui.screens.myworkouts.MyWorkoutsScreen

const val MY_CYCLE_ROOT_ROUTE = "myCycleRootRoute"

fun NavGraphBuilder.myCycleGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = NavigationItem.MyCycleNavigationItem.rout,
        route = MY_CYCLE_ROOT_ROUTE
    ) {
        composable(route = NavigationItem.MyCycleNavigationItem.rout) {
            MyCycleScreen(navHostController)
        }

        composable(route = NavigationItem.MyWorkoutNavigationItem.rout) {
            MyWorkoutsScreen(navHostController)
        }

        composable(route = NavigationItem.MyExerciseNavigationItem.rout) {
            MyExerciseScreen(navHostController)
        }

        composable(route = NavigationItem.MySetsNavigationItem.rout) {
            MySetsScreen(navHostController)
        }
    }
}