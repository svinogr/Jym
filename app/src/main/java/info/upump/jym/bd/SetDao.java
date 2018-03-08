package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.Sets;

/**
 * Created by explo on 08.03.2018.
 */

public class SetDao extends DBDao implements IData<Sets> {

    public SetDao(Context context) {
        super(context);
    }

    @Override
    public List<Sets> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                new String[]{
                        DBHelper.TABLE_KEY_ID,
                        DBHelper.TABLE_KEY_COMMENT,
                        DBHelper.TABLE_KEY_SET_WEIGHT,
                        DBHelper.TABLE_KEY_SET_REPS,
                        DBHelper.TABLE_KEY_START_DATE,
                        DBHelper.TABLE_KEY_FINISH_DATE,
                        DBHelper.TABLE_KEY_PARENT_ID}, null, null, null, null, null);
        List<Sets> setsList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Sets sets = new Sets();
                sets.setId(cursor.getLong(0));

                sets.setComment(cursor.getString(1));
                sets.setWeight(cursor.getDouble(2));
                sets.setReps(cursor.getInt(3));
                sets.setStartDate((cursor.getString(4)));
                sets.setFinishDate((cursor.getString(5)));
                sets.setParentId(cursor.getLong(6));
                setsList.add(sets);
            } while (cursor.moveToNext());
        }
        return setsList;
    }

    @Override
    public long create(Sets object) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_SET_WEIGHT, object.getWeight());
        cv.put(DBHelper.TABLE_KEY_SET_REPS, object.getReps());
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
        long id = sqLiteDatabase.insert(DBHelper.TABLE_SET, null, cv);
        return id;
    }

    @Override
    public boolean delete(Sets object) {
        long id = sqLiteDatabase.delete(DBHelper.TABLE_SET, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        System.out.println(id);
        return id != 0;
    }

    @Override
    public boolean update(Sets object) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_SET_WEIGHT, object.getWeight());
        cv.put(DBHelper.TABLE_KEY_SET_REPS, object.getReps());
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
        long id = sqLiteDatabase.update(DBHelper.TABLE_SET, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id > 0;
    }

    @Override
    public Sets getById(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                new String[]{
                        DBHelper.TABLE_KEY_ID,
                        DBHelper.TABLE_KEY_COMMENT,
                        DBHelper.TABLE_KEY_SET_WEIGHT,
                        DBHelper.TABLE_KEY_SET_REPS,
                        DBHelper.TABLE_KEY_START_DATE,
                        DBHelper.TABLE_KEY_FINISH_DATE,
                        DBHelper.TABLE_KEY_PARENT_ID}, null, null, null, null, null);
        Sets sets = null;
        if (cursor.moveToFirst()) {
            do {
                sets = new Sets();
                sets.setId(cursor.getLong(0));

                sets.setComment(cursor.getString(1));
                sets.setWeight(cursor.getDouble(2));
                sets.setReps(cursor.getInt(3));
                sets.setStartDate((cursor.getString(4)));
                sets.setFinishDate((cursor.getString(5)));
                sets.setParentId(cursor.getLong(6));
            } while (cursor.moveToNext());
        }
        return sets;

    }
}
