package info.upump.mycompose.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File


class DBProvider : FileProvider() {
    fun getDatabaseURI(c: Context): Uri {
        val data = Environment.getDataDirectory()
        val dbName = "jym.db"
        val currentDBPath = "//data//info.upump.mycompose//databases//$dbName"
        val exportFile = File(data, currentDBPath)

        return getFileUri(c, exportFile)
    }

   private fun getFileUri(c: Context, f: File): Uri {
        return getUriForFile(c!!, "info.upump.jym", f!!)
    }
}