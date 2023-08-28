package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import info.upump.mycompose.models.entity.Day

@Composable
fun ImageByDay(day: Day) {
    val context = LocalContext.current

    val bitmap = Bitmap.createBitmap(1080, 1080, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(context.getColor(day.getColor()))

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "image",
        contentScale = ContentScale.Crop,
    )
}

@Preview
@Composable
fun ImageByDayPreview() {
    ImageByDay(day = Day.MONDAY)
}