package info.upump.mycompose

import android.app.Application
import android.util.Log
import info.upump.database.DatabaseApp
import info.upump.database.RoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("Init app", "init")
        DatabaseApp.initilizeDb(this@App, RoomDB.BASE_NAME, RoomDB.DB_PATH)
    }
}