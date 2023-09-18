package info.upump.mycompose.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import info.upump.mycompose.R

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

    fun restore(uri: Uri, context: Context) {

    }


}