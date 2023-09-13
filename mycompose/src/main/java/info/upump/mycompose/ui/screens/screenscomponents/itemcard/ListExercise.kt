package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import android.annotation.SuppressLint
import android.util.Log
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
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.ui.screens.myworkoutsscreens.viewmodel.workout.WorkoutDetailVM
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListExercise(
    list: List<Exercise>,
    lazyListState: LazyListState,
    navHost: NavHostController,
    modifier: Modifier = Modifier,
    actionDel: (Long) -> Unit,
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
                    Log.d("exe", "ListExercise: ${it.exerciseDescription!!.title}")
                    actionDel(it.id)
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
                        ExerciseItemCard(exercise = it, navHost = navHost) {
                            navHost.navigate(
                                NavigationItem.DetailExerciseNavigationItem.routeWithId(
                                    it
                                )
                            )
                        }
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
            if (index < list.size - 1) {
                DividerCustom()
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListExercisePreview() {
    val nav = NavHostController(LocalContext.current)
    ListExercise(
        WorkoutDetailVM.vmOnlyForPreview.subItems.collectAsState().value,
        LazyListState(),
        nav
    ) {}
}