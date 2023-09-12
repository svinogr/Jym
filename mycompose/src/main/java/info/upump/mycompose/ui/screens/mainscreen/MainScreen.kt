package info.upump.mycompose.ui.screens.mainscreen

import android.Manifest
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import info.upump.mycompose.ui.screens.navigation.botomnavigation.MyBottomNavigation
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavGraph
import info.upump.mycompose.ui.theme.MyOutlineTextTitleLabel20Text
import info.upump.mycompose.ui.theme.MyTextTitleLabel20
const val WHITE_STYLE = 1
const val DEFAULT_STYLE = 0
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

    val bottomBarStat = remember {
        mutableStateOf(true)
    }

    val appBarStyle = remember {
        mutableStateOf(DEFAULT_STYLE)
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
                } + fadeIn(
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
                        Text(
                            text = appBarTitle.value,
                            style = if (appBarStyle.value == WHITE_STYLE) MyOutlineTextTitleLabel20Text else MyTextTitleLabel20  ,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                )
            }
        },
    ) { padding ->
        NavGraph(navController, appBarTitle, appBarStyle, padding, topBarState, bottomBarStat)
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}