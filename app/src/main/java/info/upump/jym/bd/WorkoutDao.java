package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.Day;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Sets;
import info.upump.jym.entity.Workout;


public class WorkoutDao extends DBDao implements IData<Workout> {
    public WorkoutDao(Context context) {
        super(context);
    }
    private static String sql  = "insert into "+ DBHelper.TABLE_WORKOUT + " values(?,?,?,?,?,?,?,?,?,?);";
    private static String sql1 = "insert into " + DBHelper.TABLE_EXERCISE + " values(?,?,?,?,?,?,?,?,?);";
    private static String sql2 = "insert into " + DBHelper.TABLE_SET + " values(?,?,?,?,?,?,?);";

    private List<Workout> workoutList;

    private final String[] keys = new String[]{
            DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_TITLE,
            DBHelper.TABLE_KEY_COMMENT,
            DBHelper.TABLE_KEY_WEEK_EVEN,
            DBHelper.TABLE_KEY_DEFAULT,
            DBHelper.TABLE_KEY_TEMPLATE,
            DBHelper.TABLE_KEY_DAY,
            DBHelper.TABLE_KEY_START_DATE,
            DBHelper.TABLE_KEY_FINISH_DATE,
            DBHelper.TABLE_KEY_PARENT_ID};

    private ContentValues getContentValuesFrom(Workout object) {
        ContentValues cv = new ContentValues();
        if (object.getId() != 0) {
            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
        }
        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_WEEK_EVEN, object.isWeekEven() ? 1 : 0);
        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType() ? 1 : 0);
        cv.put(DBHelper.TABLE_KEY_TEMPLATE, object.isTemplate() ? 1 : 0);
        cv.put(DBHelper.TABLE_KEY_DAY, object.getDay().toString());
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
        return cv;
    }

    private Workout getWorkoutFromCursor(Cursor cursor) {
        Workout workout = new Workout();
        workout.setId(cursor.getLong(0));
        workout.setTitle(cursor.getString(1));
        workout.setComment(cursor.getString(2));
        workout.setWeekEven(cursor.getInt(3) == 1);
        workout.setDefaultType(cursor.getInt(4) == 1);
        workout.setTemplate(cursor.getInt(5) == 1);
        workout.setDay(Day.valueOf(cursor.getString(6)));
        workout.setStartDate(cursor.getString(7));
        workout.setFinishDate(cursor.getString(8));
        workout.setParentId(cursor.getLong(9));
        return workout;
    }

    @Override
    public List<Workout> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT,
                keys, null, null, null, null, null);
        workoutList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Workout workout = getWorkoutFromCursor(cursor);
                workoutList.add(workout);
            } while (cursor.moveToNext());
        }

        return workoutList;
    }

    @Override
    public long create(Workout object) {
        ContentValues cv = getContentValuesFrom(object);
        long id = sqLiteDatabase.insert(DBHelper.TABLE_WORKOUT, null, cv);
        return id;
    }



    @Override
    public boolean update(Workout object) {
        ContentValues cv = getContentValuesFrom(object);
        long id = sqLiteDatabase.update(DBHelper.TABLE_WORKOUT, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id > 0;
    }

    @Override
    public Workout getById(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT,
                keys, DBHelper.TABLE_KEY_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);

        Workout workout = null;
        if (cursor.moveToFirst()) {
            do {
                workout = getWorkoutFromCursor(cursor);
            } while (cursor.moveToNext());
        }
        return workout;
    }

    @Override
    public List<Workout> getByParentId(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT,
                keys, DBHelper.TABLE_KEY_PARENT_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);

        workoutList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Workout workout = getWorkoutFromCursor(cursor);
                workoutList.add(workout);
            } while (cursor.moveToNext());
        }
        return workoutList;

    }



    @Override
    public boolean delete(Workout object) {
        if (object.isDefaultType()) return false;
        boolean delChild = clear(object.getId());
        long id=0;
        if (delChild) {
            id = sqLiteDatabase.delete(DBHelper.TABLE_WORKOUT, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        }
        return id != 0;
    }

    @Override
   public  boolean clear(long id) {
        List<Exercise> exerciseList;
        ExerciseDao exerciseDao = new ExerciseDao(context);
        exerciseList = exerciseDao.getByParentId(id);
        if (exerciseList.size() > 0) {
            for (Exercise exercise : exerciseList) {
                if (!exerciseDao.delete(exercise)) return false;
            }
        }
        return true;
    }

    public List<Workout> getTemplateUser() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT, keys,
                DBHelper.TABLE_KEY_TEMPLATE + " =? and "+DBHelper.TABLE_KEY_DEFAULT+ " = ?", new String[]{String.valueOf(1),String.valueOf(0)}, null, null, null
        );
        workoutList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Workout workout = getWorkoutFromCursor(cursor);
                workoutList.add(workout);
            } while (cursor.moveToNext());
        }

        return workoutList;
    }

    public List<Workout> getAllTemplate() {//
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT, keys,
                DBHelper.TABLE_KEY_TEMPLATE + " =? " , new String[]{String.valueOf(1)}, null, null, null
        );
        workoutList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Workout workout = getWorkoutFromCursor(cursor);
                workoutList.add(workout);
            } while (cursor.moveToNext());
        }

        return workoutList;
    }
/*
     DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_TITLE,
            DBHelper.TABLE_KEY_COMMENT,
            DBHelper.TABLE_KEY_WEEK_EVEN,
            DBHelper.TABLE_KEY_DEFAULT,
            DBHelper.TABLE_KEY_TEMPLATE,
            DBHelper.TABLE_KEY_DAY,
            DBHelper.TABLE_KEY_START_DATE,
            DBHelper.TABLE_KEY_FINISH_DATE,
            DBHelper.TABLE_KEY_PARENT_ID};*/


