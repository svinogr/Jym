package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 05.03.2018.
 */

public class CycleDao extends DBDAO implements IData<Cycle> {
    public CycleDao(Context context) {
        super(context);
    }

    @Override
    public List<Cycle> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_CYCLE,
                new String[]{
                        DBHelper.TABLE_KEY_ID,
                        DBHelper.TABLE_KEY_TITLE,
                        DBHelper.TABLE_KEY_START_DATE,
                        DBHelper.TABLE_KEY_FINISH_DATE}, null, null, null, null, null);
        List<Cycle> cycleList = null;
        if (cursor.moveToFirst()) {
            do {
                Cycle cycle = new Cycle();
                cycle.setId(cursor.getLong(0));
                cycle.setTitle(cursor.getString(1));
                cycle.setStartDate((cursor.getString(2)));
                cycle.setFinishDate((cursor.getString(3)));
                cycleList.add(cycle);
            } while (cursor.moveToNext());
        }
        return cycleList;
    }

    @Override
    public long create(Cycle cycle) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TABLE_KEY_TITLE, cycle.getTitle());
        cv.put(DBHelper.TABLE_KEY_START_DATE, cycle.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, cycle.getFinishStringFormatDate());
        long id = sqLiteDatabase.insert(DBHelper.TABLE_CYCLE, null, cv);
        return id;
    }

    public boolean delete(Cycle cycle) {
        long id = sqLiteDatabase.delete(DBHelper.TABLE_CYCLE, DBHelper.TABLE_KEY_ID +" = ?" , new String[]{String.valueOf(cycle.getId())});
        System.out.println(id);
        return id != 0;
    }
}
