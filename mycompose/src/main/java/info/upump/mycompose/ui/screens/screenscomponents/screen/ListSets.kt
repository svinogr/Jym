package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Sets
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.ItemSwipeBackgroundOneIcon
import info.upump.mycompose.ui.screens.screenscomponents.itemcard.SetsItemCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListSets(
    modifier: Modifier = Modifier,
    navHost: NavHostController,
    list: List<Sets>,
    listState: LazyListState,
    actionDelete: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(R.color.colorBackgroundCardView)),
        state = listState
    ) {
        itemsIndexed(list) { index, it ->
            val dismissState = rememberDismissState()
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
                background = {
                    ItemSwipeBackgroundOneIcon(dismissState = dismissState)
                },
                dismissContent = {
                    SetsItemCard(it, navHost, index)
                    if (index < list.size) {
                        Divider()
                    }
                },
                dismissThresholds = { FractionalThreshold(0.5f) }
            )
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
        listState = LazyListState(),
        actionDelete = ::println,
    )
}


