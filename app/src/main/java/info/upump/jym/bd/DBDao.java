package info.upump.jym.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


abstract class DBDao {

    protected SQLiteDatabase sqLiteDatabase;
    protected DBHelper dbHelper;
    protected Context context;
    protected Uri uri;

    public DBDao(Context context, Uri uri) {
        this.dbHelper = DBHelper.getHelper(context);
        this.context = context;
        this.uri = uri;
        if (uri == null) {
            open();
        } else open(uri);
    }

    protected void open() {
        this.sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
    }

    protected void open(Uri uri) {
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
