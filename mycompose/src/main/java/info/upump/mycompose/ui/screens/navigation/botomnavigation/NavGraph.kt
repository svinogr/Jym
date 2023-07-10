package info.upump.mycompose.ui.screens.navigation.botomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

const val NAVGRAPH_ROOT_ROUTE = "navgraphRootRoute"
@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = MY_CYCLE_ROOT_ROUTE,
        route = NAVGRAPH_ROOT_ROUTE
    ) {
        myCycleGraph(navHostController)

        defaultCycleGraph(navHostController)

        profileNavGraph(navHostController)
    }
}
