package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Sets;
import info.upump.jym.entity.Workout;

public class CycleDao extends DBDao implements IData<Cycle> {
    private CycleDao(Context context) {
        super(context, null);
    }

    private CycleDao(Context context, Uri uri) {
        super(context, uri);
    }

    public static CycleDao getInstance(Context context, Uri uri) {
        return new CycleDao(context, uri);
    }

    private static final String sqlForCycle = "insert into " + DBHelper.TABLE_CYCLE + " values(?,?,?,?,?,?,?,?);";
    private static final String sqlForWorkout = "insert into " + DBHelper.TABLE_WORKOUT + " values(?,?,?,?,?,?,?,?,?,?);";
    private static final String sqlForExercise = "insert into " + DBHelper.TABLE_EXERCISE + " values(?,?,?,?,?,?,?,?,?);";
    private static final String sqlForSets = "insert into " + DBHelper.TABLE_SET + " values(?,?,?,?,?,?,?);";

    private final String[] keys = new String[]{
            DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_TITLE,
            DBHelper.TABLE_KEY_COMMENT,
            DBHelper.TABLE_KEY_DEFAULT,
            DBHelper.TABLE_KEY_IMG,
            DBHelper.TABLE_KEY_START_DATE,
            DBHelper.TABLE_KEY_FINISH_DATE,
            DBHelper.TABLE_KEY_DEFAULT_IMG,};

