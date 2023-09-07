package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.cycle.CycleDetailVM
import info.upump.mycompose.ui.screens.screenscomponents.BitmapCreator
import info.upump.mycompose.ui.screens.screenscomponents.screen.ImageForDetailScreen

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    image: String,
    defaultImage: String,
    action: () -> Unit
) {
    val context = LocalContext.current
    val bitmap: Bitmap
    if (!image.isBlank()) {
        bitmap = BitmapCreator.getImgBitmap(image, context)
    } else if (!defaultImage.isBlank()) {
        bitmap = BitmapCreator.getImgDefaultBitmap(defaultImage, context)
    } else {
        bitmap = BitmapCreator.getExceptionDefaultBitmap(context)
    }

    Image(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                action()
            },
        contentScale = ContentScale.Crop,
        bitmap = bitmap.asImageBitmap(), contentDescription = "image"
    )
}

@Preview(showBackground = true)
@Composable
fun ItemImagePreview() {
    ItemImage(
        image = CycleDetailVM.vmOnlyForPreview.img.collectAsState().value,
        defaultImage =
        CycleDetailVM.vmOnlyForPreview.imgDefault.collectAsState().value){}

}

