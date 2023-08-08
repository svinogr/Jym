package info.upump.mycompose

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import info.upump.database.DatabaseApp
import info.upump.mycompose.ui.screens.mainscreen.MainScreen
import info.upump.mycompose.ui.theme.JymTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivityCompose : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         lifecycleScope.launch(Dispatchers.IO) {
           //  val db = DatabaseApp(this@MainActivityCompose)
             DatabaseApp.initilizeDb(this@MainActivityCompose)
             Log.d("TAG", "${DatabaseApp.db.workoutDao().getAllWorkouts().size}")
         }



        setContent {
            JymTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    MainScreen()

                }

            }
        }
    }
}

suspend fun j(contex: Context){
    coroutineScope {
        lazy {
            DatabaseApp(contex)
            DatabaseApp.db.cycleDao().getAllCycles() }
    }
}