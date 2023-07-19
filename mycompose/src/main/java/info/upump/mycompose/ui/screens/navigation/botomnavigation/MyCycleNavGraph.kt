package info.upump.mycompose.ui.screens.navigation.botomnavigation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import info.upump.mycompose.ui.screens.myworkouts.MyCycleDetailScreen
import info.upump.mycompose.ui.screens.myworkouts.MyCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.MyExerciseScreen
import info.upump.mycompose.ui.screens.myworkouts.MySetsScreen
import info.upump.mycompose.ui.screens.myworkouts.MyWorkoutsScreen

const val MY_CYCLE_ROOT_ROUTE = "myCycleRootRoute"

fun NavGraphBuilder.myCycleGraph(
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    context: Context,
    paddingValues: PaddingValues,
    topBarState: MutableState<Boolean>
) {
    navigation(
        startDestination = NavigationItem.MyCycleNavigationItem.route,
        route = MY_CYCLE_ROOT_ROUTE
    ) {
        composable(route = NavigationItem.MyCycleNavigationItem.route) {
            MyCycleScreen(navHostController, paddingValues)
            appBarTitle.value = context.resources.getString(NavigationItem.MyCycleNavigationItem.title)
        }

        composable(route = NavigationItem.MyWorkoutNavigationItem.route) {
            MyWorkoutsScreen(navHostController)
        }

        composable(route = NavigationItem.MyExerciseNavigationItem.route) {
            MyExerciseScreen(navHostController)
        }

        composable(route = NavigationItem.MySetsNavigationItem.route) {
            MySetsScreen(navHostController)
        }
        composable(
            route = NavigationItem.DetailCycleNavigationItem.route,
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
            })
        ) {
            val id =  it.arguments?.getLong("id")

            topBarState.value = false
            MyCycleDetailScreen(id = id!!, navHostController)
        }
    }
}