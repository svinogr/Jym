package info.upump.mycompose.ui.screens.navigation.botomnavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

const val NAVGRAPH_ROOT_ROUTE = "navgraphRootRoute"
@Composable
fun NavGraph(navHostController: NavHostController, appBarTitle: MutableState<String>, paddingValues: PaddingValues) {
    val context = LocalContext.current
    NavHost(
        navController = navHostController,
        startDestination = MY_CYCLE_ROOT_ROUTE,
        route = NAVGRAPH_ROOT_ROUTE
    ) {
        myCycleGraph(navHostController, appBarTitle, context, paddingValues)

        defaultCycleGraph(navHostController, appBarTitle, context, paddingValues)

        profileNavGraph(navHostController, appBarTitle, context)
    }
}
