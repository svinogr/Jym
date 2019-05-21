package info.upump.jym.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


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
        InputStream myInput = null;
        OutputStream myOutput = null;

        File cacheDir = context.getCacheDir(); //получаем директорию приложения куда будем копировать временную базу
        String outFileName = cacheDir.getPath() + "/jym.db";

        try {
            //получаем файл через ContextResolver!!!
            myInput = context.getContentResolver().openInputStream(uri);
            myOutput = new FileOutputStream(outFileName);
            // побайтово копируем данные
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
            close();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }


    protected void close() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            sqLiteDatabase = null;
        }
    }
}
