package info.upump.mycompose

import android.app.Application
import android.util.Log
import info.upump.database.DatabaseApp

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("Init app", "init")
        DatabaseApp.initilizeDb(this@App)
    }
}