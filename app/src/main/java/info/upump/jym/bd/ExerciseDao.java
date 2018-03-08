package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;


public class ExerciseDao extends DBDao implements IData<Exercise> {
    public ExerciseDao(Context context) {
        super(context);
    }

    @Override
    public List<Exercise> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                new String[]{
                        DBHelper.TABLE_KEY_ID,
                        DBHelper.TABLE_KEY_TITLE,
                        DBHelper.TABLE_KEY_COMMENT,
                        DBHelper.TABLE_KEY_TYPE_EXERCISE,
                        DBHelper.TABLE_KEY_DEFAULT,
                        DBHelper.TABLE_KEY_START_DATE,
                        DBHelper.TABLE_KEY_FINISH_DATE,
                        DBHelper.TABLE_KEY_PARENT_ID}, null, null, null, null, null);
        List<Exercise> exerciseList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise();
                exercise.setId(cursor.getLong(0));
                exercise.setTitle(cursor.getString(1));
                exercise.setComment(cursor.getString(2));
                exercise.setTypeMuscle(TypeMuscle.valueOf(cursor.getString(3)));
                exercise.setDefaultType(cursor.getInt(4) == 1);
                exercise.setStartDate((cursor.getString(5)));
                exercise.setFinishDate((cursor.getString(6)));
                exercise.setParentId(cursor.getLong(7));
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        return exerciseList;
    }

    @Override
    public long create(Exercise object) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_TYPE_EXERCISE, object.getTypeMuscle().toString());
        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType());
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
        long id = sqLiteDatabase.insert(DBHelper.TABLE_EXERCISE, null, cv);
        return id;
    }

    @Override
    public boolean delete(Exercise object) {
        long id = sqLiteDatabase.delete(DBHelper.TABLE_EXERCISE, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        System.out.println(id);
        return id != 0;
    }

    @Override
    public boolean update(Exercise object) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_TYPE_EXERCISE, object.getTypeMuscle().toString());
        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType());
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
        long id = sqLiteDatabase.update(DBHelper.TABLE_EXERCISE, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id > 0;
    }

    @Override
    public Exercise getById(long id) {
        return null;
    }
}
