package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import info.upump.mycompose.models.entity.Day
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMInterface
@Composable
fun ImageByDay(modelVM: VMInterface<Workout>) {
    val context = LocalContext.current

    val image by modelVM.img.collectAsState()

    val bitmap = Bitmap.createBitmap(1080, 1080, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(context.getColor(Day.valueOf(image).getColor()))

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "image",
        contentScale = ContentScale.Crop,
    )
}