package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListWorkouts(
    list: List<Workout>,
    lazyListState: LazyListState,
    navhost: NavHostController,
    modifier: Modifier = Modifier,
    deleteAction: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = lazyListState
    ) {
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->
            val dismissState = rememberDismissState(confirmStateChange = { value ->
                if (value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
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
                        WorkoutItemCard(workout = it, navHost = navhost)
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
            if (index < list.size -1) {
                DividerCustom()
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListWorkoutsPreview() {
    val nav = NavHostController(LocalContext.current)
    ListWorkouts(
        CycleDetailVM.vmOnlyForPreview.subItems.collectAsState().value,
        LazyListState(),
        nav
    ) {}
}