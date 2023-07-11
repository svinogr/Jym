package info.upump.mycompose.ui.screens.navigation.botomnavigation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
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
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    context: Context,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = NavigationItem.MyCycleNavigationItem.rout,
        route = MY_CYCLE_ROOT_ROUTE
    ) {
        composable(route = NavigationItem.MyCycleNavigationItem.rout) {
            MyCycleScreen(navHostController, paddingValues)
            appBarTitle.value = context.resources.getString(NavigationItem.MyCycleNavigationItem.title)
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