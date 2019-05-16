package info.upump.jym.kotlinClasses.backupDb.implBackup

import info.upump.jym.kotlinClasses.backupDb.Backupable
import info.upump.jym.kotlinClasses.backupDb.Backupable.const.WRITE_TO_FILE
import info.upump.jym.kotlinClasses.backupDb.Backupable.const.WRITE_TO_MAIL
import info.upump.jym.kotlinClasses.backupDb.DBReadable
import info.upump.jym.kotlinClasses.backupDb.DBWritable

class Backup : Backupable {
    private val dbReader: DBReadable
    private val dbWriter: DBWritable

    init {
        this.dbReader = DBReadableImpl()
        this.dbWriter = DBWritableImpl()
    }

    override fun toBackup(to: Int) {
        when (to) {
            WRITE_TO_FILE -> dbWriter.writeToFile()
            WRITE_TO_MAIL -> dbWriter.writeToMail()
        }


    }

    override fun fromBackup() {
        println("press button import")
    }
}