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
import info.upump.mycompose.ui.screens.defaultscreen.DefaultCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.screens.cyclescreens.AlterCycleDetailScreenM3

const val DEFAULT_CYCLE_ROOT_ROUTE = "default_cycle_root_route"

fun NavGraphBuilder.defaultCycleGraph(
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    context: Context,
    paddingValues: PaddingValues,
    topBarState: MutableState<Boolean>
) {
    navigation(
        startDestination = NavigationItem.DefaultCycleNavigationItem.route,
        route = DEFAULT_CYCLE_ROOT_ROUTE
    ) {
        composable(
            route = NavigationItem.DefaultCycleNavigationItem.route
        ) {
            DefaultCycleScreen(navHostController, paddingValues)
            appBarTitle.value = context.resources.getString(NavigationItem.DefaultCycleNavigationItem.title)
            topBarState.value = true
        }

        composable(
            route = NavigationItem.DefaultDetailCycleNavigationItem.route,
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
            })
        ) {
            val id =  it.arguments?.getLong("id")

           AlterCycleDetailScreenM3(id = id!!, navHostController, paddingValues, appBarTitle)
            topBarState.value = false
            appBarTitle.value = context.resources.getString(NavigationItem.DefaultCycleNavigationItem.title)
        }

    }
}