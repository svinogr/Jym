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
    private final String[] keys =  new String[]{
            DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_TITLE,
            DBHelper.TABLE_KEY_COMMENT,
            DBHelper.TABLE_KEY_DEFAULT,
            DBHelper.TABLE_KEY_START_DATE,
            DBHelper.TABLE_KEY_FINISH_DATE};

    private ContentValues getContentValuesFrom(Cycle object){
        ContentValues cv = new ContentValues();
        if(object.getId()!=0){
            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
        }
        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType());
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        return cv;
    }

    private Cycle getExerciseFromCursor(Cursor cursor) {
        Cycle cycle = new Cycle();
        cycle.setId(cursor.getLong(0));
        cycle.setTitle(cursor.getString(1));
        cycle.setComment(cursor.getString(2));
        cycle.setDefaultType(cursor.getInt(3)==1);
        cycle.setStartDate((cursor.getString(4)));
        cycle.setFinishDate((cursor.getString(5)));
        return cycle;
    }

    @Override
    public List<Cycle> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_CYCLE,
       keys, null, null, null, null, null);
        List<Cycle> cycleList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Cycle cycle = getExerciseFromCursor(cursor);
                cycleList.add(cycle);
            } while (cursor.moveToNext());
        }
        return cycleList;
    }

    @Override
    public long create(Cycle cycle) {
        ContentValues cv = getContentValuesFrom(cycle);
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
        ContentValues cv = getContentValuesFrom(cycle);
        long id = sqLiteDatabase.update(DBHelper.TABLE_CYCLE, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(cycle.getId())});
        return id > 0;
    }

    @Override
    public Cycle getById(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_CYCLE,
                keys, DBHelper.TABLE_KEY_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        Cycle cycle = null;
        if (cursor.moveToFirst()) {
            do {
                cycle = getExerciseFromCursor(cursor);
            } while (cursor.moveToNext());
        }
        return cycle;
    }

    @Override
    public List<Cycle> getByParentId(long id) {
        return null;
    }
}
