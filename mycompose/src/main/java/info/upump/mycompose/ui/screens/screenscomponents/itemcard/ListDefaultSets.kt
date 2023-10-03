package info.upump.jym.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.jym.R
import info.upump.jym.models.entity.Sets
import info.upump.jym.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jym.ui.screens.screenscomponents.itemcard.item.ExerciseItemCard
import info.upump.jym.ui.screens.screenscomponents.itemcard.item.SetsItemCard
import info.upump.jym.ui.screens.screenscomponents.itemcard.item.SetsItemCardWithoutClick
import info.upump.jym.ui.screens.screenscomponents.screen.DividerCustom
import info.upump.jym.ui.screens.screenscomponents.screen.DividerCustomBottom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListDefaultSets(
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    list: List<Sets>,
    listState: LazyListState,
    deleteAction: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = listState
    ) {
        item() {
            EmptyItem(size = 2.dp)
        }
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->
            val state = remember {
                mutableStateOf(false)
            }

            val dismissState = rememberDismissState(confirmStateChange = { value ->
                true
            })
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(),
                background = {

                    ItemSwipeBackgroundOneIcon(dismissState = dismissState, state = state.value)
                },
                dismissContent = {

                    Column(modifier = Modifier) {
                        SetsItemCardWithoutClick(it, index) {
                        }
                        DividerCustom(dismissState, state = state.value)
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
        }
    }
}
