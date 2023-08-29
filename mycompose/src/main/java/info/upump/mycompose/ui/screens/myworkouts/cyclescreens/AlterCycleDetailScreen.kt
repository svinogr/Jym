package info.upump.mycompose.ui.screens.myworkouts.cyclescreens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.mainscreen.isScrollingUp
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.FloatExtendedButtonWithState
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.DateCard
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ImageForDetailScreenWithICons
import info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen.ListWorkouts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlterCycleDetailScreen(
    id: Long,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    appBarTitle: MutableState<String>
) {
    val listState = rememberLazyListState()
    val cycleVM: CycleDetailVM = viewModel()
    val context = LocalContext.current



    LaunchedEffect(key1 = true) {
        Log.d("AlterCycleDetailScreen", "luncEffect start")
        cycleVM.getBy(id)
    }

    if (id == 0L) {
        appBarTitle.value = context.resources.getString(R.string.cycle_dialog_create_new)
    } else {
        appBarTitle.value = cycleVM.title.collectAsState().value
    }

    Scaffold(modifier = Modifier.padding(top = 0.dp),
        floatingActionButton = {
            FloatExtendedButtonWithState(
                stringResource(id = R.string.workout_dialog_create_new),
                listState.isScrollingUp(), R.drawable.ic_add_black_24dp) {
            }
        }
    ) {
        Column(modifier = Modifier
            .padding(top = it.calculateTopPadding())
            .fillMaxHeight()) {
            ImageForDetailScreenWithICons(
                image = cycleVM.img.collectAsState().value,
                defaultImage = cycleVM.imgDefault.collectAsState().value,
                actionInfo = ::println,
                actionView = ::println,
                modifier = Modifier.weight(1.5f)
            )
            DateCard(
                cycleVM.startDate.collectAsState().value,
                cycleVM.finishDate.collectAsState().value,
                Modifier.weight(0.5f)
            )

            ListWorkouts(
                list = cycleVM.subItems.collectAsState().value,
                listState , navhost = navHostController,
                Modifier.weight(4f))
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AlterCycleDetailScreenPreview() {
    val m: MutableState<String> =
        MutableStateFlow<String>(" ").asStateFlow().collectAsState() as MutableState<String>
    AlterCycleDetailScreen(
        0L, NavHostController(LocalContext.current),
        PaddingValues(),
        m
    )
}
