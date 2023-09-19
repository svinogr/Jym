package com.example.jymcompose.ui.screens.ProfileScreen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import info.upump.database.DatabaseApp
import info.upump.database.repo.CycleRepo
import info.upump.mycompose.utils.DBRestoreBackup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navHostController: NavHostController, paddingValues: PaddingValues) {
    val context = LocalContext.current

    val coroutine = rememberCoroutineScope()

    val launch = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            coroutine.launch(Dispatchers.IO) {
                restoreFromFile(it, context)

            }
        }
    }

    Scaffold(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) { it ->
        Column() {
            Button(onClick = { sendToDb(context) }) {
                Text(text = "передать")
            }

            Button(onClick = { launch.launch("*/*") }) {
                Text(text = "gjkexbnm")
            }
        }
    }
}

private suspend fun restoreFromFile(uri: Uri, context: Context) {
    val backupDB = DBRestoreBackup()
    backupDB.restore(uri, context)
}


private fun sendToDb(context: Context) {
    val backupDab = DBRestoreBackup()
    val intent = backupDab.getSendIntent(context)
    context.startActivity(intent)
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(NavHostController(LocalContext.current), PaddingValues())
}