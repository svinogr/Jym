package info.upump.mycompose.ui.screens.defaultscreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AppBarDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    vm.getAllDefaultCycles()

    LazyColumn(modifier = Modifier.padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())) {
        cycles.forEach {
            item {
                CycleItemCard(cycle = it)
            }
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