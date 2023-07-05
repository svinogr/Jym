package info.upump.mycompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import info.upump.database.DatabaseApp

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("db", "начало")
        /*   DBHelper helper = DBHelper.getHelper(getApplicationContext());
        helper.create_db();*/
     //   val appDb = DatabaseApp(applicationContext)
        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            Log.d("db", "ошибка")
            e.printStackTrace()
        }
        // Intent intent = new Intent(this, MainActivity.class);
        val intent = Intent(this, MainActivityCompose::class.java)
        startActivity(intent)
        finish()
    }
}