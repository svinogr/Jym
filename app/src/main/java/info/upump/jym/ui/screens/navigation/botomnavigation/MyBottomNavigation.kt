package info.upump.jym.ui.screens.navigation.botomnavigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun MyBottomNavigation(navController: NavController = rememberNavController()) {
    val listScreens = listOf(
        NavigationItem.MyCycleNavigationItem,
        NavigationItem.DefaultCycleNavigationItem,
        NavigationItem.ProfileNavigationItem
    )

    BottomNavigation() {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRout = backStackEntry?.destination?.route
        Log.d("currentRout", " $currentRout")
        listScreens.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier.background(Color.White),
                selected = currentRout == item.route,
                onClick = { navController.navigate(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = stringResource(
                            id = item.title
                        )
                    )
                },

                selectedContentColor = Color.Green,
                unselectedContentColor = Color.Red
            )
        }
    }
}