package info.upump.jym.kotlinClasses.backupDb.implBackup

import android.content.Context
import android.content.Intent
import android.net.Uri
import info.upump.jym.kotlinClasses.backupDb.Backupable
import info.upump.jym.kotlinClasses.backupDb.Backupable.const.WRITE_TO_FILE
import info.upump.jym.kotlinClasses.backupDb.Backupable.const.WRITE_TO_MAIL
import info.upump.jym.kotlinClasses.backupDb.DBReadable
import info.upump.jym.kotlinClasses.backupDb.DBWritable

class Backup(val context: Context) : Backupable {
    private val dbReader: DBReadable
    private val dbWriter: DBWritable

    init {
        this.dbReader = DBReadableImpl(context)
        this.dbWriter = DBWritableImpl(context)
    }

    override fun toBackup(toDestinationType: Int): Intent? =
        when (toDestinationType) {
            WRITE_TO_FILE -> dbWriter.writeToFile()
            WRITE_TO_MAIL -> dbWriter.writeToMail()
            else -> null
        }


    //для получения базы из файла бекапа
    override fun fromBackup(uri: Uri) {
        println("press button import")
        //TODO прикрутить прогшрес бар или нет
        val listCyle = dbReader.readFrom(uri)
        dbWriter.writeToDB(listCyle)
    }
}