package info.upump.mycompose.ui.screens.screenscomponents.screen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DividerCustom(dismiseState: DismissState, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Divider(
            color = colorResource(id = R.color.colorBackgroundCardView),
            modifier = Modifier
                .width(64.dp)
                .height(1.dp)
        )
        Divider(
            color = colorResource(id = R.color.colorBackgroundChips),
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(end = 8.dp)
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DividerCustom2(dismiseState: DismissState, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {

        if (dismiseState.dismissDirection == DismissDirection.EndToStart ||
            dismiseState.dismissDirection == DismissDirection.StartToEnd
        ) {

        /*    Divider(
                thickness = 0.5.dp,
                color = colorResource(id = R.color.colorBackgroundChips),
                modifier = Modifier
                    .width(64.dp)
                    //   .fillMaxWidth()
                    .offset(y = (-0.5).dp)
            )*/
        }
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

@Preview(showBackground = true )
@Composable
fun klkl(){
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
                .padding(end = 8.dp)
        )

    }
}