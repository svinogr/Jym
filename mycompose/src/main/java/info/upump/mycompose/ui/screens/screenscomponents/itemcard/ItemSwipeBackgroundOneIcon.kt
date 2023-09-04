package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ItemSwipeBackgroundOneIcon(dismissState: DismissState, modifier: Modifier = Modifier) {
    val alignment = remember {
        mutableStateOf(Arrangement.End)
    }
    val color = animateColorAsState(
        targetValue =
        when (dismissState.targetValue) {
            DismissValue.Default -> colorResource(id = R.color.colorBackgroundChips)
            else -> Color.Red
        },
        animationSpec = tween(1000, easing = LinearEasing),
        label = ""
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color.value)
            .padding(0.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = alignment.value
    ) {
        val direction = dismissState.dismissDirection

        if (direction == DismissDirection.StartToEnd) {
            alignment.value = Arrangement.Start
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_24),
                " ",
                Modifier
                    .size(AssistChipDefaults.Height)

            )
        }

        if (direction == DismissDirection.EndToStart) {
            alignment.value = Arrangement.End
            Icon(
                painter = painterResource(id = R.drawable.ic_delete_24),
                " ",
                Modifier
                    .size(AssistChipDefaults.Height)
            )
        }
    }
}
