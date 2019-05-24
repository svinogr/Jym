package info.upump.jym.kotlinClasses.backupDb.implBackup

import android.content.Context
import android.content.Intent
import android.net.Uri
import info.upump.jym.R
import info.upump.jym.kotlinClasses.backupDb.Backupable
import info.upump.jym.kotlinClasses.backupDb.Backupable.const.WRITE_TO_FILE
import info.upump.jym.kotlinClasses.backupDb.Backupable.const.WRITE_TO_MAIL
import info.upump.jym.kotlinClasses.backupDb.DBReadable
import info.upump.jym.kotlinClasses.backupDb.DBWritable
import info.upump.jym.utils.fileprovider.MyFileProvider
import lib.folderpicker.FolderPicker

class Backup(val context: Context) : Backupable {
    override fun importTo(from: Uri) {
        val readFromList = dbReader.readFrom(from)
        dbWriter.writeToDB(readFromList)
    }

    override fun export(to: Int, toDist: Uri) =
            when (to) {
                WRITE_TO_FILE -> dbWriter.writeToFile(toDist)
                WRITE_TO_MAIL -> dbWriter.writeToMail()
                else -> print("do know how")
            }


    private val dbReader: DBReadable
    private val dbWriter: DBWritable

    init {
        this.dbReader = DBReadableImpl(context)
        this.dbWriter = DBWritableImpl(context)
    }

    override fun getIntentToBackup(toDestinationType: Int): Intent? =
            getDistIntent(toDestinationType)

    private fun getDistIntent(toDestinationType: Int): Intent =
            when (toDestinationType) {
                WRITE_TO_FILE -> fileIntent()

                WRITE_TO_MAIL
                -> mailIntent()
                else -> throw Exception("not intent backup")
            }

    private fun fileIntent(): Intent =
            Intent(context, FolderPicker::class.java)


    private fun mailIntent(): Intent {
        val intentToSendToBd = Intent(Intent.ACTION_SEND)
        val ur = MyFileProvider().getDatabaseURI(context)
        intentToSendToBd.type = "text/plain"
        intentToSendToBd.putExtra(Intent.EXTRA_STREAM, ur)
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intentToSendToBd.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject_for_beackup))
        return intentToSendToBd
    }


    override fun getIntentFromBackup(): Intent? {
        println("press button import")
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        return intent
        //TODO прикрутить прогшрес бар или нет
        //    val listCyle = dbReader.readFrom(uri)
        // dbWriter.writeToDB(listCyle)
    }
}