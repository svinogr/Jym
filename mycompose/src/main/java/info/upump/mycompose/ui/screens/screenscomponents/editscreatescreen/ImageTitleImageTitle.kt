package info.upump.mycompose.ui.screens.screenscomponents.editscreatescreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import info.upump.mycompose.models.entity.Entity
import info.upump.mycompose.models.entity.Workout
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.CycleVM
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.VMInterface
import info.upump.mycompose.ui.screens.myworkouts.viewmodel.WorkoutVM

@Composable
fun <T: Entity> ImageTitleImageTitle(modelVM: VMInterface<T>, content: @Composable() ()-> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

           content()
           LabelTitleForImage(modelVM)
        }

        CardTitle(modelVM)
    }
}

@Preview(showBackground = false)
@Composable
fun ImageTitleImageTitlePreviewCycle() {
    val cycle = CycleVM.vmOnlyForPreview
    ImageTitleImageTitle(cycle){
        ImageWithPicker(cycle)
    }
}
/*

@Preview(showBackground = false)
@Composable
fun ImageTitleImageTitlePreviewWorkout() {
    val workout = WorkoutVM.vmOnlyForPreview
    ImageTitleImageTitle(workout) {
        ImageByDay(workout)
    }
}

@Composable
fun ImageByDay(workout: VMInterface<Workout>) {
    val context = LocalContext.current

    val image by workout.imgOption.collectAsState()

    val bitmap = Bitmap.createBitmap(1920, 1080, Bitmap.Config.ARGB_8888)
   // bitmap.eraseColor(context.getColor(workout.day!!.getColor()))
    bitmap.eraseColor(context.getColor(image))
}

private fun getImageByDaY(image: String, context: Context): Bitmap {
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
}*/
