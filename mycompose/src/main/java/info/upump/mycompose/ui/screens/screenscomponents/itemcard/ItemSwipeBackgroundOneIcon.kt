package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ItemSwipeBackgroundOneIcon(dismissState: DismissState, state: Boolean = false, modifier: Modifier = Modifier) {
    val color = animateColorAsState(
        targetValue =
        when (dismissState.targetValue) {
            DismissValue.Default -> colorResource(id = R.color.colorBackgroundChips)
            else -> Color.Red
        },
        animationSpec = tween(1000, easing = LinearEasing),
        label = ""
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color.value)
            .padding(0.dp, 0.dp),
    ) {
        val isVisibleTopDivider = remember {
            mutableStateOf(false)
        }

        val alignment = remember {
            mutableStateOf(Alignment.CenterStart)
        }
        val padding = remember {
            mutableStateOf(PaddingValues(start = 12.dp))
        }

        val direction = dismissState.dismissDirection
        when (direction) {
            DismissDirection.StartToEnd -> {
                alignment.value = Alignment.CenterStart
                isVisibleTopDivider.value = true
                padding.value = PaddingValues(start = 12.dp)
            }

            DismissDirection.EndToStart -> {
                alignment.value = Alignment.CenterEnd
                isVisibleTopDivider.value = true
                padding.value = PaddingValues(end = 12.dp)
            }

            else -> isVisibleTopDivider.value = false
        }

        if (isVisibleTopDivider.value || state) Divider(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-1).dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.colorBackgroundChips)
        )

        if (!isVisibleTopDivider.value || state) Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd),
            thickness = 1.dp,
            color = colorResource(id = R.color.colorBackgroundCardView)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_delete_24),
            " ",
            Modifier
                .padding(padding.value)
                .size(AssistChipDefaults.Height)
                .align(alignment.value)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    val state = true
    val dismissState = DismissValue.DismissedToEnd
    val color = animateColorAsState(
        targetValue =
        when (dismissState) {
            DismissValue.Default -> colorResource(id = R.color.colorBackgroundChips)
            else -> Color.Red
        },
        animationSpec = tween(1000, easing = LinearEasing),
        label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color.value)
            .padding(0.dp, 0.dp),
    ) {
        val isVisibleTopDivider = remember {
            mutableStateOf(false)
        }

        val alignment = remember {
            mutableStateOf(Alignment.CenterStart)
        }
        val padding = remember {
            mutableStateOf(PaddingValues(start = 12.dp))
        }

        val direction = DismissDirection.EndToStart
        when (direction) {
            DismissDirection.StartToEnd -> {
                alignment.value = Alignment.CenterStart
                isVisibleTopDivider.value = true
                padding.value = PaddingValues(start = 12.dp)
            }

            DismissDirection.EndToStart -> {
                alignment.value = Alignment.CenterEnd
                isVisibleTopDivider.value = true
                padding.value = PaddingValues(end = 12.dp)
            }

            else -> isVisibleTopDivider.value = false
        }

        if (isVisibleTopDivider.value || state) Divider(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-1).dp),
            thickness = 1.dp,
            color = colorResource(id = R.color.colorBackgroundChips)
        )

        if (!isVisibleTopDivider.value) Divider(
            modifier = Modifier
                .fillMaxWidth().align(Alignment.BottomEnd),
            thickness = 1.dp,
            color = colorResource(id = R.color.colorBackgroundCardView)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_delete_24),
            " ",
            Modifier
                .padding(padding.value)
                .size(AssistChipDefaults.Height)
                .align(alignment.value)
        )
    }
}
