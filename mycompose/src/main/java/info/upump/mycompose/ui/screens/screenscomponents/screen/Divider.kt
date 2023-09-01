package info.upump.mycompose.ui.screens.screenscomponents.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Divider(modifier: Modifier = Modifier) {
    androidx.compose.material3.Divider(
        modifier = modifier.padding(start = 32.dp)
            .fillMaxWidth()
            .height(1.dp)
            .padding(end = 8.dp)
            .background(Color.Black)
    )
}

@Preview(showBackground = true)
@Composable
fun DividerPreview() {
    Divider()
}
