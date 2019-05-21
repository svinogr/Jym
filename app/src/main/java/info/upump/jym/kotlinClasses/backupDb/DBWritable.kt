package info.upump.jym.kotlinClasses.backupDb

import info.upump.jym.entity.Cycle

interface DBWritable {
    fun writeToDB(from: List<Cycle>)
    fun writeToFile()
    fun writeToMail()
}