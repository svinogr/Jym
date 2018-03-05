package info.upump.jym.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


abstract class DBDAO  {
    protected SQLiteDatabase sqLiteDatabase;
    protected DBHelper dbHelper;

    public DBDAO(Context context) {
        this.dbHelper = DBHelper.getHelper(context);
        this.sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    protected void close(){
        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
            sqLiteDatabase = null;
        }
    }
}
