package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.Sets;


public class SetDao extends DBDao implements IData<Sets> {
    private static String sql = "insert into " + DBHelper.TABLE_SET + " values(?,?,?,?,?,?,?);";

    private SetDao(Context context) {
        super(context, null);
    }

    private SetDao(Context context, Uri uri) {
        super(context, uri);
    }

    public static SetDao getInstance(Context context, Uri uri) {
        return new SetDao(context, uri);
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
        Cursor cursor = null;
        List<Sets> setsList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                    keys, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Sets sets = getSetsFromCursor(cursor);
                    setsList.add(sets);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
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
        Cursor cursor = null;
        Sets sets = null;
        try {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                    keys, DBHelper.TABLE_KEY_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    sets = getSetsFromCursor(cursor);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return sets;
    }

    @Override
    public List<Sets> getByParentId(long id) {
        Cursor cursor = null;
        List<Sets> setsList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                    keys, DBHelper.TABLE_KEY_PARENT_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Sets sets = getSetsFromCursor(cursor);
                    setsList.add(sets);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return setsList;
    }

    @Override
    public boolean clear(long id) {
        return false;
    }

    @Override
    public long copyFromTemplate(long idItem, long id) {
    /*    SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        Sets sets = getById(idItem);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindNull(2);
        sqLiteStatement.bindDouble(3, sets.getWeight());
        sqLiteStatement.bindLong(4, sets.getReps());
        sqLiteStatement.bindString(5, sets.getStartStringFormatDate());
        sqLiteStatement.bindString(6, sets.getFinishStringFormatDate());
        sqLiteStatement.bindLong(7, id);
        long l = sqLiteStatement.executeInsert();*/
        Sets sets = getById(idItem);
        sets.setId(0);
        sets.setParentId(id);
        long l = create(sets);
        return l;
    }

/*    DBHelper.TABLE_KEY_ID,
    DBHelper.TABLE_KEY_COMMENT,
    DBHelper.TABLE_KEY_SET_WEIGHT,
    DBHelper.TABLE_KEY_SET_REPS,
    DBHelper.TABLE_KEY_START_DATE,
    DBHelper.TABLE_KEY_FINISH_DATE,
    DBHelper.TABLE_KEY_PARENT_ID};*/

    public void alterCopy(long idFrom, long idTarget) {
        System.out.println(idFrom + "dd" + idTarget);
        List<Sets> setsList = getByParentId(idFrom);
        System.out.println(setsList.size() + " SIZE0");
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteDatabase.beginTransaction();
        try {
            for (Sets sets : setsList) {
                sqLiteStatement.clearBindings();
                System.out.println(sets);
                // sqLiteStatement.bindNull(2);
                sqLiteStatement.bindDouble(3, sets.getWeight());
                sqLiteStatement.bindLong(4, sets.getReps());
                sqLiteStatement.bindString(5, sets.getStartStringFormatDate());
                sqLiteStatement.bindString(6, sets.getFinishStringFormatDate());
                sqLiteStatement.bindLong(7, idTarget);
                sqLiteStatement.executeInsert();
            }

            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }


    public List<Sets> getSetsFromId(long id) {
        Cursor cursor = null;
        List<Sets> setsList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_SET,
                    keys, DBHelper.TABLE_KEY_ID + " >=? ", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Sets sets = getSetsFromCursor(cursor);
                    setsList.add(sets);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return setsList;
    }
}
