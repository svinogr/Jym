package info.upump.mycompose.ui.screens.myworkouts.cyclescreens


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleVM
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.CycleItemCard
import info.upump.mycompose.ui.screens.screenscomponents.FloatActionButtonWithState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("UnrememberedMutableState", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyCycleScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {
    val listState = rememberLazyListState()

    val cycleVM: CycleVM = viewModel()
    val listCycle by cycleVM.cycleList.collectAsState()
    val context = LocalContext.current

    Scaffold(modifier = Modifier.padding(paddingValues = paddingValues),
        floatingActionButton = {
            FloatActionButtonWithState(isVisible = listState.isScrollingUp(), R.drawable.ic_add_black_24dp) {
                navHostController.navigate(
                    NavigationItem.CreateEditeCycleNavigationItem.routeWith(0)
                )
            }
        }, content = {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(colorResource(id = R.color.colorBackgroundConstrateLayout))
            ) {

                itemsIndexed(
                    items = listCycle,  // list items from Viewmodel: val listCycle by cycleVM.cycles.collectAsState()
                ) { _, it ->
                    CycleItemCard(cycle = it, navHostController, context)
                }
            }

            LaunchedEffect(key1 = true) {
                cycleVM.getAllPersonal()
            }
        })
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewCycleScreen() {
    val m: MutableState<Boolean> =
        MutableStateFlow<Boolean>(true).asStateFlow().collectAsState() as MutableState<Boolean>
    MyCycleScreen(
        navHostController = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(16.dp)
    )
}

@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
