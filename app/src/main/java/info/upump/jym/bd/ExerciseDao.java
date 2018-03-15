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

    private final String[] keys = new String[]{
            DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_TITLE,
            DBHelper.TABLE_KEY_COMMENT,
            DBHelper.TABLE_KEY_DESCRIPTION,
            DBHelper.TABLE_KEY_TYPE_EXERCISE,
            DBHelper.TABLE_KEY_DEFAULT,
            DBHelper.TABLE_KEY_IMG,
            DBHelper.TABLE_KEY_START_DATE,
            DBHelper.TABLE_KEY_FINISH_DATE,
            DBHelper.TABLE_KEY_PARENT_ID};

    private ContentValues getContentValuesFrom(Exercise object) {
        ContentValues cv = new ContentValues();
        if (object.getId() != 0) {
            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
        }
        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_DESCRIPTION, object.getDescription());
        cv.put(DBHelper.TABLE_KEY_TYPE_EXERCISE, object.getTypeMuscle().toString());
        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType());
        cv.put(DBHelper.TABLE_KEY_IMG, object.getImg());
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
        return cv;
    }

    private Exercise getExerciseFromCursor(Cursor cursor) {
        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setTitle(cursor.getString(1));
        exercise.setComment(cursor.getString(2));
        exercise.setDescription(cursor.getString(3));
        exercise.setTypeMuscle(TypeMuscle.valueOf(cursor.getString(4)));
        exercise.setDefaultType(cursor.getInt(5) == 1);
        exercise.setImg(cursor.getString(6));
        exercise.setStartDate((cursor.getString(7)));
        exercise.setFinishDate((cursor.getString(8)));
        exercise.setParentId(cursor.getLong(9));
        return exercise;
    }

    @Override
    public List<Exercise> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                keys, null, null, null, null, null);
        List<Exercise> exerciseList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = getExerciseFromCursor(cursor);
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        return exerciseList;
    }

    @Override
    public long create(Exercise object) {
        ContentValues cv = getContentValuesFrom(object);
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
        ContentValues cv = getContentValuesFrom(object);
        long id = sqLiteDatabase.update(DBHelper.TABLE_EXERCISE, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id > 0;
    }

    @Override
    public Exercise getById(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                keys, DBHelper.TABLE_KEY_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        Exercise exercise = null;
        if (cursor.moveToFirst()) {
            do {
                exercise = getExerciseFromCursor(cursor);
            } while (cursor.moveToNext());
        }
        return exercise;
    }

    @Override
    public List<Exercise> getByParentId(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                keys, DBHelper.TABLE_KEY_PARENT_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);

        return getListExercise(cursor);
    }

    public List<Exercise> getAllByTypeMuscle(TypeMuscle typeMuscle) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                keys, DBHelper.TABLE_KEY_TYPE_EXERCISE + " = ? ", new String[]{typeMuscle.toString()}, null, null, null);
        return getListExercise(cursor);
    }

    private List<Exercise> getListExercise(Cursor cursor) {
        List<Exercise> exerciseList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = getExerciseFromCursor(cursor);
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        return exerciseList;
    }
}
