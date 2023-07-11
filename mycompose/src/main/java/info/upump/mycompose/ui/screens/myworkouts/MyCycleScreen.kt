package info.upump.mycompose.ui.screens.myworkouts


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.upump.mycompose.R
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.screenscomponents.CycleItemCard
import java.util.Date

@SuppressLint("UnrememberedMutableState")
@Composable
fun MyCycleScreen(navHostController: NavHostController, paddingValues: PaddingValues) {
    val listState = rememberLazyListState()
     val fabVisibility by derivedStateOf {
          listState.firstVisibleItemIndex == 0
      }

    Box() {
        LazyColumn(modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding()),
        state = listState) {
            item {
                for (i in 1..20) {
                    val cycle = Cycle(title = "pipec $i")
                    cycle.image = "uk1"
                    cycle.startDate = Date()
                    cycle.finishDate = Date()
                    CycleItemCard(cycle = cycle)
                }
            }
        }

        var density = LocalDensity.current
        AnimatedVisibility(
            visible = fabVisibility,
            enter = slideInVertically {
                with(density) { 40.dp.roundToPx() }
            } + fadeIn(),
            exit = fadeOut(
                animationSpec = keyframes {
                    this.durationMillis = 120
                }
            )
        ) {
            FloatingActionButton(
                onClick = {},
                Modifier
                    .padding(end = 16.dp, bottom = paddingValues.calculateBottomPadding() + 16.dp)
                    .align(Alignment.BottomEnd),
                shape = CircleShape,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.fp_ic_action_add),
                    contentDescription = "add"
                )
            }
        }
    }

    /*   AddPaymentFab(
           modifier = Modifier
              //.align(Alignment.BottomEnd)
               .padding(bottom = 40.dp),
           isVisibleBecauseOfScrolling = fabVisibility
       )*/

}


@Composable
private fun AddPaymentFab(
    modifier: Modifier,
    isVisibleBecauseOfScrolling: Boolean,
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisibleBecauseOfScrolling,
        enter = slideInVertically {
            with(density) { 40.dp.roundToPx() }
        } + fadeIn(),
        exit = fadeOut(
            animationSpec = keyframes {
                this.durationMillis = 120
            }
        )
    ) {
        ExtendedFloatingActionButton(
            text = { Text(text = "Add Payment") },
            onClick = { },
            icon = { Icon(Icons.Filled.Add, "Add Payment") }
        )
    }
}