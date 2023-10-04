package info.upump.jym

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import info.upump.database.DatabaseApp
import info.upump.jym.ui.screens.mainscreen.MainScreen
import info.upump.jym.ui.theme.JymTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivityCompose : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         lifecycleScope.launch(Dispatchers.IO) {
           //  val db = DatabaseApp(this@MainActivityCompose)
            // DatabaseApp.initilizeDb(this@MainActivityCompose)
           //  Log.d("TAG", "init db")
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