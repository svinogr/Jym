package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMDetailInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.BitmapCreator

@Composable
fun <T, R> ImageForDetailScreen(modelVM: VMDetailInterface<T, R>) {
    Log.d("ImageForDetailScreen", modelVM.img.collectAsState().value)
    val context = LocalContext.current

    val imgDefault = modelVM.imgDefault.collectAsState().value
    val img = modelVM.img.collectAsState().value

    val bitmap: Bitmap
    if (!img.isBlank()) {
        bitmap = BitmapCreator.getImgBitmap(img, context)
    } else if (!imgDefault.isBlank()) {
        bitmap = BitmapCreator.getImgDefaultBitmap(imgDefault, context)
    } else {
        bitmap = BitmapCreator.getExceptionDefaultBitmap(context)
    }

    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.Crop
    )
}


@Preview
@Composable
fun ImageForDetailScreenPreview() {
    ImageForDetailScreen(modelVM = CycleDetailVM.vmOnlyForPreview)
}
