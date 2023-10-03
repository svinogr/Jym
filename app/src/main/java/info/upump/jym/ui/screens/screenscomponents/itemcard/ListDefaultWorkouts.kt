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
import info.upump.jym.models.entity.Workout
import info.upump.jym.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jym.ui.screens.screenscomponents.itemcard.item.WorkoutItemCard
import info.upump.jym.ui.screens.screenscomponents.screen.DividerCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListItemDefaultsWorkouts(
    list: List<Workout>,
    lazyListState: LazyListState,
    navhost: NavHostController,
    modifier: Modifier = Modifier,
) {
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
            val action: () -> Unit =
                {
                    state.value = true
                    navhost.navigate(
                        NavigationItem.DefaultDetailWorkoutNavigationItem.routeWithId(
                            it.id
                        )
                    )
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
                        WorkoutItemCard(it, action)
                        DividerCustom(dismissState, state = state.value)
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
        }
    }
}
