package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DividerCustom(dismissState: DismissState, modifier: Modifier = Modifier) {
    val direction = dismissState.dismissDirection
    val color = remember {
        mutableStateOf(R.color.colorBackgroundCardView)
    }

    if (direction == DismissDirection.StartToEnd ||
        direction == DismissDirection.EndToStart
    ) {
        color.value = R.color.colorBackgroundChips
    } else {
        color.value = R.color.colorBackgroundCardView
    }
    Row(modifier = modifier) {
        Divider(
            color = colorResource(id = color.value),
            modifier = Modifier
                .width(64.dp)
                .height(1.dp)
        )
        Divider(
            color = colorResource(id = R.color.colorBackgroundChips),
            modifier = Modifier
               .fillMaxWidth().padding(end = 8.dp)
                .height(1.dp),
        )


    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DividerPreview() {
    val dismissState = rememberDismissState(confirmStateChange = { value ->
        if (value == DismissValue.DismissedToEnd || value == DismissValue.DismissedToStart) {
        }

        true
    })
    dismissState.performDrag(10f)
    DividerCustom(dismissState)
}

@Preview(showBackground = true)
@Composable
fun klkl() {
    Divider(
        //    color = colorResource(id = R.color.colorBackgroundChips),
        color = Color.Red,
        thickness = 0.5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 64.dp, end = 8.dp)

    )
}

@Composable
fun DividerCustomDef(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {

        Divider(
            thickness = 1.dp,
            color = colorResource(id = R.color.colorBackgroundChips),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 64.dp, end = 8.dp)
        )

    }
}