package info.upump.mycompose.ui.screens.screenscomponents.itemcard

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import info.upump.mycompose.ui.screens.myworkoutsscreens.viewmodel.cycle.CycleDetailVM

@Composable
fun ItemImage(
    modifier: Modifier = Modifier,
    image: String,
    defaultImage: String,
    action: () -> Unit
) {
    val context = LocalContext.current
    val bitmap: Bitmap
    val k: Uri
    if (!image.isBlank()) {
     //   bitmap = BitmapCreator.getImgBitmap(image, context)
        k = Uri.parse(image)

    } else if (!defaultImage.isBlank()) {
       // bitmap = BitmapCreator.getImgDefaultBitmap(defaultImage, context)
        k = Uri.parse("android.resource://info.upump.mycompose/drawable/$defaultImage")
    } else {
       // bitmap = BitmapCreator.getExceptionDefaultBitmap(context)
        k = Uri.parse("android.resource://info.upump.mycompose/drawable/logo_upump_round")
    }
/*
    Image(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                action()
            },
        contentScale = ContentScale.Crop,
        bitmap = bitmap.asImageBitmap(), contentDescription = "image"
    )*/
    val b =
    AsyncImage(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                action()
            },
        contentScale = ContentScale.Crop,
        model = k, contentDescription = "image"
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

