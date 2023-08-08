package info.upump.mycompose.ui.screens.mainscreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
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
                visible = topBarState.value,
                //visible = true,
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
                } /*+ expandHorizontally(
                    // Expand from the top.
                    expandFrom = Alignment.End
                ) */ + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() {
                    // Slide in from 40 dp from the top.
                    with(density) { -60.dp.roundToPx() }
                }
            ) {
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

