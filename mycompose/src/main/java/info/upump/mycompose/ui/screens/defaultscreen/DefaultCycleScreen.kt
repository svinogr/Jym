package info.upump.mycompose.ui.screens.defaultscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.ui.screens.defaultscreen.viewmodel.CycleDefaultVM
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.CycleItemCard


@Composable
fun DefaultCycleScreen(navHostController: NavHostController, paddingValues: PaddingValues) {
    val vm: CycleDefaultVM = viewModel()
    val cycles by vm.cycles.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues)
    ) {
        LazyColumn() {
            itemsIndexed(cycles) {index, it ->
                CycleItemCard(cycle = it, navHostController )
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
        }
    }

    LaunchedEffect(key1 = true, block ={
        vm.getAllDefaultCycles()
    } )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val nav = NavHostController(LocalContext.current)
    val pad = PaddingValues.Absolute()
    DefaultCycleScreen(navHostController = nav, pad)
}