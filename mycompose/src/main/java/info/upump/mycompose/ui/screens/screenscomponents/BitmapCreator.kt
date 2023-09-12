package info.upump.mycompose.ui.screens.screenscomponents

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.platform.LocalContext

class BitmapCreator {
    companion object {
        fun getExceptionDefaultBitmap(context: Context): Bitmap {

            var bitmap: Bitmap
            val name = context.packageName
            val source: ImageDecoder.Source
            try {
                val id = context.resources.getIdentifier("drew", "drawable", name)
                /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        source = ImageDecoder.createSource(context.resources, id)
                        bitmap = ImageDecoder.decodeBitmap(source)
                    } else {*/
                bitmap = BitmapFactory.decodeResource(context.resources, id);
                //  }
                Log.d("getExceptionDefaultBitmap", "id = $id")
            } catch (e: Exception) {
                bitmap = getExceptionDefaultBitmap(context)

            }

            return bitmap
        }

        fun getImgDefaultBitmap(imgDefault: String, context: Context): Bitmap {
            var bitmap: Bitmap
            val name = context.packageName
            val source: ImageDecoder.Source
            try {
                val id = context.resources.getIdentifier(imgDefault, "drawable", name)
                /*      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        source = ImageDecoder.createSource(context.resources, id)
                        bitmap = ImageDecoder.decodeBitmap(source)
                    } else {*/
                bitmap = BitmapFactory.decodeResource(context.resources, id);
                //   }
                Log.d("getImgDefaultBitmap", "imgDefault = $imgDefault")
            } catch (e: Exception) {
                bitmap = getExceptionDefaultBitmap(context)
            }

            return bitmap
        }

        fun getImgBitmap(img: String, context: Context): Bitmap {
            val source: ImageDecoder.Source
            var bitmap: Bitmap
            val contentResolver = context.contentResolver

            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
            // or
            //Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            //Check for the freshest data.
            val name = context.packageName
            //   context.grantUriPermission(name, Uri.parse(img), Intent.FLAG_GRANT_READ_URI_PERMISSION )
            //contentResolver.takePersistableUriPermission(Uri.parse(img),  Intent.FLAG_GRANT_READ_URI_PERMISSION)
            //   val persistedUriPermissions = contentResolver.persistedUriPermissions
            //   Log.d("pers", "${persistedUriPermissions.toString()}")
            try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                Log.d("parse","${Uri.parse(img)}")
                source = ImageDecoder.createSource(contentResolver, Uri.parse(img))
                bitmap = ImageDecoder.decodeBitmap(source)
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    Uri.parse(img)
                );
            }
            } catch (e: Exception) {
                bitmap = getExceptionDefaultBitmap(context)
            }

            Log.d("getImgDefaultBitmap", "im str = $img")

            return bitmap
        }
    }
}