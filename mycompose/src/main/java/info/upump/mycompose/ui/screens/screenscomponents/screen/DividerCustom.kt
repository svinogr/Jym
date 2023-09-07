package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.R

@Composable
fun DividerCustom(modifier: Modifier = Modifier) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DividerPreview() {
    DividerCustom()
}