    private ContentValues getContentValuesFrom(Cycle object) {
        ContentValues cv = new ContentValues();
        if (object.getId() != 0) {
            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
        }
        cv.put(DBHelper.TABLE_KEY_TITLE, object.getTitle());
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType());
        cv.put(DBHelper.TABLE_KEY_IMG, object.getImage());
        if (object.getImage() != null) {
            cv.putNull(DBHelper.TABLE_KEY_DEFAULT_IMG);
        } else cv.put(DBHelper.TABLE_KEY_DEFAULT_IMG, object.getDefaultImg());
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        return cv;
    }

    private Cycle getExerciseFromCursor(Cursor cursor) {
        Cycle cycle = new Cycle();
        cycle.setId(cursor.getLong(0));
        cycle.setTitle(cursor.getString(1));
        cycle.setComment(cursor.getString(2));
        cycle.setDefaultType(cursor.getInt(3) == 1);
        cycle.setImage(cursor.getString(4));
        cycle.setStartDate((cursor.getString(5)));
        cycle.setFinishDate((cursor.getString(6)));
        cycle.setDefaultImg(cursor.getString(7));
        return cycle;
    }

    @Override
    public List<Cycle> getAll() {
        Cursor cursor = null;
        List<Cycle> cycleList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_CYCLE,
                    keys, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Cycle cycle = getExerciseFromCursor(cursor);
                    cycleList.add(cycle);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cycleList;
    }

    public List<Cycle> getAllDefault() {
        Cursor cursor = null;
        List<Cycle> cycleList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_CYCLE,
                    keys, DBHelper.TABLE_KEY_DEFAULT + " = ?", new String[]{"1"}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Cycle cycle = getExerciseFromCursor(cursor);
                    cycleList.add(cycle);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cycleList;
    }

    public List<Cycle> getAllUserInflated() {
        Cursor cursor = null;
        List<Cycle> cycleList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_CYCLE,
                    keys, DBHelper.TABLE_KEY_DEFAULT + " = ?", new String[]{"0"}, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Cycle cycle = getExerciseFromCursor(cursor);
                    cycleList.add(cycle);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        WorkoutDao workoutDao = WorkoutDao.getInstance(context, uri);
        for (Cycle cycle : cycleList) {
            List<Workout> workoutList = workoutDao.getAllUserInflated(cycle.getId());
            cycle.setWorkoutList(workoutList);
        }

        return cycleList;
    }


    public List<Cycle> getAllUser() {
        Cursor cursor = null;
        List<Cycle> cycleList = new ArrayList<>();
        try {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_CYCLE,
                    keys, DBHelper.TABLE_KEY_DEFAULT + " = ?", new String[]{"0"}, null, null, null);


            if (cursor.moveToFirst()) {
                do {
                    Cycle cycle = getExerciseFromCursor(cursor);
                    cycleList.add(cycle);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
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
    public boolean delete(Cycle object) {
        if (object.isDefaultType()) return false;
        boolean delChild = clear(object.getId());
        long id = 0;
        if (delChild) {
            id = sqLiteDatabase.delete(DBHelper.TABLE_CYCLE, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        }
        return id != 0;
    }

    private boolean deleteChildren(long id) {
        List<Workout> workoutList;
        WorkoutDao workoutDao = WorkoutDao.getInstance(context, uri);
        workoutList = workoutDao.getByParentId(id);
        if (workoutList.size() > 0) {
            for (Workout workout : workoutList) {
                if (!workoutDao.delete(workout)) return false;
            }
        }
        return true;
    }

    @Override
    public boolean update(Cycle cycle) {
        ContentValues cv = getContentValuesFrom(cycle);
        long id = sqLiteDatabase.update(DBHelper.TABLE_CYCLE, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(cycle.getId())});
        return id > 0;
    }

    @Override
    public Cycle getById(long id) {
        Cursor cursor = null;
        Cycle cycle = null;
        try {
            cursor = sqLiteDatabase.query(DBHelper.TABLE_CYCLE,
                    keys, DBHelper.TABLE_KEY_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    cycle = getExerciseFromCursor(cursor);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return cycle;
    }

    @Override
    public List<Cycle> getByParentId(long id) {
        return null;
    }

    @Override
    public boolean clear(long id) {
        List<Workout> workoutList;
        WorkoutDao workoutDao = WorkoutDao.getInstance(context, uri);
        workoutList = workoutDao.getByParentId(id);
        if (workoutList.size() > 0) {
            for (Workout workout : workoutList) {
                if (!workoutDao.delete(workout)) return false;
            }
        }
        return true;
    }


    @Override
    public long copyFromTemplate(long idItem, long idParent) {
        Cycle cycle = getById(idItem);
        WorkoutDao workoutDao = WorkoutDao.getInstance(context, uri);
        List<Workout> workoutList = workoutDao.getByParentId(cycle.getId());
        cycle.setDefaultType(false);
        cycle.setId(0);
        setActualDate(cycle);
        long idNewCycle = create(cycle);

        for (Workout workout : workoutList) {
            workoutDao.copyFromTemplate(workout.getId(), idNewCycle);
        }

        return idNewCycle;
    }

    public Cycle alterCopyTemplate(long idFrom) {
        long start = System.currentTimeMillis();
        Cycle cycle = getById(idFrom);
        cycle.setDefaultType(false);
        cycle.setId(0);
        setActualDate(cycle);
        WorkoutDao workoutDao = WorkoutDao.getInstance(context, uri);
        ExerciseDao exerciseDao = ExerciseDao.getInstance(context, uri);
        SetDao setDao = SetDao.getInstance(context, uri);

        sqLiteDatabase.beginTransaction();

        try {
            SQLiteStatement sqLiteStatementCycle = sqLiteDatabase.compileStatement(sqlForCycle);

            sqLiteStatementCycle.bindString(2, cycle.getTitle());
            sqLiteStatementCycle.bindString(3, cycle.getComment());
            sqLiteStatementCycle.bindLong(4, cycle.isDefaultType() ? 1 : 0);
            if (cycle.getImage() == null) {
                sqLiteStatementCycle.bindNull(5);
            } else sqLiteStatementCycle.bindString(5, cycle.getImage());
            sqLiteStatementCycle.bindString(6, cycle.getStartStringFormatDate());
            sqLiteStatementCycle.bindString(7, cycle.getFinishStringFormatDate());
            if (cycle.getDefaultImg() == null) {
                sqLiteStatementCycle.bindNull(8);
            } else sqLiteStatementCycle.bindString(8, cycle.getDefaultImg());

            long idNewCycle = sqLiteStatementCycle.executeInsert();
            cycle.setId(idNewCycle);

            List<Workout> workoutList = workoutDao.getByParentId(idFrom);

            SQLiteStatement sqLiteStatementWorkout = sqLiteDatabase.compileStatement(sqlForWorkout);

            for (Workout workout : workoutList) {
                workout.setTemplate(false);
                workout.setDefaultType(false);
                sqLiteStatementWorkout.clearBindings();
                sqLiteStatementWorkout.bindString(2, workout.getTitle());
                sqLiteStatementWorkout.bindString(3, workout.getComment());
                sqLiteStatementWorkout.bindLong(4, workout.isWeekEven() ? 1 : 0);
                sqLiteStatementWorkout.bindLong(5, workout.isDefaultType() ? 1 : 0);
                sqLiteStatementWorkout.bindLong(6, workout.isTemplate() ? 1 : 0);
                sqLiteStatementWorkout.bindString(7, workout.getDay().toString());
                sqLiteStatementWorkout.bindString(8, workout.getStartStringFormatDate());
                sqLiteStatementWorkout.bindString(9, workout.getFinishStringFormatDate());
                sqLiteStatementWorkout.bindLong(10, idNewCycle);
                long l = sqLiteStatementWorkout.executeInsert();
                cycle.getWorkoutList().add(workout); //do setparent
                workout.setParentId(workout.getId());
                workout.setId(l);

            }

            for (Workout workout : workoutList) {

                List<Exercise> list = exerciseDao.getByParentId(workout.getParentId());
                SQLiteStatement sqLiteStatementExercise = sqLiteDatabase.compileStatement(sqlForExercise);

                for (Exercise exercise : list) {
                    exercise.setParentId(workout.getId());
                    exercise.setTemplate(false);
                    exercise.setDefaultType(false);
                    sqLiteStatementExercise.clearBindings();
                    sqLiteStatementExercise.bindString(2, exercise.getComment());
                    sqLiteStatementExercise.bindLong(3, exercise.getDescriptionId());
                    sqLiteStatementExercise.bindString(4, exercise.getTypeMuscle().toString());
                    sqLiteStatementExercise.bindLong(5, exercise.isDefaultType() ? 1 : 0);
                    sqLiteStatementExercise.bindLong(6, exercise.isTemplate() ? 1 : 0);
                    sqLiteStatementExercise.bindString(7, exercise.getStartStringFormatDate());
                    sqLiteStatementExercise.bindString(8, exercise.getFinishStringFormatDate());
                    sqLiteStatementExercise.bindLong(9, exercise.getParentId());
                    long l = sqLiteStatementExercise.executeInsert();
                    exercise.setParentId(exercise.getId());
                    exercise.setId(l);
                }

                for (Exercise exercise : list) {
                    List<Sets> setsList = setDao.getByParentId(exercise.getParentId());
                    SQLiteStatement sqLiteStatementSets = sqLiteDatabase.compileStatement(sqlForSets);

                    for (Sets sets : setsList) {
                        sqLiteStatementSets.clearBindings();
                        sqLiteStatementSets.bindDouble(3, sets.getWeight());
                        sqLiteStatementSets.bindLong(4, sets.getReps());
                        sqLiteStatementSets.bindString(5, sets.getStartStringFormatDate());
                        sqLiteStatementSets.bindString(6, sets.getFinishStringFormatDate());
                        sqLiteStatementSets.bindLong(7, exercise.getId());
                        sqLiteStatementSets.executeInsert();
                    }
                }
            }

            sqLiteDatabase.setTransactionSuccessful();

        } finally {
            sqLiteDatabase.endTransaction();
        }
        long finish = System.currentTimeMillis() - start;
        System.out.println(finish + " ms");

        if (cycle.getId() != 0) {
            return cycle;
        } else return null;
    }

    private void setActualDate(Cycle cycle) {
        Date start = cycle.getStartDate();
        Date finish = cycle.getFinishDate();
        long mSecond = finish.getTime() - start.getTime();
        cycle.setStartDate(new Date());
        cycle.setFinishDate(new Date(cycle.getStartDate().getTime() + mSecond));
    }
}
