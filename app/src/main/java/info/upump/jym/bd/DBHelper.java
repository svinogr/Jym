package info.upump.jym.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by explo on 05.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    public final static int DATA_BASE_VERSION = 1;
    public final static String DATABASE_NAME = "jym.db";
    public final static String TABLE_SET = "sets";
    public final static String TABLE_EXERCISE = "exercises";
    public final static String TABLE_WORKOUT = "workouts";
    public final static String TABLE_CYCLE = "cycles";



    public static final String TABLE_KEY_ID = "_id";
    public static final String TABLE_KEY_TITLE = "title";


    public static final String TABLE_KEY_START_DATE = "start_date";
    public static final String TABLE_KEY_FINISH_DATE = "finish_date";


    public static final String TABLE_KEY_WEEK_EVEN = "week_even";// only for workout
    public static final String TABLE_KEY_DEFAULT = "default_type";// only for workout
    private static final String TABLE_KEY_CYCLE_ID = "cycle_id";// only for workout

    public static final String TABLE_KEY_TYPE_EXERCISE = "type_exercise"; //for exercise, type of group muscles
    public static final String TABLE_KEY_IMG = "img";
    public static final String TABLE_KEY_WORKOUT_ID = "workout_id";//for exercise

    private static final String TABLE_KEY_SET_WEIGHT = "weight";//for set
    private static final String TABLE_KEY_SET_REPS = "reps";//for set
    private static final String TABLE_KEY_EXERCISE_ID = "exercise_id";//for set

    private static final String CREATE_TABLE_CYCLE="CREATE TABLE "+TABLE_CYCLE+
            "( "+TABLE_KEY_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            TABLE_KEY_TITLE+ " TEXT NOT NULL, " +
            TABLE_KEY_START_DATE+" TEXT NOT NULL, " +
            TABLE_KEY_FINISH_DATE+" TEXT NOT NULL)";


    private static final String CREATE_TABLE_WORKOUT="CREATE TABLE "+TABLE_WORKOUT+
            "( "+TABLE_KEY_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
             TABLE_KEY_TITLE+ " TEXT NOT NULL, " +
            TABLE_KEY_WEEK_EVEN+" INTEGER NOT NULL, " +
            TABLE_KEY_DEFAULT+" INTEGER NOT NULL, "+
            TABLE_KEY_CYCLE_ID+" INTEGER, "+
            "FOREIGN KEY("+TABLE_KEY_CYCLE_ID+") REFERENCES "+TABLE_CYCLE+"("+TABLE_KEY_ID+") ON DELETE CASCADE)";

    private static final String CREATE_TABLE_EXERCISE="CREATE TABLE "+TABLE_EXERCISE+
            "( "+TABLE_KEY_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            TABLE_KEY_TITLE+ " TEXT NOT NULL, " +
            TABLE_KEY_TYPE_EXERCISE+" TEXT, " +
            TABLE_KEY_IMG+" TEXT, " +
            TABLE_KEY_WORKOUT_ID+" INTEGER, " +
            "FOREIGN KEY("+TABLE_KEY_WORKOUT_ID+") REFERENCES "+TABLE_WORKOUT+"("+TABLE_KEY_ID+")ON DELETE CASCADE)";

    private static final String CREATE_TABLE_SET="CREATE TABLE "+TABLE_SET+
            "( "+TABLE_KEY_ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            TABLE_KEY_SET_WEIGHT+" INTEGER, " +
            TABLE_KEY_SET_REPS+" INTEGER, " +
            TABLE_KEY_EXERCISE_ID+" INTEGER, " +
            "FOREIGN KEY("+ TABLE_KEY_EXERCISE_ID+") REFERENCES "+TABLE_EXERCISE+"("+TABLE_KEY_ID+") ON DELETE CASCADE)";

    private static DBHelper instance;

    private static final String DB_PATH = "data/data/info.upump.jym/databases/" + DATABASE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CYCLE);
        db.execSQL(CREATE_TABLE_WORKOUT);
        db.execSQL(CREATE_TABLE_EXERCISE);
        db.execSQL(CREATE_TABLE_SET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized DBHelper getHelper(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }
}
