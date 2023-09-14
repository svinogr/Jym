package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom

@Composable
fun ListItemDefaultsCycle(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    list: List<Cycle>,
    navhost: NavHostController,
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = lazyListState
    ) {
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->
            Column(modifier = Modifier) {
                val action: ()->Unit = {navhost.navigate(NavigationItem.DefaultDetailCycleNavigationItem.routeWithId(it.id))}
                CycleItemCard(it, action)
            }
            if (index < list.size - 1) {
                DividerCustom()
            }
        }
    }
}