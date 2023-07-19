package info.upump.mycompose.ui.screens.navigation.botomnavigation

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.MyCycleDetailScreen
import info.upump.mycompose.ui.screens.myworkouts.MyCycleScreen
import info.upump.mycompose.ui.screens.myworkouts.MyExerciseScreen
import info.upump.mycompose.ui.screens.myworkouts.MySetsScreen
import info.upump.mycompose.ui.screens.myworkouts.MyWorkoutsScreen

const val MY_CYCLE_ROOT_ROUTE = "myCycleRootRoute"

@OptIn(ExperimentalAnimationApi::class)
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
            appBarTitle.value =
                context.resources.getString(NavigationItem.MyCycleNavigationItem.title)
            topBarState.value = true
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
            arguments = listOf(navArgument("id") {
                type = NavType.LongType
            })
        ) {
            val id = it.arguments?.getLong("id")
            topBarState.value = false

            MyCycleDetailScreen(id = id!!, navHostController)

        }

    }


}
