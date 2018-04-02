package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Day;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Sets;
import info.upump.jym.entity.Workout;


public class WorkoutDao extends DBDao implements IData<Workout> {
    public WorkoutDao(Context context) {
        super(context);
    }

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
    public boolean delete(Workout object) {
        long id = sqLiteDatabase.delete(DBHelper.TABLE_WORKOUT, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        System.out.println(id);
        return id != 0;
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
    public boolean clear(Workout object) {
        return false;
    }

    public List<Workout> getTemplate() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT, keys,
                DBHelper.TABLE_KEY_TEMPLATE + " =?", new String[]{String.valueOf(1)}, null, null, null
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

    public long copyFromTemplate(long idItem, long id) {
//        insert into cycles (title, comment, default_type, img, start_date, finish_date) select  title, 1, 0, img, start_date, finish_date from cycles where _id = 1
   /*     Workout workout = getById(idItem);
        workout.setId(0);
        workout.setTemplate(false);
        workout.setParentId(id);
        return create(workout);*/
        Workout workout = getById(idItem);
        ExerciseDao exerciseDao = new ExerciseDao(context);

        List<Exercise> exerciseList = exerciseDao.getByParentId(workout.getId());

        workout.setId(0);
        workout.setTemplate(false);
        workout.setDefaultType(false);
        workout.setParentId(id);
        long idNewWorkout = create(workout);

        for (Exercise exercise : exerciseList) {
            exerciseDao.copyFromTemplate(exercise.getId(), idNewWorkout);

        }
        return idNewWorkout;


    }


}
