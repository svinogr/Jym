package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.Workout;


public class WorkoutDao extends DBDao implements IData<Workout> {
    public WorkoutDao(Context context) {
        super(context);
    }

    @Override
    public List<Workout> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT,
                new String[]{
                        DBHelper.TABLE_KEY_ID,
                        DBHelper.TABLE_KEY_TITLE,
                        DBHelper.TABLE_KEY_COMMENT,
                        DBHelper.TABLE_KEY_WEEK_EVEN,
                        DBHelper.TABLE_KEY_DEFAULT,
                        DBHelper.TABLE_KEY_PARENT_ID}
                , null, null, null, null, null);
        List<Workout> workoutList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout();
                workout.setId(cursor.getLong(0));
                workout.setTitle(cursor.getString(1));
                workout.setComment(cursor.getString(2));
                workout.setWeekEven(cursor.getInt(3) == 1);
                workout.setDefaultType(cursor.getInt(4) == 1);
                workout.setCycleId(cursor.getLong(5));

                workoutList.add(workout);
            } while (cursor.moveToNext());
        }
        return workoutList;

    }

    @Override
    public long create(Workout object) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_WEEK_EVEN, object.isWeekEven() ? 1 : 0);
        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType() ? 1 : 0);
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getCycleId());
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
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_WEEK_EVEN, object.isWeekEven() ? 1 : 0);
        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType() ? 1 : 0);
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getCycleId());
        long id = sqLiteDatabase.update(DBHelper.TABLE_WORKOUT, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id > 0;
    }
}
