package info.upump.mycompose.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import info.upump.database.DatabaseApp
import info.upump.database.RoomDB
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class DBRestoreBackup() {
    fun getSendIntent(context: Context): Intent {
        val intentToSendToBd = Intent(Intent.ACTION_SEND)
        val ur = DBProvider().getDatabaseURI(context)
        intentToSendToBd.type = "text/plain"
        intentToSendToBd.putExtra(Intent.EXTRA_STREAM, ur)
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intentToSendToBd.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intentToSendToBd.putExtra(
            Intent.EXTRA_SUBJECT,
            context.getString(R.string.email_subject_for_beackup)
        )

        return intentToSendToBd
    }

    suspend fun restore(uri: Uri, context: Context) {
        coroutineScope {
            launch {
                val bytes = context.contentResolver.openInputStream(uri)?.use {
                    it.readBytes()
                }

                val file = File(RoomDB.DB_PATH_RESTORE, RoomDB.BASE_NAME_RESTORE)

                withContext(Dispatchers.IO) {
                    FileOutputStream(file)
                }?.use {
                    it.write(bytes)
                    DatabaseApp.initilizeDb(context, RoomDB.BASE_NAME_RESTORE, RoomDB.DB_PATH_RESTORE, true)

                    val repo = CycleRepo.get().getAllFullEntityTemplate()
                    repo.onEach {
                        Log.d("jhj", it.size.toString())
                    }.collect() {

                        DatabaseApp.initilizeDb(context, RoomDB.BASE_NAME, RoomDB.DB_PATH)

                    }

                }


            }


        }
    }


}