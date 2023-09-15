package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.item.SetsItemCard
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListSets(
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    list: List<Sets>,
    listState: LazyListState,
    deleteAction: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = listState
    ) {
        itemsIndexed(list,  key = { index, item -> item.id }) { index, it ->
            val dismissState = rememberDismissState(confirmStateChange = {value ->
                if(value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
                    deleteAction(it.id)
                    true
                } else {
                    false
                }
            })
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                background = {
                    ItemSwipeBackgroundOneIcon(dismissState = dismissState)
                },
                dismissContent = {
                    Column(modifier = Modifier) {
                        SetsItemCard(it, index){
                            navHost.navigate(NavigationItem.EditSetsNavigationItem.routeWithId(it.id))
                        }
                    }
                    if (index < list.size - 1) {
                        DividerCustom(dismissState)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListSetsPreview() {
    val listSets = listOf<Sets>(
        Sets().apply {
            weightPast = 10.0
            weight = 12.0
            reps = 12
        },
        Sets().apply {
            weightPast = 10.0
            weight = 12.0
            reps = 12
        },
        Sets().apply {
            weightPast = 10.0
            weight = 12.0
            reps = 12
        },
        Sets().apply {
            weightPast = 10.0
            weight = 12.0
            reps = 12
        },
        Sets().apply {
            weightPast = 10.0
            weight = 12.0
            reps = 12
        },
        Sets().apply {
            weightPast = 10.0
            weight = 12.0
            reps = 12
        }
    )

    ListSets(
        navHost = NavHostController(LocalContext.current),
        list = listSets,
        listState = LazyListState(),
        deleteAction = ::println,
    )
}


