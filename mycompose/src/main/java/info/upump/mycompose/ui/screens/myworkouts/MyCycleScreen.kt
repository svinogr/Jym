package info.upump.mycompose.ui.screens.myworkouts


import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.CycleItemCard

@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("UnrememberedMutableState")
@Composable
fun MyCycleScreen(navHostController: NavHostController, paddingValues: PaddingValues) {

    val listState = rememberLazyListState()
    val cycleVM: CycleVM = viewModel()
    val listCycle by cycleVM.cycles.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        LazyColumn(
            state = listState
        ) {

            itemsIndexed(listCycle) { _, it ->
                CycleItemCard(cycle = it, navHostController)
            }
        }

        val fabVisibility by remember {
            derivedStateOf {
                listState.isScrollInProgress != true
            }
        }

        val density = LocalDensity.current
        AnimatedVisibility(modifier = Modifier
            .padding(end = 16.dp, bottom = 16.dp)
            .align(Alignment.BottomEnd),
            //visible = fabVisibility,
            visible = true,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { 40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            FloatingActionButton(
                onClick = {navHostController.navigate(NavigationItem.CreateEditeCycleNavigationItem.routeWithId(0))},
                shape = CircleShape,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.fp_ic_action_add),
                    contentDescription = "add"
                )
            }
        }

        LaunchedEffect(key1 = true) {
            cycleVM.getAllPersonal()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewCycleScreen() {
    MyCycleScreen(navHostController = NavHostController(LocalContext.current), paddingValues = PaddingValues(16.dp))
}

