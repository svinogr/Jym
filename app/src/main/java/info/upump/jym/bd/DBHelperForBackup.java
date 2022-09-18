package info.upump.jym.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;


public class DBHelperForBackup extends SQLiteOpenHelper {
    private Context context;
    public final static int DATA_BASE_VERSION = DBHelper.DATA_BASE_VERSION;
    public final static String DATABASE_NAME = "jymBackup.db";
    public final static String TABLE_SET = "sets";
    public final static String TABLE_EXERCISE = "exercises";
    public final static String TABLE_WORKOUT = "workouts";
    public final static String TABLE_CYCLE = "cycles";
    public final static String TABLE_USER = "user";
    public final static String TABLE_EXERCISE_DESCRIPTION = "exercise_description";


    public static final String TABLE_KEY_ID = "_id";
    public static final String TABLE_KEY_TITLE = "title";
    public static final String TABLE_KEY_COMMENT = "comment";


    public static final String TABLE_KEY_START_DATE = "start_date";
    public static final String TABLE_KEY_FINISH_DATE = "finish_date";


    public static final String TABLE_KEY_WEEK_EVEN = "week_even";
    public static final String TABLE_KEY_DEFAULT = "default_type";
    public static final String TABLE_KEY_PARENT_ID = "parent_id";
    public static final String TABLE_KEY_DAY = "day";

    public static final String TABLE_KEY_TYPE_EXERCISE = "type_exercise";
    public static final String TABLE_KEY_IMG = "img";
    public static final String TABLE_KEY_DEFAULT_IMG = "default_img";
    public static final String TABLE_KEY_TEMPLATE = "template";

    public static final String TABLE_KEY_SET_WEIGHT = "weight";
    public static final String TABLE_KEY_SET_REPS = "reps";

    public static final String TABLE_KEY_NAME = "name";
    public static final String TABLE_KEY_HEIGHT = "height";
    public static final String TABLE_KEY_FAT = "fat";
    public static final String TABLE_KEY_NECK = "neck";
    public static final String TABLE_KEY_SHOULDER = "shoulder";
    public static final String TABLE_KEY_PECTORAL = "pectoral";
    public static final String TABLE_KEY_RIGHT_HAND = "right_hand";
    public static final String TABLE_KEY_LEFT_HAND = "left_hand";
    public static final String TABLE_KEY_ABS = "abs";
    public static final String TABLE_KEY_RIGHT_LEG = "right_leg";
    public static final String TABLE_KEY_LEFT_LEG = "left_leg";
    public static final String TABLE_KEY_LEFT_CALVES = "right_calves";
    public static final String TABLE_KEY_RIGHT_CALVES = "left_calves";
    public static final String TABLE_KEY_DATE = "date";
    public static final String TABLE_KEY_IMG_ID = "id_image";
    public static final String TABLE_KEY_SET_PAST_WEIGHT = "past_set";
    public static final String TABLE_KEY_DESCRIPTION_ID = "description_id";


    private static final String CREATE_TABLE_CYCLE = "CREATE TABLE " + TABLE_CYCLE +
            "( " + TABLE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            TABLE_KEY_TITLE + " TEXT NOT NULL, " +
            TABLE_KEY_COMMENT + " TEXT, " +
            TABLE_KEY_DEFAULT + " INTEGER NOT NULL, " +
            TABLE_KEY_IMG + " TEXT, " +
            TABLE_KEY_START_DATE + " TEXT NOT NULL, " +
            TABLE_KEY_FINISH_DATE + " TEXT NOT NULL, " +
            TABLE_KEY_DEFAULT_IMG + " TEXT )";

    private static final String CREATE_TABLE_WORKOUT = "CREATE TABLE " + TABLE_WORKOUT +
            "( " + TABLE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            TABLE_KEY_TITLE + " TEXT NOT NULL, " +
            TABLE_KEY_COMMENT + " TEXT, " +
            TABLE_KEY_WEEK_EVEN + " INTEGER NOT NULL, " +
            TABLE_KEY_DEFAULT + " INTEGER NOT NULL, " +
            TABLE_KEY_TEMPLATE + " INTEGER NOT NULL, " +
            TABLE_KEY_DAY + " TEXT, " +
            TABLE_KEY_START_DATE + " TEXT NOT NULL, " +
            TABLE_KEY_FINISH_DATE + " TEXT NOT NULL, " +
            TABLE_KEY_PARENT_ID + " INTEGER)";

