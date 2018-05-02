package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.Sets;


public class SetDao extends DBDao implements IData<Sets> {

    public SetDao(Context context) {
        super(context);
    }

    private final String[] keys = new String[]{
            DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_COMMENT,
            DBHelper.TABLE_KEY_SET_WEIGHT,
            DBHelper.TABLE_KEY_SET_REPS,
            DBHelper.TABLE_KEY_START_DATE,
            DBHelper.TABLE_KEY_FINISH_DATE,
            DBHelper.TABLE_KEY_PARENT_ID};

    private ContentValues getContentValuesFor(Sets object) {
        ContentValues cv = new ContentValues();
        if (object.getId() != 0) {
            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
        }
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_SET_WEIGHT, object.getWeight());
        cv.put(DBHelper.TABLE_KEY_SET_REPS, object.getReps());
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
        return cv;
    }

    private Sets getSetsFromCursor(Cursor cursor) {
        Sets sets = new Sets();
        sets.setId(cursor.getLong(0));
        sets.setComment(cursor.getString(1));
        sets.setWeight(cursor.getDouble(2));
        sets.setReps(cursor.getInt(3));
        sets.setStartDate((cursor.getString(4)));
        sets.setFinishDate((cursor.getString(5)));
        sets.setParentId(cursor.getLong(6));
        return sets;
    }

    @Override
    public List<Sets> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                keys, null, null, null, null, null);

        List<Sets> setsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Sets sets = getSetsFromCursor(cursor);
                setsList.add(sets);
            } while (cursor.moveToNext());
        }
        return setsList;
    }

    @Override
    public long create(Sets object) {
        ContentValues cv = getContentValuesFor(object);
        long id = sqLiteDatabase.insert(DBHelper.TABLE_SET, null, cv);
        return id;
    }

    @Override
    public boolean delete(Sets object) {
        int id = sqLiteDatabase.delete(DBHelper.TABLE_SET, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id != 0;
    }

    @Override
    public boolean update(Sets object) {
        ContentValues cv = getContentValuesFor(object);
        long id = sqLiteDatabase.update(DBHelper.TABLE_SET, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id != 0;
    }

    @Override
    public Sets getById(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                keys, DBHelper.TABLE_KEY_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        Sets sets = null;
        if (cursor.moveToFirst()) {
            do {
                sets = getSetsFromCursor(cursor);
            } while (cursor.moveToNext());
        }

        return sets;
    }

    @Override
    public List<Sets> getByParentId(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                keys, DBHelper.TABLE_KEY_PARENT_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);

        List<Sets> setsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Sets sets = getSetsFromCursor(cursor);
                setsList.add(sets);
            } while (cursor.moveToNext());
        }

        return setsList;
    }

    @Override
    public boolean clear(long id) {
        return false;
    }


@Override
    public long copyFromTemplate(long idItem, long id) {
        Sets sets = getById(idItem);
        sets.setId(0);
        sets.setParentId(id);
        long l = create(sets);
        return l;
    }

    public List<Sets> getSetsFromId(long id){
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                keys, DBHelper.TABLE_KEY_ID + " >=? ", new String[]{String.valueOf(id)}, null, null, null);

        List<Sets> setsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Sets sets = getSetsFromCursor(cursor);
                setsList.add(sets);
            } while (cursor.moveToNext());
        }

        return setsList;
    }
}
