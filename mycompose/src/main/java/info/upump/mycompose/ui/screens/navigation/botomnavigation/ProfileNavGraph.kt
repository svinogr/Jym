package info.upump.mycompose.ui.screens.navigation.botomnavigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jymcompose.ui.screens.ProfileScreen.ProfileScreen
import info.upump.mycompose.ui.screens.myworkouts.MyExerciseScreen
import info.upump.mycompose.ui.screens.myworkouts.MySetsScreen
import info.upump.mycompose.ui.screens.myworkouts.MyWorkoutsScreen

const val PROFILE_ROOT_ROUT = "profileRootRout"

fun NavGraphBuilder.profileNavGraph(navHostController: NavHostController) {
    navigation(
        startDestination = NavigationItem.ProfileNavigationItem.rout,
        route = PROFILE_ROOT_ROUT
    ) {
        composable(route = NavigationItem.ProfileNavigationItem.rout) {
            ProfileScreen(navHostController)
        }
    }
}