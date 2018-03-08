package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 05.03.2018.
 */

public class CycleDao extends DBDao implements IData<Cycle> {
    public CycleDao(Context context) {
        super(context);
    }

    @Override
    public List<Cycle> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_CYCLE,
                new String[]{
                        DBHelper.TABLE_KEY_ID,
                        DBHelper.TABLE_KEY_TITLE,
                        DBHelper.TABLE_KEY_COMMENT,
                        DBHelper.TABLE_KEY_START_DATE,
                        DBHelper.TABLE_KEY_FINISH_DATE}, null, null, null, null, null);
        List<Cycle> cycleList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Cycle cycle = new Cycle();
                cycle.setId(cursor.getLong(0));
                cycle.setTitle(cursor.getString(1));
                cycle.setComment(cursor.getString(2));
                cycle.setStartDate((cursor.getString(3)));
                cycle.setFinishDate((cursor.getString(4)));
                cycleList.add(cycle);
            } while (cursor.moveToNext());
        }
        return cycleList;
    }

    @Override
    public long create(Cycle cycle) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_KEY_TITLE, cycle.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, cycle.getComment());
        cv.put(DBHelper.TABLE_KEY_START_DATE, cycle.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, cycle.getFinishStringFormatDate());
        long id = sqLiteDatabase.insert(DBHelper.TABLE_CYCLE, null, cv);
        return id;
    }

    @Override
    public boolean delete(Cycle cycle) {
        long id = sqLiteDatabase.delete(DBHelper.TABLE_CYCLE, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(cycle.getId())});
        System.out.println(id);
        return id != 0;
    }

    @Override
    public boolean update(Cycle cycle) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_KEY_TITLE, cycle.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, cycle.getComment());
        cv.put(DBHelper.TABLE_KEY_START_DATE, cycle.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, cycle.getFinishStringFormatDate());
        long id = sqLiteDatabase.update(DBHelper.TABLE_CYCLE, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(cycle.getId())});
        return id > 0;
    }

    @Override
    public Cycle getById(long id) {
        return null;
    }
}
