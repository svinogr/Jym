package info.upump.mycompose.ui.screens.defaultscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.screenscomponents.CycleItemCard
import java.util.Date


@Composable
fun DefaultCycleScreen(navHostController: NavHostController) {
    val vm: CycleVM = viewModel()
    val cycles by vm.cycles.collectAsState()
    vm.getAllDefaultCycles()

    LazyColumn {
        cycles.forEach {
            item {
                CycleItemCard(cycle = it)
            }
        }
    }
}