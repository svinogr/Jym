package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.item.CycleItemCard
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom2
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
        itemsIndexed(list, key ={index, item -> item.id  }) { index, it ->
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
                    ItemSwipeBackgroundOneIcon(dismissState = dismissState)
                },
                dismissContent = {
                    Column(modifier = Modifier) {
                  /*      if (index != 0) {
                            DividerCustom()
                        }*/
                 /*       if (dismissState.dismissDirection == DismissDirection.EndToStart ||
                            dismissState.dismissDirection == DismissDirection.StartToEnd) {
                            DividerCustom2(dismissState)
                        }*/

                        val action: ()->Unit = {navhost.navigate(NavigationItem.DetailCycleNavigationItem.routeWithId(it.id))}
                        CycleItemCard(it, action)
                        if (index < list.size - 1) {
                            DividerCustom(dismissState)
                        }
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )

        }
        item(){
            EmptyItem()
        }
    }
}

@Preview
@Composable
fun ComPreview() {
    val vm: CycleVM = viewModel()
    val del: (Context,String,Long) -> Unit =  vm::delete
    ListCycle(
        lazyListState = rememberLazyListState(), navhost = NavHostController(
            LocalContext.current
        ), list = listOf(
            Cycle().apply {
                title = "Program1"
                image = ""
                imageDefault = "uk1"
            },
            Cycle().apply {
                title = "Program2"
                image = ""
                imageDefault = "uk2"
            },
            Cycle().apply {
                title = "Program3"
                image = ""
                imageDefault = "uk1"
            }

        ), deleteAction = del
    )
}
