package info.upump.mycompose.ui.screens.screenscomponents

import android.net.Uri

const val DEFAULT_IMAGE = "drew"
const val DEFAULT_IMAGE_ROUTE = "android.resource://info.upump.mycompose/drawable"

class BitmapCreator {
    companion object {
        fun getImageWithUri(image: String, defaultImage: String): Uri {
            val uri: Uri = if (image.isNotBlank()) {
                Uri.parse(image)
            } else if (defaultImage.isNotBlank()) {
                Uri.parse("$DEFAULT_IMAGE_ROUTE/$defaultImage")
            } else {
                Uri.parse("$DEFAULT_IMAGE_ROUTE/$DEFAULT_IMAGE")
            }

            return uri
        }
    }
}