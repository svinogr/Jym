package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.SetsItemCard

@Composable
fun ListSets(
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    list: List<Sets>,
    listState: LazyListState
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = listState
    ) {
        list.forEachIndexed { index, it ->
            item {
                SetsItemCard(it, navHost, index)
                if (index < list.size) {
                    Divider()
                }
            }
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
        listState = LazyListState()
    )
}