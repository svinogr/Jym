package info.upump.jym.kotlinClasses.backupDb

import android.content.Intent
import info.upump.jym.entity.Cycle

interface DBWritable {
    fun writeToDB(from: List<Cycle>)
    fun writeToFile(): Intent
    fun writeToMail(): Intent
}