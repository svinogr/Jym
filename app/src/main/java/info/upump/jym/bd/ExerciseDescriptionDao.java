package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.ExerciseDescription;



public class ExerciseDescriptionDao extends DBDao implements IData<ExerciseDescription> {
    public ExerciseDescriptionDao(Context context) {
        super(context);
    }

    private String[] keys = new String[]{
            DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_DESCRIPTION,
            DBHelper.TABLE_KEY_PARENT_ID};

    private ExerciseDescription getEerciseDescriptionFromCursor(Cursor cursor) {
        ExerciseDescription exerciseDescription = new ExerciseDescription();
        exerciseDescription.setId(cursor.getLong(0));
        exerciseDescription.setDescription(cursor.getString(1));
        exerciseDescription.setParentId(cursor.getLong(2));
        return exerciseDescription;
    }

    private ContentValues getContentValuesFrom(ExerciseDescription object) {
        ContentValues cv = new ContentValues();
        if (object.getId() != 0) {
            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
        }
        cv.put(DBHelper.TABLE_KEY_DESCRIPTION, object.getDescription());
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
        return cv;
    }

    @Override
    public List<ExerciseDescription> getAll() {
        return null;
    }

    @Override
    public long create(ExerciseDescription object) {
        ContentValues cv = getContentValuesFrom(object);
        return sqLiteDatabase.insert(DBHelper.TABLE_EXERCISE_DESCRIPTION, null, cv);
    }

    @Override
    public boolean delete(ExerciseDescription object) {
        int delete = sqLiteDatabase.delete(DBHelper.TABLE_EXERCISE_DESCRIPTION, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return delete != 0;
    }

    @Override
    public boolean update(ExerciseDescription object) {
        ContentValues cv = getContentValuesFrom(object);
        int update = sqLiteDatabase.update(DBHelper.TABLE_EXERCISE_DESCRIPTION, cv, DBHelper.TABLE_KEY_ID + " = ? ", new String[]{String.valueOf(object.getId())});
        return update != 0;
    }

    @Override
    public ExerciseDescription getById(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE_DESCRIPTION,
                keys, DBHelper.TABLE_KEY_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        ExerciseDescription exerciseDescription = null;
        if (cursor.moveToFirst()) {
            do {
                exerciseDescription = getEerciseDescriptionFromCursor(cursor);
            } while (cursor.moveToNext());
        }
        return exerciseDescription;

    }

    @Override
    public List<ExerciseDescription> getByParentId(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE_DESCRIPTION,
                keys, DBHelper.TABLE_KEY_PARENT_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);
        List<ExerciseDescription> exerciseDescriptionList = null;
        ExerciseDescription exerciseDescription = null;
        if (cursor.moveToFirst()) {
            do {
                exerciseDescriptionList = new ArrayList<>();
                exerciseDescription = getEerciseDescriptionFromCursor(cursor);
                exerciseDescriptionList.add(exerciseDescription);
            } while (cursor.moveToNext());
        }
        return exerciseDescriptionList;
    }

    @Override
    public boolean clear(ExerciseDescription object) {
        return false;
    }
}
