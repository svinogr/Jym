package info.upump.mycompose.ui.screens.navigation.botomnavigation

import android.content.Context
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import info.upump.mycompose.ui.screens.myworkouts.ActionState
import info.upump.mycompose.ui.screens.myworkouts.cyclescreens.CreateEditeCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.CreateEditeWorkoutScreen
import info.upump.mycompose.ui.screens.myworkouts.cyclescreens.MyCycleDetailScreen
import info.upump.mycompose.ui.screens.myworkouts.cyclescreens.MyCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.MyExerciseDetailScreen
import info.upump.mycompose.ui.screens.myworkouts.MySetsCreateScreen
import info.upump.mycompose.ui.screens.myworkouts.MySetsDetailScreen
import info.upump.mycompose.ui.screens.myworkouts.MyWorkoutDetailScreen

const val MY_CYCLE_ROOT_ROUTE = "myCycleRootRoute"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.myCycleGraph(
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    context: Context,
    paddingValues: PaddingValues,
    topBarState: MutableState<Boolean>,
    bottomBarState: MutableState<Boolean>
) {
    navigation(
        startDestination = NavigationItem.MyCycleNavigationItem.route,
        route = MY_CYCLE_ROOT_ROUTE
    ) {

        composable(route = NavigationItem.MyCycleNavigationItem.route) {

            MyCycleScreen(navHostController, paddingValues)
            appBarTitle.value =
                context.resources.getString(NavigationItem.MyCycleNavigationItem.title)
            topBarState.value = true
            bottomBarState.value = true
        }

        composable(
            route = NavigationItem.DetailCycleNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            topBarState.value = false
            bottomBarState.value = false
            MyCycleDetailScreen(id = id!!, navHostController, paddingValues, appBarTitle)

        }

        composable(
            route = NavigationItem.DetailWorkoutNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            //topBarState.value = false он уже должен был быть убран
            Log.d("TAG", "id = $id")

            MyWorkoutDetailScreen(id = id!!, navHostController,paddingValues, appBarTitle)

        }

        composable(
            route = NavigationItem.DetailExerciseNavigationItem.route,
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            //topBarState.value = false он уже должен был быть убран
            Log.d("TAG", "id = $id")

            MyExerciseDetailScreen(id = id!!, navHostController, paddingValues, appBarTitle)
        }

        composable(
            route = NavigationItem.CreateSetsNavigationItem.route,
            arguments = listOf(navArgument("parentId"){
                type = NavType.LongType
            }),
        ) {
            //topBarState.value = false он уже должен был быть убран
            val parentId = it.arguments?.getLong("parentId")

            MySetsCreateScreen(parentId!!, navHostController, paddingValues, appBarTitle)
        }

        composable(
            route = NavigationItem.DetailSetDetailNavigationItem.route,
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
            }),
        ) {

            //topBarState.value = false он уже должен был быть убран
            val id = it.arguments?.getLong("id")
            Log.d("TAG", "id = $id")

            MySetsDetailScreen(id!!, navHostController, paddingValues, appBarTitle)
        }
        composable(
            route = NavigationItem.CreateEditeCycleNavigationItem.route,
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
            }),
        ) {

            //topBarState.value = false он уже должен был быть убран
            val id = it.arguments?.getLong("id")
            Log.d("TAG", "id = $id")
            bottomBarState.value = false
            val action = if (id == 0L) {ActionState.CREATE} else{ActionState.UPDATE}
            CreateEditeCycleScreen(id!!, navHostController, paddingValues, appBarTitle, action)
        }

        composable(
            route = NavigationItem.CreateWorkoutNavigationItem.route,
            arguments = listOf(navArgument("parentId"){
                type = NavType.LongType
            }),
        ) {
            //topBarState.value = false он уже должен был быть убран
            val parentId = it.arguments?.getLong("parentId")
            Log.d("TAG", "id = $id")
          //  bottomBarState.value = false и так отключен ранее
            CreateEditeWorkoutScreen(0, parentId!!, navHostController, paddingValues, appBarTitle)
        }
    }
}
