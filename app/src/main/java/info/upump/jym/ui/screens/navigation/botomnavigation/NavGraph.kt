package info.upump.jym.ui.screens.navigation.botomnavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

const val NAVGRAPH_ROOT_ROUTE = "navgraphRootRoute"

@Composable
fun NavGraph(
    navHostController: NavHostController,
    appBarTitle: MutableState<String>,
    appBarStyle: MutableState<Int>,
    paddingValues: PaddingValues,
    topBarState: MutableState<Boolean>,
    bottomBarState: MutableState<Boolean>
) {
    val context = LocalContext.current
    NavHost(
        navController = navHostController,
        startDestination = MY_CYCLE_ROOT_ROUTE,
        route = NAVGRAPH_ROOT_ROUTE
    ) {
        myCycleGraph(navHostController, appBarTitle, appBarStyle,  context, paddingValues, topBarState, bottomBarState)

        defaultCycleGraph(navHostController, appBarTitle,appBarStyle,  context, paddingValues, topBarState, bottomBarState)

        profileNavGraph(navHostController,appBarTitle,appBarStyle,  context, paddingValues, topBarState, bottomBarState)
    }
}