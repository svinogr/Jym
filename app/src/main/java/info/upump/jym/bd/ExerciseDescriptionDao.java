package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import info.upump.jym.entity.ExerciseDescription;

/**
 * Created by explo on 18.04.2018.
 */

public class ExerciseDescriptionDao extends DBDao implements IData<ExerciseDescription> {
    public ExerciseDescriptionDao(Context context) {
        super(context);
    }

    private final String[] keys = new String[]{
            DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_DEFAULT_IMG,
            DBHelper.TABLE_KEY_TITLE,
            DBHelper.TABLE_KEY_IMG
      };

    private ContentValues getContentValuesFor(ExerciseDescription object) {
        ContentValues cv = new ContentValues();
        if (object.getId() != 0) {
            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
        }
        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
        cv.put(DBHelper.TABLE_KEY_IMG, object.getImg());
        cv.put(DBHelper.TABLE_KEY_DEFAULT_IMG, object.getDefaultImg());

        return cv;
    }

    private ExerciseDescription getImageFromCursor(Cursor cursor) {
        ExerciseDescription imageForItem = new ExerciseDescription();
        imageForItem.setId(cursor.getLong(0));
        imageForItem.setDefaultImg(cursor.getString(1));
        imageForItem.setTitle(cursor.getString(2));
        imageForItem.setImg(cursor.getString(3));
        return imageForItem;
    }



    @Override
    public long create(ExerciseDescription object) {
        ContentValues cv = getContentValuesFor(object);
        long id = sqLiteDatabase.insert(DBHelper.TABLE_EXERCISE_DESCRIPTION, null, cv);
        return id;
    }

    @Override
    public boolean delete(ExerciseDescription object) {
        int id = sqLiteDatabase.delete(DBHelper.TABLE_EXERCISE_DESCRIPTION, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id != 0;
    }

    @Override
    public boolean update(ExerciseDescription object) {
        ContentValues cv = getContentValuesFor(object);
        long id = sqLiteDatabase.update(DBHelper.TABLE_EXERCISE_DESCRIPTION, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id != 0;
    }

    @Override
    public ExerciseDescription getById(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE_DESCRIPTION,
                keys, DBHelper.TABLE_KEY_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        ExerciseDescription imageForItem = null;
        if (cursor.moveToFirst()) {
            do {
                imageForItem = getImageFromCursor(cursor);
            } while (cursor.moveToNext());
        }
        return imageForItem;

    }

    @Override
    public List<ExerciseDescription> getAll() {
        return null;
    }


    @Override
    public List<ExerciseDescription> getByParentId(long id) {
        return null;
    }

    @Override
    public boolean clear(long id) {
        return false;
    }

    @Override
    public long copyFromTemplate(long idItem, long idPrent) {
        return 0;
    }
}
