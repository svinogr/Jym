package info.upump.mycompose.ui.screens.mainscreen

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import info.upump.mycompose.ui.screens.navigation.botomnavigation.MyBottomNavigation
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar =
        {
            MyBottomNavigation(navController)

        }
    ) {
        NavGraph(navController)
    }

}