package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.item.WorkoutItemCard
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustomDef

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
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->

            Column(modifier = Modifier) {
                val action: ()-> Unit =
                    {
                        navhost.navigate(NavigationItem.DefaultDetailWorkoutNavigationItem.routeWithId(it.id))
                    }
                WorkoutItemCard(workout = it, action )
            }
            if (index < list.size - 1) {
                DividerCustomDef()
            }
        }
    }
}
