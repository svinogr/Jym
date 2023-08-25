package info.upump.mycompose.ui.screens.screenscomponents

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log

class BitmapCreator {
    companion object{
        fun getImageBitmap(imageEntity: Imageable, context: Context): Bitmap {
            val bitmap: Bitmap
            Log.d("getImageBitmap", "start---------------------------------------" )
            if (!imageEntity.image.isBlank()) {
                bitmap = getImgBitmap(imageEntity.image, context)
            } else if (!imageEntity.imageDefault.isBlank()) {
                bitmap = getImgDefaultBitmap(imageEntity.imageDefault, context)
            } else {
                bitmap = getExceptionDefaultBitmap(context)
            }
            Log.d("getImageBitmap", "---------------------------------------end" )
            return bitmap

        }

        fun getExceptionDefaultBitmap(context: Context): Bitmap {

            var bitmap: Bitmap
            val name = context.packageName
            val source: ImageDecoder.Source
            try {
                val id = context.resources.getIdentifier("drew", "drawable", name)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    source = ImageDecoder.createSource(context.resources, id)
                    bitmap = ImageDecoder.decodeBitmap(source)
                } else {
                    bitmap = BitmapFactory.decodeResource(context.resources, id);
                }
                Log.d("getExceptionDefaultBitmap", "id = $id" )
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    source = ImageDecoder.createSource(context.resources, id)
                    bitmap = ImageDecoder.decodeBitmap(source)
                } else {
                    bitmap = BitmapFactory.decodeResource(context.resources, id);
                }
                Log.d("getImgDefaultBitmap", "imgDefault = $imgDefault" )
            } catch (e: Exception) {
                bitmap = getExceptionDefaultBitmap(context)
            }

            return bitmap
        }

        fun getImgBitmap(img: String, context: Context): Bitmap {
            val source: ImageDecoder.Source
            val bitmap: Bitmap
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                source = ImageDecoder.createSource(context.contentResolver, Uri.parse(img))
                bitmap = ImageDecoder.decodeBitmap(source)
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    Uri.parse(img)
                );
            }
            Log.d("getImgDefaultBitmap", "im str = $img" )
            return bitmap
        }
    }
}