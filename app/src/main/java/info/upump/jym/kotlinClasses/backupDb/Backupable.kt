package info.upump.jym.kotlinClasses.backupDb

import android.content.Intent
import android.net.Uri

interface Backupable {
    companion object const {
        const val WRITE_TO_FILE = 1
        const val WRITE_TO_MAIL = 2
    }

    fun getIntentToBackup(toDestinationType: Int): Intent?
    fun getIntentFromBackup(): Intent?
    fun importTo(from: Uri)
    fun export(to: Int, from: Uri)
}