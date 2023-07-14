package info.upump.mycompose.ui.screens.mainscreen

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import info.upump.mycompose.ui.screens.navigation.botomnavigation.MyBottomNavigation
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavGraph
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val appBarTitle = remember {
        mutableStateOf("")
    }
    val topBarState = remember {
        mutableStateOf(true)
    }

    Scaffold(
        bottomBar =
        {
            if (topBarState.value) {
                MyBottomNavigation(navController)
            }
        },
        topBar = {
            if (topBarState.value) {
                TopAppBar(
                    title = {
                        Text(appBarTitle.value)
                    },

                    )
            }
        }
    ) { padding ->
        NavGraph(navController, appBarTitle, padding, topBarState)
    }
}