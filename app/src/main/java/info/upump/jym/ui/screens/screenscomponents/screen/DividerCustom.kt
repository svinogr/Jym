package info.upump.jym.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.jym.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DividerCustom(
    dismissState: DismissState,
    modifier: Modifier = Modifier,
    state: Boolean = false
) {
    val direction = dismissState.dismissDirection
    val color = remember {
        mutableIntStateOf(R.color.colorBackgroundCardView)
    }
    val padding = remember {
        mutableStateOf(8.dp)
    }

    if (direction == DismissDirection.StartToEnd ||
        direction == DismissDirection.EndToStart || state
    ) {
        color.intValue = R.color.colorBackgroundChips
        padding.value = 0.dp
    } else {
        color.intValue = R.color.colorBackgroundCardView
        padding.value = 8.dp
    }
    Row(modifier = modifier) {
        Divider(
            color = colorResource(id = color.intValue),
            modifier = Modifier
                .width(64.dp)
                .height(1.dp)
        )
        Divider(
            color = colorResource(id = R.color.colorBackgroundChips),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = padding.value)
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
fun DividerCustomBottom(modifier: Modifier = Modifier, state: Boolean = false) {
    val padding = remember {
        mutableStateOf(PaddingValues(start = 64.dp, end = 8.dp))
    }
    if (state) {
        padding.value = PaddingValues(0.dp)
    } else {
        padding.value = PaddingValues(start = 64.dp, end = 8.dp)
    }

    Row(modifier = modifier.fillMaxWidth()) {
        Divider(
            thickness = 1.dp,
            color = colorResource(id = R.color.colorBackgroundChips),
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding.value)
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DividerCustomDefPreview(modifier: Modifier = Modifier, state: Boolean = false) {
    DividerCustomBottom(state = true)
}

@Composable
fun DividerCustomTop(modifier: Modifier = Modifier, state: Boolean = false) {
    val color = remember {
        mutableStateOf(Color.Transparent)
    }
    if (state) {
        color.value = colorResource(id = R.color.colorBackgroundChips)
    } else color.value = Color.Transparent
    Divider(
        thickness = 1.dp,
        color = color.value,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (-1).dp)
    )

}