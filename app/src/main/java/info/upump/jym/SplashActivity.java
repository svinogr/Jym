package info.upump.jym;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import info.upump.jym.bd.DBHelper;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("db", "начало");
        DBHelper helper = DBHelper.getHelper(getApplicationContext());
        helper.create_db();
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Log.d("db", "ошибка");
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
