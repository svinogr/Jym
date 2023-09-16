package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.item.CycleItemCard
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom
import info.upump.mycompose.ui.screens.viewmodel.cycle.CycleVM

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListCycle(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    list: List<Cycle>,
    navhost: NavHostController,
    deleteAction: (Context, String, Long) -> Unit,
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = lazyListState
    ) {
        item() {
            EmptyItem(size = 2.dp)
        }
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->
            val state = remember {
                mutableStateOf(false)
            }
            val dismissState = rememberDismissState(confirmStateChange = { value ->
                if (value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
                    deleteAction(context, it.image, it.id)
                }
                true
            })
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                background = {

                    ItemSwipeBackgroundOneIcon(dismissState = dismissState, state = state.value)
                },
                dismissContent = {

                    Column(modifier = Modifier) {
                        val action: () -> Unit = {
                            state.value = true
                            navhost.navigate(
                                NavigationItem.DetailCycleNavigationItem.routeWithId(it.id)
                            )
                        }
                        CycleItemCard(it, action)
                        DividerCustom(dismissState, state = state.value)
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
        }

        item() {
            EmptyItem()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ComPreview() {
 //   val vm: CycleVM = viewModel()
 //   val del: (Context, String, Long) -> Unit = vm::delete
    val list = listOf(
        Cycle().apply {
            id = 1
            title = "Program1"
            image = ""
            imageDefault = "uk1"
        },
        Cycle().apply {
            id = 13
            title = "Program2"
            image = ""
            imageDefault = "uk2"
        },
        Cycle().apply {
            id = 15
            title = "Program3"
            image = ""
            imageDefault = "uk1"
        }

    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = LazyListState()
    ) {
        item() {
            EmptyItem(size = 2.dp)
        }
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->
            val state = remember {
                mutableStateOf(false)
            }
            val dismissState = rememberDismissState(confirmStateChange = { value ->
                if (value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
                    // deleteAction(context, it.image, it.id)
                }
                true
            })
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                background = {

                    ItemSwipeBackgroundOneIcon(dismissState = dismissState, state = state.value)
                },
                dismissContent = {

                    Column(modifier = Modifier) {
                        val action: () -> Unit = {
                            state.value = true
                            /*  navhost.navigate(
                                  NavigationItem . DetailCycleNavigationItem . routeWithId (it.id)
                              )*/
                        }
                        CycleItemCard(it, action)
                        DividerCustom(dismissState, state = state.value)
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
        }

        item() {
            EmptyItem()
        }
    }


}
