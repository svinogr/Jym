package info.upump.jym.kotlinClasses.backupDb

import android.net.Uri

interface Backupable {
    companion object const {
        const val WRITE_TO_FILE = 1
        const val WRITE_TO_MAIL = 2
    }

    fun toBackup(toDestinationType: Int)
    fun fromBackup(fromUri: Uri)
}