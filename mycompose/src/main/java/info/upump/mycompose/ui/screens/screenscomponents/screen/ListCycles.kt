package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.CycleItemCard
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.WorkoutItemCard

@Composable
fun ListCycle(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    list: List<Cycle>,
    navhost: NavHostController
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = lazyListState
    ) {
        list.forEachIndexed { index, cycle ->
            item {
                Column(modifier = Modifier) {
                    CycleItemCard(cycle, navhost)
                    if (index < list.size) {
                        Divider(
                            modifier = Modifier
                                .padding(start = 32.dp)
                                .fillMaxWidth()
                                .height(1.dp)
                                .padding(end = 8.dp)
                                .background(Color.Black)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ComPreview() {

    ListCycle(
        lazyListState = rememberLazyListState(), navhost = NavHostController(
            LocalContext.current
        ), list = listOf(
            Cycle().apply {
                title = "Program1"
                image = ""
                imageDefault = "uk1"
            },
            Cycle().apply {
                title = "Program2"
                image = ""
                imageDefault = "uk2"
            },
            Cycle().apply {
                title = "Program3"
                image = ""
                imageDefault = "uk1"
            }

        )

    )
}
