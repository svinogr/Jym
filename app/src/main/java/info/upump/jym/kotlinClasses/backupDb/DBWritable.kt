package info.upump.jym.kotlinClasses.backupDb

import android.net.Uri
import info.upump.jym.entity.Cycle

interface DBWritable {
    fun writeToDB(from: List<Cycle>)
    fun writeToFile(to: Uri)
    fun writeToMail()
}