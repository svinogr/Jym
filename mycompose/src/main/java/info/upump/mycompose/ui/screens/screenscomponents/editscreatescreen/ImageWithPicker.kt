package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import info.upump.mycompose.models.entity.Cycle
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMInterface

@Preview(showBackground = true)
@Composable
fun ImageWithPickerPreview() {
    val cycleVM = CycleVM.vmOnlyForPreview
    ImageWithPicker(cycleVM)
}
@Composable
fun ImageWithPicker(cycleVM: VMInterface<Cycle>) {
    val context = LocalContext.current

    val image by cycleVM.imgOption.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        cycleVM.updateImgage(it.toString())
    }


    Image(modifier = Modifier
            .clickable {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
        bitmap = getImagePicker(image,  context).asImageBitmap(),
        contentDescription = "image",
        contentScale = ContentScale.Crop,
    )
}

private fun getImagePicker(image: String, context: Context): Bitmap {
    var bitmap: Bitmap
    val name = context.packageName

    var source: ImageDecoder.Source
    try {
        if (!image.isBlank()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                source = ImageDecoder.createSource(context.contentResolver, Uri.parse(image))
                bitmap = ImageDecoder.decodeBitmap(source)
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    Uri.parse(image)
                );
            }

        } else {
            val id = context.resources.getIdentifier("drew", "drawable", name)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                source = ImageDecoder.createSource(context.resources, id)
                bitmap = ImageDecoder.decodeBitmap(source)
            } else {
                bitmap = BitmapFactory.decodeResource(context.resources, id);
            }
        }
    } catch (e: NullPointerException) {
        val id = context.resources.getIdentifier("drew", "drawable", name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            source = ImageDecoder.createSource(context.resources, id)
            bitmap = ImageDecoder.decodeBitmap(source)
        } else {
            bitmap = BitmapFactory.decodeResource(context.resources, id);
        }
    }
    return bitmap
}