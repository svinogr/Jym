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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Exercise
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.item.ExerciseItemCard
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustomDef
import info.upump.mycompose.ui.screens.viewmodel.workout.WorkoutDetailVM

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListDefaultExercise(
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
        item() {
            EmptyItem(size = 2.dp)
        }
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->
            Column(modifier = Modifier) {
                ExerciseItemCard(exercise = it, navHost = navHost) {
                    navHost.navigate(
                        NavigationItem.DefaultDetailExerciseNavigationItem.routeWithId(
                            it
                        )
                    )
                }
            }
            DividerCustomDef()
        }
    }
}
