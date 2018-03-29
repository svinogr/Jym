package info.upump.jym.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import info.upump.jym.entity.Exercise;


abstract class DBDao {
    protected SQLiteDatabase sqLiteDatabase;
    protected DBHelper dbHelper;
    protected Context context;

    public DBDao(Context context) {
        this.dbHelper = DBHelper.getHelper(context);
        this.context = context;
        open();

    }

    protected void open() {
        this.sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
    }


    protected void close() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            sqLiteDatabase = null;
        }
    }
}
