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
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.item.SetsItemCard
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustom
import info.upump.mycompose.ui.screens.screenscomponents.screen.DividerCustomDef

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
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = listState
    ) {
        itemsIndexed(list, key = { index, item -> item.id }) { index, it ->
            Column(modifier = Modifier) {
                SetsItemCard(it, index) {}
            }
            if (index < list.size - 1) {
                DividerCustomDef()
            }
        }
    }
}