    private static final String CREATE_TABLE_EXERCISE = "CREATE TABLE " + TABLE_EXERCISE +
            "( " + TABLE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            TABLE_KEY_COMMENT + " TEXT, " +
            TABLE_KEY_DESCRIPTION_ID + " INTEGER, " +
            TABLE_KEY_TYPE_EXERCISE + " TEXT, " +
            TABLE_KEY_DEFAULT + " INTEGER NOT NULL, " +
            TABLE_KEY_TEMPLATE + " INTEGER NOT NULL, " +
            TABLE_KEY_START_DATE + " TEXT NOT NULL, " +
            TABLE_KEY_FINISH_DATE + " TEXT NOT NULL, " +
            TABLE_KEY_PARENT_ID + " INTEGER)";

    private static final String CREATE_TABLE_SET = "CREATE TABLE " + TABLE_SET +
            "( " + TABLE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            TABLE_KEY_COMMENT + " TEXT, " +
            TABLE_KEY_SET_WEIGHT + " REAL, " +
            TABLE_KEY_SET_REPS + " INTEGER, " +
            TABLE_KEY_START_DATE + " TEXT NOT NULL, " +
            TABLE_KEY_FINISH_DATE + " TEXT NOT NULL, " +
            TABLE_KEY_PARENT_ID + " INTEGER, "+
            TABLE_KEY_SET_PAST_WEIGHT + " INTEGER)";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER +
            "( " + TABLE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            TABLE_KEY_NAME + " TEXT, " +
            TABLE_KEY_SET_WEIGHT + " REAL, " +
            TABLE_KEY_HEIGHT + " REAL, " +
            TABLE_KEY_FAT + " REAL, " +
            TABLE_KEY_NECK + " REAL, " +
            TABLE_KEY_SHOULDER + " REAL, " +
            TABLE_KEY_PECTORAL + " REAL, " +
            TABLE_KEY_RIGHT_HAND + " REAL, " +
            TABLE_KEY_LEFT_HAND + " REAL, " +
            TABLE_KEY_ABS + " REAL, " +
            TABLE_KEY_RIGHT_LEG + " REAL, " +
            TABLE_KEY_LEFT_LEG + " REAL, " +
            TABLE_KEY_RIGHT_CALVES + " REAL, " +
            TABLE_KEY_LEFT_CALVES + " REAL, " +
            TABLE_KEY_DATE + " TEXT NOT NULL)";


    private static final String CREATE_TABLE_EXERCISE_DESCRIPTION = "CREATE TABLE " + TABLE_EXERCISE_DESCRIPTION +
            "( " + TABLE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            TABLE_KEY_IMG + " TEXT, " +
            TABLE_KEY_TITLE + " TEXT, " +
            TABLE_KEY_DEFAULT_IMG + " TEXT)";

    private static DBHelperForBackup instance;
    public static final String DB_PATH_FOR_BACKUP = "data/data/info.upump.jym/databases/";
    public static final String DB_PATH = DB_PATH_FOR_BACKUP + DATABASE_NAME;

    public DBHelperForBackup(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CYCLE);
        db.execSQL(CREATE_TABLE_WORKOUT);
        db.execSQL(CREATE_TABLE_EXERCISE);
        db.execSQL(CREATE_TABLE_SET);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_EXERCISE_DESCRIPTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static synchronized DBHelperForBackup getHelper(Context context) {
        if (instance == null) {
            instance = new DBHelperForBackup(context);
        }
        return instance;
    }


    private void deleteBD() {
        File file = new File(DB_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    private void seVersionDB() {
        SQLiteDatabase sqLiteDatabase;
        try {
            sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.setVersion(DATA_BASE_VERSION);
            sqLiteDatabase.close();
        } catch (SQLiteException e) {
        }
    }

    private boolean checkBD() {
        SQLiteDatabase sqLiteDatabase;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
            int version = sqLiteDatabase.getVersion();
            sqLiteDatabase.close();
            if (version < DATA_BASE_VERSION) {
                deleteBD();
                return false;
            } else return true;
        } catch (SQLiteException e) {
            return false;
        }
    }
}
