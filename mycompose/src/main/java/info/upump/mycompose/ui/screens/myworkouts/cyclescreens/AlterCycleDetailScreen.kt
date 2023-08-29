package info.upump.mycompose.ui.screens.myworkouts.cyclescreens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.magnifier
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.ui.screens.mainscreen.isScrollingUp
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.FloatActionButtonWithState
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

    if (cycleVM.id.collectAsState().value == 0L) {
        appBarTitle.value = context.resources.getString(R.string.cycle_dialog_create_new)
    } else {
        appBarTitle.value = cycleVM.title.collectAsState().value
    }

    LaunchedEffect(key1 = true) {
        Log.d("AlterCycleDetailScreen", "luncEffect start")
        cycleVM.getInitialItem(id)
    }


    Scaffold(modifier = Modifier.padding(top = 0.dp),
        floatingActionButton = {
            FloatActionButtonWithState(listState.isScrollingUp(), R.drawable.ic_add_black_24dp) {

            }
        }
    ) {
        Column(modifier = Modifier.padding(top = it.calculateTopPadding())) {

        }

    }

}


@Preview
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
