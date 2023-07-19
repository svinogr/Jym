//package info.upump.jym.bd;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteStatement;
//import android.net.Uri;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import info.upump.jym.entity.Day;
//import info.upump.jym.entity.Exercise;
//import info.upump.jym.entity.Sets;
//import info.upump.jym.entity.Workout;
//
//
//public class WorkoutDao extends DBDao implements IData<Workout> {
//    private WorkoutDao(Context context) {
//        super(context, null);
//    }
//
//    private WorkoutDao(Context context, Uri uri) {
//        super(context, uri);
//    }
//
//    private static String sqlForWorkout = "insert into " + DBHelper.TABLE_WORKOUT + " values(?,?,?,?,?,?,?,?,?,?);";
//    private static String sqlForExercise = "insert into " + DBHelper.TABLE_EXERCISE + " values(?,?,?,?,?,?,?,?,?);";
//    private static String sqlForSets = "insert into " + DBHelper.TABLE_SET + " values(?,?,?,?,?,?,?);";
//
//    private List<Workout> workoutList;
//
//    public static WorkoutDao getInstance(Context context, Uri uri) {
//        return new WorkoutDao(context, uri);
//    }
//
//    private final String[] keys = new String[]{
//            DBHelper.TABLE_KEY_ID,
//            DBHelper.TABLE_KEY_TITLE,
//            DBHelper.TABLE_KEY_COMMENT,
//            DBHelper.TABLE_KEY_WEEK_EVEN,
//            DBHelper.TABLE_KEY_DEFAULT,
//            DBHelper.TABLE_KEY_TEMPLATE,
//            DBHelper.TABLE_KEY_DAY,
//            DBHelper.TABLE_KEY_START_DATE,
//            DBHelper.TABLE_KEY_FINISH_DATE,
//            DBHelper.TABLE_KEY_PARENT_ID};
//
//    private ContentValues getContentValuesFrom(Workout object) {
//        ContentValues cv = new ContentValues();
//        if (object.getId() != 0) {
//            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
//        }
//        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
//        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
//        cv.put(DBHelper.TABLE_KEY_WEEK_EVEN, object.isWeekEven() ? 1 : 0);
//        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType() ? 1 : 0);
//        cv.put(DBHelper.TABLE_KEY_TEMPLATE, object.isTemplate() ? 1 : 0);
//        cv.put(DBHelper.TABLE_KEY_DAY, object.getDay().toString());
//        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
//        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
//        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
//        return cv;
//    }
//
//    private Workout getWorkoutFromCursor(Cursor cursor) {
//        Workout workout = new Workout();
//        workout.setId(cursor.getLong(0));
//        workout.setTitle(cursor.getString(1));
//        workout.setComment(cursor.getString(2));
//        workout.setWeekEven(cursor.getInt(3) == 1);
//        workout.setDefaultType(cursor.getInt(4) == 1);
//        workout.setTemplate(cursor.getInt(5) == 1);
//        workout.setDay(Day.valueOf(cursor.getString(6)));
//        workout.setStartDate(cursor.getString(7));
//        workout.setFinishDate(cursor.getString(8));
//        workout.setParentId(cursor.getLong(9));
//        return workout;
//    }
//
//    @Override
//    public List<Workout> getAll() {
//        Cursor cursor = null;
//        workoutList = new ArrayList<>();
//        try {
//            cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT,
//                    keys, null, null, null, null, null);
//
//
//            if (cursor.moveToFirst()) {
//                do {
//                    Workout workout = getWorkoutFromCursor(cursor);
//                    workoutList.add(workout);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return workoutList;
//    }
//
//    @Override
//    public long create(Workout object) {
//        ContentValues cv = getContentValuesFrom(object);
//        long id = sqLiteDatabase.insert(DBHelper.TABLE_WORKOUT, null, cv);
//        return id;
//    }
//
//
//    @Override
//    public boolean update(Workout object) {
//        ContentValues cv = getContentValuesFrom(object);
//        long id = sqLiteDatabase.update(DBHelper.TABLE_WORKOUT, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
//        return id > 0;
//    }
//
//    @Override
//    public Workout getById(long id) {
//        Cursor cursor = null;
//        Workout workout = null;
//        try {
//            cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT,
//                    keys, DBHelper.TABLE_KEY_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    workout = getWorkoutFromCursor(cursor);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        return workout;
//    }
//
//    @Override
//    public List<Workout> getByParentId(long id) {
//        Cursor cursor = null;
//        workoutList = new ArrayList<>();
//        try {
//            cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT,
//                    keys, DBHelper.TABLE_KEY_PARENT_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);
//            if (cursor.moveToFirst()) {
//                do {
//                    Workout workout = getWorkoutFromCursor(cursor);
//                    workoutList.add(workout);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        return workoutList;
//    }
//
//
//    @Override
//    public boolean delete(Workout object) {
//        if (object.isDefaultType()) return false;
//        boolean delChild = clear(object.getId());
//        long id = 0;
//        if (delChild) {
//            id = sqLiteDatabase.delete(DBHelper.TABLE_WORKOUT, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
//        }
//
//        return id != 0;
//    }
//
//    @Override
//    public boolean clear(long id) {
//        List<Exercise> exerciseList;
//        ExerciseDao exerciseDao = ExerciseDao.getInstance(context, uri);
//        exerciseList = exerciseDao.getByParentId(id);
//        if (exerciseList.size() > 0) {
//            for (Exercise exercise : exerciseList) {
//                if (!exerciseDao.delete(exercise)) return false;
//            }
//        }
//        return true;
//    }
//
//    public List<Workout> getTemplateUser() {
//        Cursor cursor = null;
//        workoutList = new ArrayList<>();
//        try {
//            cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT, keys,
//                    DBHelper.TABLE_KEY_TEMPLATE + " =? and " + DBHelper.TABLE_KEY_DEFAULT + " = ?", new String[]{String.valueOf(1), String.valueOf(0)}, null, null, null
//            );
//            if (cursor.moveToFirst()) {
//                do {
//                    Workout workout = getWorkoutFromCursor(cursor);
//                    workoutList.add(workout);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return workoutList;
//    }
//
//
//    public List<Workout> getAllUserInflated(long byParentId) {
//
//        List<Workout> list = getByParentId(byParentId);
//
//        ExerciseDao exerciseDao = ExerciseDao.getInstance(context, uri);
//        for (Workout workout : list) {
//            List<Exercise> exerciseList = exerciseDao.getAllInflated(workout.getId());
//            workout.setExercises(exerciseList);
//        }
//
//        return list;
//    }
//
//
//
//    public List<Workout> getAllTemplate() {//
//        Cursor cursor = null;
//        workoutList = new ArrayList<>();
//        try {
//            cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT, keys,
//                    DBHelper.TABLE_KEY_TEMPLATE + " =? ", new String[]{String.valueOf(1)}, null, null, null
//            );
//            if (cursor.moveToFirst()) {
//                do {
//                    Workout workout = getWorkoutFromCursor(cursor);
//                    workoutList.add(workout);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        return workoutList;
//    }
//
//
//    public Workout alter(long idFrom, long idTarget) {
//        long start = System.currentTimeMillis();
//        SQLiteStatement sqLiteStatementWorkout = sqLiteDatabase.compileStatement(sqlForWorkout);
//        ExerciseDao exerciseDao = ExerciseDao.getInstance(context, uri);
//        SetDao setDao = SetDao.getInstance(context, uri);
//
//        Workout workout = getById(idFrom);
//        workout.setParentId(idTarget);
//        workout.setTemplate(false);
//        workout.setDefaultType(false);
//        sqLiteDatabase.beginTransaction();
//        try {
//            sqLiteStatementWorkout.clearBindings();
//            sqLiteStatementWorkout.bindString(2, workout.getTitle());
//            sqLiteStatementWorkout.bindString(3, workout.getComment());
//            sqLiteStatementWorkout.bindLong(4, workout.isWeekEven() ? 1 : 0);
//            sqLiteStatementWorkout.bindLong(5, workout.isDefaultType() ? 1 : 0);
//            sqLiteStatementWorkout.bindLong(6, workout.isTemplate() ? 1 : 0);
//            sqLiteStatementWorkout.bindString(7, workout.getDay().toString());
//            sqLiteStatementWorkout.bindString(8, workout.getStartStringFormatDate());
//            sqLiteStatementWorkout.bindString(9, workout.getFinishStringFormatDate());
//            sqLiteStatementWorkout.bindLong(10, idTarget);
//            long idNewWorkout = sqLiteStatementWorkout.executeInsert();
//            workout.setId(idNewWorkout);
//
//            List<Exercise> list = exerciseDao.getByParentId(idFrom);
//            SQLiteStatement sqLiteStatementExercise = sqLiteDatabase.compileStatement(sqlForExercise);
//
//            for (Exercise exercise : list) {
//                exercise.setParentId(workout.getId());
//                exercise.setTemplate(false);
//                exercise.setDefaultType(false);
//                sqLiteStatementExercise.clearBindings();
//                sqLiteStatementExercise.bindString(2, exercise.getComment());
//                sqLiteStatementExercise.bindLong(3, exercise.getDescriptionId());
//                sqLiteStatementExercise.bindString(4, exercise.getTypeMuscle().toString());
//                sqLiteStatementExercise.bindLong(5, exercise.isDefaultType() ? 1 : 0);
//                sqLiteStatementExercise.bindLong(6, exercise.isTemplate() ? 1 : 0);
//                sqLiteStatementExercise.bindString(7, exercise.getStartStringFormatDate());
//                sqLiteStatementExercise.bindString(8, exercise.getFinishStringFormatDate());
//                sqLiteStatementExercise.bindLong(9, exercise.getParentId());
//                long l = sqLiteStatementExercise.executeInsert();
//                exercise.setParentId(exercise.getId());
//                exercise.setId(l);
//            }
//
//            for (Exercise exercise : list) {
//                List<Sets> setsList = setDao.getByParentId(exercise.getParentId());
//                SQLiteStatement sqLiteStatementSets = sqLiteDatabase.compileStatement(sqlForSets);
//
//                for (Sets sets : setsList) {
//                    sqLiteStatementSets.clearBindings();
//                    sqLiteStatementSets.bindDouble(3, sets.getWeight());
//                    sqLiteStatementSets.bindLong(4, sets.getReps());
//                    sqLiteStatementSets.bindString(5, sets.getStartStringFormatDate());
//                    sqLiteStatementSets.bindString(6, sets.getFinishStringFormatDate());
//                    sqLiteStatementSets.bindLong(7, exercise.getId());
//                    sqLiteStatementSets.executeInsert();
//                }
//            }
//
//            sqLiteDatabase.setTransactionSuccessful();
//
//        } finally {
//            sqLiteDatabase.endTransaction();
//        }
//
//
//        long finish = System.currentTimeMillis() - start;
//        System.out.println(finish + " workout copy ms");
//        if (workout.getId() != 0) {
//            return workout;
//        } else return null;
//    }
//
//    @Override
//    public long copyFromTemplate(long idItem, long id) {
//        long start = System.currentTimeMillis();
//        Workout workout = getById(idItem);
//        ExerciseDao exerciseDao = ExerciseDao.getInstance(context, uri);
//
//        List<Exercise> exerciseList = exerciseDao.getByParentId(workout.getId());
//        workout.setId(0);
//        workout.setTemplate(false);
//        workout.setDefaultType(false);
//        workout.setParentId(id);
//        long idNewWorkout = create(workout);
//
//        for (Exercise exercise : exerciseList) {
//            exerciseDao.copyFromTemplate(exercise.getId(), idNewWorkout);
//        }
//
//        long finish = System.currentTimeMillis() - start;
//        System.out.println(finish + " workout copy ms");
//        return idNewWorkout;
//    }
//
//    public List<Workout> getDefault() {
//        Cursor cursor =  null;
//        workoutList = new ArrayList<>();
//        try {
//            cursor = sqLiteDatabase.query(DBHelper.TABLE_WORKOUT, keys,
//                    DBHelper.TABLE_KEY_DEFAULT + " =? and " + DBHelper.TABLE_KEY_TEMPLATE + " =?", new String[]{String.valueOf(1), String.valueOf(0)}, null, null, null
//            );
//        if (cursor.moveToFirst()) {
//            do {
//                Workout workout = getWorkoutFromCursor(cursor);
//                workoutList.add(workout);
//            } while (cursor.moveToNext());
//        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//
//        return workoutList;
//    }
//}
