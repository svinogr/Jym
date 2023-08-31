package info.upump.mycompose.ui.screens.screenscomponents.screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import info.upump.mycompose.models.entity.Day

@Composable
fun ImageByDay(
    day: Day,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val bitmap = Bitmap.createBitmap(1080, 1080, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(context.getColor(day.getColor()))

    Box(modifier = modifier) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun ImageByDayPreview() {
    ImageByDay(day = Day.MONDAY)
}

@Preview
@Composable
fun ImageByDayPreview2() {
    ImageByDay(day = Day.THURSDAY)
}