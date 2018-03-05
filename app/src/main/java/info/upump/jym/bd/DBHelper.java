package info.upump.jym.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by explo on 05.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private Context context;

    public final static String DATABASE_NAME = "jym.db";
    public final static String TABLE_EXERCISE = "exercise";
    public final static String TABLE_WORKOUT = "workout";

    public final static int DATA_BASE_VERSION = 1;

    public static final String TABLE_KEY_ID = "_id";
    public static final String TABLE_KEY_TITLE = "title";
    public static final String TABLE_KEY_WEEK_EVEN = "week";// only for workout

    public static final String TABLE_KEY_TYPE_EXERCISE = "type_exercise"; //for exercise type of roup muscles
    public static final String TABLE_KEY_READ = "read";
    public static final String TABLE_KEY_IMG_COVER = "img_cover";
    public static final String TABLE_KEY_ID_AUTHOR = "id_author";
    public static final String TABLE_KEY_IMG_CHAR = "img_char";

    public static final String TABLE_KEY_ID_COVER = "cover_id";
    public static final String TABLE_KEY_TEXT = "text";

    public static final String TABLE_KEY_NAME = "name";
    public static final String TABLE_KEY_EMAIL = "email";

    private static DBHelper instance;

    private static final String DB_PATH = "data/data/info.upump.russianapp/databases/" + DATABASE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
        this.context = context;
    }
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
