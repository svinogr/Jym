package info.upump.mycompose.ui.screens.myworkouts

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DateCardWithDatePicker
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DescriptionCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ImageTitleImageTitle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditeCycleScreen(
    id: Long, navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val visibleFab by remember {
        mutableStateOf(true) // не забыть псотавить фолс для начального
    }
    val density = LocalDensity.current
    val cycleVM: CycleVM = viewModel()
    val isLoad by cycleVM.isLoading.collectAsState()

    val c = cycleVM.cycle.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        cycleVM.getCycle(id)
        if (id == 0L) {
            appBarTitle.value = context.resources.getString(R.string.cycle_dialog_create_new)
        } else {
            appBarTitle.value = c.value.title
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = visibleFab,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { 100.dp.roundToPx() }
                },
                exit = slideOutVertically {
                    with(density) { 100.dp.roundToPx() }
                }
            ) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = {
                        navHostController.navigate(
                            NavigationItem.CreateEditeCycleNavigationItem.routeWithId(0)
                        )
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fab_next),
                            contentDescription = "next"
                        )
                    }
                )
            }
        }) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = it.calculateTopPadding())
                .verticalScroll(rememberScrollState())
                .background(color = colorResource(id = R.color.colorBackgroundConstrateLayout)),
        ) {
            // of thee parts
            ImageTitleImageTitle(cycleVM)
            DateCardWithDatePicker(cycleVM)
            // description aka comment
            DescriptionCard(cycleVM)
        }
    }



    BackHandler {
        Log.d("updateVM", "save _____}")
        cycleVM.saveCycle()

        navHostController.navigateUp()
    }
}

/*@RequiresApi(Build.VERSION_CODES.P)*/
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateEditeCycleScreen() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    CreateEditeCycleScreen(
        id = 1L,
        navHostController = NavHostController(LocalContext.current),
        PaddingValues(20.dp),
        m
    )
}



