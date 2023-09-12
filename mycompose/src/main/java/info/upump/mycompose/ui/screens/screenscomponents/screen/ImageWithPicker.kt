package info.upump.mycompose.ui.screens.screenscomponents.screen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import info.upump.mycompose.ui.screens.screenscomponents.BitmapCreator

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ImageWithPickerPreview() {
   // val cycleVMCreateEdit = CycleVMCreateEdit.vmOnlyForPreview
    ImageWithPicker("", "uk", ::print)
}
@Composable
fun ImageWithPicker(image: String, defaultImage: String, updateImage: (String) -> Unit) {
    val context = LocalContext.current

     Log.d("image", "$image $defaultImage")
 //   val bitmap: Bitmap
/*
    if (!image.isBlank()) {
        bitmap = BitmapCreator.getImgBitmap(image, context)
    } else if (!defaultImage.isBlank()) {
        bitmap = BitmapCreator.getImgDefaultBitmap(defaultImage, context)
    } else {
        bitmap = BitmapCreator.getExceptionDefaultBitmap(context)
    }*/

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if(it == null) return@rememberLauncherForActivityResult
        Log.d("pers ", "$it.")
            val name = context.packageName
            context.grantUriPermission(name, it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        updateImage(it.toString())
    }
    ImageForDetailScreen(
        image = image,
        defaultImage = defaultImage,
        modifier = Modifier.clickable {
            launcher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo
                )
            )
        }
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