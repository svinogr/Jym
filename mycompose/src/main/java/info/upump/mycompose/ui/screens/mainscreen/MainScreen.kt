package info.upump.mycompose.ui.screens.mainscreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import info.upump.mycompose.ui.screens.navigation.botomnavigation.MyBottomNavigation
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavGraph
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val appBarTitle = remember {
        mutableStateOf("")
    }
    val topBarState = remember {
        mutableStateOf(true)
    }

    val bottomBarStat = remember{
        mutableStateOf(true)
    }

    val cameraPermission =
        rememberPermissionState(permission = "android.permission.CAMERA")
    val storagePermission =
        rememberPermissionState(permission = "android.permission.READ_EXTERNAL_STORAGE")

    SideEffect {
        cameraPermission.launchPermissionRequest()
        storagePermission.launchPermissionRequest()
    }

    val density = LocalDensity.current
    Scaffold(
        bottomBar =
        {
            AnimatedVisibility(modifier = Modifier.fillMaxWidth(),
                visible = bottomBarStat.value,
                enter = slideInVertically() {
                    // Slide in from 40 dp from the top.
                    with(density) { 60.dp.roundToPx() }
                } /*+ expandHorizontally(
                    // Expand from the top.
                    expandFrom = Alignment.End
                ) */ + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() { with(density) { 60.dp.roundToPx() } }
                // + shrinkHorizontally()
            ) {
                MyBottomNavigation(navController)
            }

        },

        topBar = {
            AnimatedVisibility(modifier = Modifier.fillMaxWidth(),
                //visible = topBarState.value,
                visible = true,

                enter = slideInVertically() {
                    // Slide in from 40 dp from the top.
                    with(density) { -60.dp.roundToPx() }
                }  + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() {
                    // Slide in from 40 dp from the top.
                    with(density) { -60.dp.roundToPx() }
                }
            ) {
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    title = {
                        Text(text = appBarTitle.value,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis)
                    },
                )
            }
        },
    ) { padding ->
        NavGraph(navController, appBarTitle, padding, topBarState, bottomBarStat)
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}