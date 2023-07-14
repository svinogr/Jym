package info.upump.mycompose.ui.screens.navigation.botomnavigation

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.jymcompose.ui.screens.ProfileScreen.ProfileScreen

const val PROFILE_ROOT_ROUT = "profileRootRout"

fun NavGraphBuilder.profileNavGraph(
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    context: Context
) {
    navigation(
        startDestination = NavigationItem.ProfileNavigationItem.route,
        route = PROFILE_ROOT_ROUT
    ) {
        composable(route = NavigationItem.ProfileNavigationItem.route) {
            ProfileScreen(navHostController)
            appBarTitle.value =
            context.resources.getString(NavigationItem.ProfileNavigationItem.title)

        }
    }
}