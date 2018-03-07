package info.upump.jym.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


abstract class DBDAO {
    protected SQLiteDatabase sqLiteDatabase;
    protected DBHelper dbHelper;

    public DBDAO(Context context) {
        this.dbHelper = DBHelper.getHelper(context);
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
