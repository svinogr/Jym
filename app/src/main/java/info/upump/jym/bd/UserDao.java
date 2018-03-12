package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.entity.User;


public class UserDao extends DBDao implements IData<User> {
    private final String[] keys = new String[]{
            DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_NAME,
            DBHelper.TABLE_KEY_SET_WEIGHT,
            DBHelper.TABLE_KEY_HEIGHT,
            DBHelper.TABLE_KEY_NECK,
            DBHelper.TABLE_KEY_PECTORAL,
            DBHelper.TABLE_KEY_HAND,
            DBHelper.TABLE_KEY_ABS,
            DBHelper.TABLE_KEY_LEG,
            DBHelper.TABLE_KEY_DATE};

    private ContentValues getContentValuesFrom(User object) {
        ContentValues cv = new ContentValues();
        if (object.getId() != 0) {
            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
        }
        cv.put(DBHelper.TABLE_KEY_NAME, object.getName());
        cv.put(DBHelper.TABLE_KEY_SET_WEIGHT, object.getWeight());
        cv.put(DBHelper.TABLE_KEY_HEIGHT, object.getHeight());
        cv.put(DBHelper.TABLE_KEY_NECK, object.getNeck());
        cv.put(DBHelper.TABLE_KEY_PECTORAL, object.getPectoral());
        cv.put(DBHelper.TABLE_KEY_HAND, object.getHand());
        cv.put(DBHelper.TABLE_KEY_ABS, object.getAbs());
        cv.put(DBHelper.TABLE_KEY_LEG, object.getLeg());
        cv.put(DBHelper.TABLE_KEY_DATE, object.getStringFormatDate());
        return cv;
    }

    private User getUserFromCursor(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setName(cursor.getString(1));
        user.setWeight(cursor.getDouble(2));
        user.setHeight(cursor.getDouble(3));
        user.setNeck(cursor.getDouble(4));
        user.setPectoral(cursor.getDouble(5));
        user.setHand(cursor.getDouble(6));
        user.setAbs(cursor.getDouble(7));
        user.setLeg(cursor.getDouble(8));
        user.setDate(cursor.getString(9));
        return user;
    }

    public UserDao(Context context) {
        super(context);
    }

    @Override
    public List<User> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_USER, keys, null, null, null, null, null);
        List<User> userList = null;
        User user;
        if (cursor.moveToFirst()) {
            userList = new ArrayList<>();
            do {
                user = getUserFromCursor(cursor);
                userList.add(user);
            } while (cursor.moveToNext());
        }
        return userList;
    }

    @Override
    public long create(User object) {
        ContentValues cv = getContentValuesFrom(object);
        return sqLiteDatabase.insert(DBHelper.TABLE_USER, null, cv);
    }

    @Override
    public boolean delete(User object) {
        int id = sqLiteDatabase.delete(DBHelper.TABLE_USER, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id != 0;
    }

    @Override
    public boolean update(User object) {
     ContentValues cv = getContentValuesFrom(object);
     int id =sqLiteDatabase.update(DBHelper.TABLE_USER, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
      return id >0;
    }

    @Override
    public User getById(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_USER, keys, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(id)},null, null, null);
        User user = null;
        if(cursor.moveToFirst()){
            do{
                user = getUserFromCursor(cursor);
            }while (cursor.moveToNext());
        }
        return user;
    }

    @Override
    public List<User> getByParentId(long id) {
        return null;
    }
}
