package info.upump.mycompose.ui.screens.defaultscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.screenscomponents.CycleItemCard
import java.util.Date


@Composable
fun DefaultCycleScreen(navHostController: NavHostController, paddingValues: PaddingValues) {
    val vm: CycleVM = viewModel()
    val cycles by vm.cycles.collectAsState()
    val load by vm.stateLoading.collectAsState()

    vm.getAllDefaultCycles()

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues)
    ) {

        LazyColumn() {
            cycles.forEach {
                item {
                    CycleItemCard(cycle = it, navHostController)
                }
            }
        }
        if (load) {
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
        }
    }


}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val nav = NavHostController(LocalContext.current)
    val pad = PaddingValues.Absolute()
    DefaultCycleScreen(navHostController = nav, pad)
}