public void  alter(long idFrom, long idTarget){
    List<Workout> workoutList = getByParentId(idFrom);
    ExerciseDao exerciseDao = new ExerciseDao(context);
    SetDao setDao = new SetDao(context);
    SQLiteStatement  sqLiteStatement = sqLiteDatabase.compileStatement(sql);
    sqLiteDatabase.beginTransaction();
    try {

        for (Workout workout : workoutList) {
            workout.setTemplate(false);
            workout.setDefaultType(false);
            workout.setParentId(idTarget);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(2, workout.getTitle());
            sqLiteStatement.bindString(3, workout.getComment());
            sqLiteStatement.bindLong(4, workout.isWeekEven() ? 1 : 0);
            sqLiteStatement.bindLong(5,workout.isDefaultType() ? 1 : 0);
            sqLiteStatement.bindLong(6, workout.isTemplate()? 1 : 0);
            sqLiteStatement.bindString(7,workout.getDay().toString());
            sqLiteStatement.bindString(8, workout.getStartStringFormatDate());
            sqLiteStatement.bindString(9, workout.getFinishStringFormatDate());
            sqLiteStatement.bindLong(10, idTarget);
            long l = sqLiteStatement.executeInsert();
            workout.setParentId(idFrom);
            workout.setId(l);
        }

        for (Workout workout : workoutList) {

            List<Exercise> list = exerciseDao.getByParentId(workout.getParentId());
            SQLiteStatement sqLiteStatement1 = sqLiteDatabase.compileStatement(sql1);

            for (Exercise exercise : list) {
                exercise.setParentId(workout.getId());
                exercise.setTemplate(false);
                exercise.setDefaultType(false);
                sqLiteStatement1.clearBindings();
                sqLiteStatement1.bindString(2, exercise.getComment());
                sqLiteStatement1.bindLong(3, exercise.getDescriptionId());
                sqLiteStatement1.bindString(4, exercise.getTypeMuscle().toString());
                sqLiteStatement1.bindLong(5, exercise.isDefaultType() ? 1 : 0);
                sqLiteStatement1.bindLong(6, exercise.isTemplate() ? 1 : 0);
                sqLiteStatement1.bindString(7, exercise.getStartStringFormatDate());
                sqLiteStatement1.bindString(8, exercise.getFinishStringFormatDate());
                sqLiteStatement1.bindLong(9, exercise.getParentId());
                long l = sqLiteStatement1.executeInsert();
                exercise.setParentId(exercise.getId());
                exercise.setId(l);
            }

            for (Exercise exercise : list) {
                List<Sets> setsList = setDao.getByParentId(exercise.getParentId());
                SQLiteStatement sqLiteStatement2 = sqLiteDatabase.compileStatement(sql2);
                //  sqLiteDatabase.beginTransaction();

                for (Sets sets : setsList) {
                    sqLiteStatement2.clearBindings();
                    sqLiteStatement2.bindDouble(3, sets.getWeight());
                    sqLiteStatement2.bindLong(4, sets.getReps());
                    sqLiteStatement2.bindString(5, sets.getStartStringFormatDate());
                    sqLiteStatement2.bindString(6, sets.getFinishStringFormatDate());
                    sqLiteStatement2.bindLong(7, exercise.getId());
                    sqLiteStatement2.executeInsert();
                }
            }
        }

        sqLiteDatabase.setTransactionSuccessful();

    } finally {
        sqLiteDatabase.endTransaction();
    }
  /*  for (Workout exercise : workoutList) {
        exercise.setId(0);
        exercise.setTemplate(false);
        exercise.setDefaultType(false);
        exercise.setParentId(idTarget);
        long l = create(exercise);
        exercise.setId(l);
        exercise.setParentId(idTarget);
    }*/
 /*   ExerciseDao exerciseDao = new ExerciseDao(context);
    for (Workout exercise : workoutList) {
        exerciseDao.alterCopy(exercise.getParentId(), exercise.getId());
    }*/


}

    @Override
    public long copyFromTemplate(long idItem, long id) {
        Workout workout = getById(idItem);
        ExerciseDao exerciseDao = new ExerciseDao(context);

        List<Exercise> exerciseList = exerciseDao.getByParentId(workout.getId());
        workout.setId(0);
        workout.setTemplate(false);
        workout.setDefaultType(false);
        workout.setParentId(id);
        long idNewWorkout = create(workout);
    /*    sqLiteDatabase.beginTransaction();
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);

        long l;

        try {
            for (Sets sets : setsList) {

                sqLiteStatement.clearBindings();
                sqLiteStatement.bindNull( 2);
                sqLiteStatement.bindDouble( 3, sets.getWeight());
                sqLiteStatement.bindLong( 4, sets.getReps());
                sqLiteStatement.bindString( 5, sets.getStartStringFormatDate());
                sqLiteStatement.bindString( 6, sets.getFinishStringFormatDate());
                sqLiteStatement.bindLong( 7, id);
                l =sqLiteStatement.executeInsert();
            }
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }*/


        for (Exercise exercise : exerciseList) {
            exerciseDao.copyFromTemplate(exercise.getId(), idNewWorkout);
        }

        return idNewWorkout;
    }

    public List<Workout> getDefault() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT, keys,
                DBHelper.TABLE_KEY_DEFAULT + " =? and "+ DBHelper.TABLE_KEY_TEMPLATE + " =?", new String[]{String.valueOf(1), String.valueOf(0)}, null, null, null
        );
        workoutList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Workout workout = getWorkoutFromCursor(cursor);
                workoutList.add(workout);
            } while (cursor.moveToNext());
        }

        return workoutList;
    }
}
