//package info.upump.jym.bd;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.net.Uri;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import info.upump.jym.entity.User;
//
//
//public class UserDao extends DBDao implements IData<User> {
//    private final String[] keys = new String[]{
//            DBHelper.TABLE_KEY_ID,
//            DBHelper.TABLE_KEY_NAME,
//            DBHelper.TABLE_KEY_SET_WEIGHT,
//            DBHelper.TABLE_KEY_HEIGHT,
//            DBHelper.TABLE_KEY_FAT,
//            DBHelper.TABLE_KEY_NECK,
//            DBHelper.TABLE_KEY_SHOULDER,
//            DBHelper.TABLE_KEY_PECTORAL,
//            DBHelper.TABLE_KEY_RIGHT_HAND,
//            DBHelper.TABLE_KEY_LEFT_HAND,
//            DBHelper.TABLE_KEY_ABS,
//            DBHelper.TABLE_KEY_RIGHT_LEG,
//            DBHelper.TABLE_KEY_LEFT_LEG,
//            DBHelper.TABLE_KEY_RIGHT_CALVES,
//            DBHelper.TABLE_KEY_LEFT_CALVES,
//            DBHelper.TABLE_KEY_DATE};
//
//    private ContentValues getContentValuesFrom(User object) {
//        ContentValues cv = new ContentValues();
//        if (object.getId() != 0) {
//            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
//        }
//        cv.put(DBHelper.TABLE_KEY_NAME, object.getName());
//        cv.put(DBHelper.TABLE_KEY_SET_WEIGHT, object.getWeight());
//        cv.put(DBHelper.TABLE_KEY_HEIGHT, object.getHeight());
//        cv.put(DBHelper.TABLE_KEY_FAT, object.getFat());
//        cv.put(DBHelper.TABLE_KEY_NECK, object.getNeck());
//        cv.put(DBHelper.TABLE_KEY_SHOULDER, object.getShoulder());
//        cv.put(DBHelper.TABLE_KEY_PECTORAL, object.getPectoral());
//        cv.put(DBHelper.TABLE_KEY_RIGHT_HAND, object.getRightBiceps());
//        cv.put(DBHelper.TABLE_KEY_LEFT_HAND, object.getLeftBiceps());
//        cv.put(DBHelper.TABLE_KEY_ABS, object.getAbs());
//        cv.put(DBHelper.TABLE_KEY_RIGHT_LEG, object.getRightLeg());
//        cv.put(DBHelper.TABLE_KEY_LEFT_LEG, object.getLeftLeg());
//        cv.put(DBHelper.TABLE_KEY_RIGHT_CALVES, object.getRightCalves());
//        cv.put(DBHelper.TABLE_KEY_LEFT_CALVES, object.getLeftCalves());
//        cv.put(DBHelper.TABLE_KEY_DATE, object.getStringFormatDate());
//        return cv;
//    }
//
//    private User getUserFromCursor(Cursor cursor) {
//        User user = new User();
//        user.setId(cursor.getLong(0));
//        user.setName(cursor.getString(1));
//        user.setWeight(cursor.getDouble(2));
//        user.setHeight(cursor.getDouble(3));
//        user.setFat(cursor.getDouble(4));
//        user.setNeck(cursor.getDouble(5));
//        user.setShoulder(cursor.getDouble(6));
//        user.setPectoral(cursor.getDouble(7));
//        user.setRightBiceps(cursor.getDouble(8));
//        user.setLeftBiceps(cursor.getDouble(9));
//        user.setAbs(cursor.getDouble(10));
//        user.setLeftLeg(cursor.getDouble(11));
//        user.setRightLeg(cursor.getDouble(12));
//        user.setRightCalves(cursor.getDouble(13));
//        user.setLeftCalves(cursor.getDouble(14));
//        user.setDate(cursor.getString(15));
//        return user;
//    }
//
//    private UserDao(Context context) {
//        super(context, null);
//    }
//
//    private UserDao(Context context, Uri uri) {
//        super(context, uri);
//    }
//
//    public static UserDao getInstance(Context context, Uri uri) {
//        return new UserDao(context, uri);
//    }
//
//    @Override
//    public List<User> getAll() {
//        Cursor cursor = null;
//        List<User> userList = new ArrayList<>();
//        try {
//            cursor = sqLiteDatabase.query(DBHelper.TABLE_USER, keys, null, null, null, null, null);
//
//            User user;
//            if (cursor.moveToFirst()) {
//                do {
//                    user = getUserFromCursor(cursor);
//                    userList.add(user);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return userList;
//    }
//
//    @Override
//    public long create(User object) {
//        ContentValues cv = getContentValuesFrom(object);
//        return sqLiteDatabase.insert(DBHelper.TABLE_USER, null, cv);
//    }
//
//    @Override
//    public boolean delete(User object) {
//        int id = sqLiteDatabase.delete(DBHelper.TABLE_USER, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
//        return id != 0;
//    }
//
//    @Override
//    public boolean update(User object) {
//        ContentValues cv = getContentValuesFrom(object);
//        int id = sqLiteDatabase.update(DBHelper.TABLE_USER, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
//        return id > 0;
//    }
//
//    @Override
//    public User getById(long id) {
//        Cursor cursor = null;
//        User user = null;
//        try {
//            cursor = sqLiteDatabase.query(DBHelper.TABLE_USER, keys, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
//
//
//        if (cursor.moveToFirst()) {
//            do {
//                user = getUserFromCursor(cursor);
//            } while (cursor.moveToNext());
//        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return user;
//    }
//
//    @Override
//    public List<User> getByParentId(long id) {
//        return null;
//    }
//
//    @Override
//    public boolean clear(long id) {
//        return false;
//    }
//
//    @Override
//    public long copyFromTemplate(long idItem, long idPrent) {
//        return 0;
//    }
//
//    public User getByOldDate() {
//        User user = null;
//        Cursor cursor = null;
//        try {
//            cursor = sqLiteDatabase.query(DBHelper.TABLE_USER, keys,
//                    null, null, null, null,
//                    " date(" + DBHelper.TABLE_KEY_DATE + ") desc Limit 1 ");
//        if (cursor.moveToFirst()) {
//            do {
//                user = getUserFromCursor(cursor);
//            } while (cursor.moveToNext());
//        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return user;
//    }
//}
//
