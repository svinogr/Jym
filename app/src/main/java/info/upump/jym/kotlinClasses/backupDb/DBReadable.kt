package info.upump.jym.kotlinClasses.backupDb

import android.net.Uri
import info.upump.jym.entity.Cycle

interface DBReadable {

    fun readFrom(uri: Uri): List<Cycle>
}