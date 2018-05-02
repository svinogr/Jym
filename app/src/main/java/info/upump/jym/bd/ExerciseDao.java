package info.upump.jym.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.ExerciseDescription;
import info.upump.jym.entity.Sets;
import info.upump.jym.entity.TypeMuscle;


public class ExerciseDao extends DBDao implements IData<Exercise> {
    public ExerciseDao(Context context) {
        super(context);
    }

    private final String[] keys = new String[]{
            DBHelper.TABLE_KEY_ID,
            DBHelper.TABLE_KEY_COMMENT,
            DBHelper.TABLE_KEY_DESCRIPTION_ID,
            DBHelper.TABLE_KEY_TYPE_EXERCISE,
            DBHelper.TABLE_KEY_DEFAULT,
            DBHelper.TABLE_KEY_TEMPLATE,
            DBHelper.TABLE_KEY_START_DATE,
            DBHelper.TABLE_KEY_FINISH_DATE,
            DBHelper.TABLE_KEY_PARENT_ID};

    private ContentValues getContentValuesFrom(Exercise object) {
        ContentValues cv = new ContentValues();
        if (object.getId() != 0) {
            cv.put(DBHelper.TABLE_KEY_ID, object.getId());
        }
        cv.put(DBHelper.TABLE_KEY_COMMENT, object.getComment());
        cv.put(DBHelper.TABLE_KEY_DESCRIPTION_ID, object.getDescriptionId());
        cv.put(DBHelper.TABLE_KEY_TYPE_EXERCISE, object.getTypeMuscle().toString());
        cv.put(DBHelper.TABLE_KEY_DEFAULT, object.isDefaultType());
        cv.put(DBHelper.TABLE_KEY_TEMPLATE, object.isTemplate());
        if (object.getStartDate() == null) {
            object.setStartDate(new Date());
        }
        cv.put(DBHelper.TABLE_KEY_START_DATE, object.getStartStringFormatDate());
        if (object.getFinishDate() == null) {
            object.setFinishDate(new Date());
        }
        cv.put(DBHelper.TABLE_KEY_FINISH_DATE, object.getFinishStringFormatDate());
        cv.put(DBHelper.TABLE_KEY_PARENT_ID, object.getParentId());
        return cv;
    }

    private Exercise getExerciseFromCursor(Cursor cursor) {
        Exercise exercise = new Exercise();
        exercise.setId(cursor.getLong(0));
        exercise.setComment(cursor.getString(1));
        exercise.setDescriptionId(cursor.getInt(2));
        exercise.setTypeMuscle(TypeMuscle.valueOf(cursor.getString(3)));
        exercise.setDefaultType(cursor.getInt(4) == 1);
        exercise.setTemplate(cursor.getInt(5) == 1);
        exercise.setStartDate((cursor.getString(6)));
        exercise.setFinishDate((cursor.getString(7)));
        exercise.setParentId(cursor.getLong(8));
        return exercise;
    }

    @Override
    public List<Exercise> getAll() {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                keys, null, null, null, null, null);
        List<Exercise> exerciseList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = getExerciseFromCursor(cursor);
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        return exerciseList;
    }

    @Override
    public long create(Exercise object) {
        if (object.getExerciseDescription().getId() == 0) {
            ExerciseDescriptionDao exerciseDescriptionDao = new ExerciseDescriptionDao(context);
            long l = exerciseDescriptionDao.create(object.getExerciseDescription());
            object.setDescriptionId(l);
        }
        ContentValues cv = getContentValuesFrom(object);
        long id = sqLiteDatabase.insert(DBHelper.TABLE_EXERCISE, null, cv);
        return id;
    }

    @Override
    public boolean delete(Exercise object) {
        if (object.isDefaultType()) return false;
        boolean delChild = clear(object.getId());
        long id = 0;
        if (delChild) {
            id = sqLiteDatabase.delete(DBHelper.TABLE_EXERCISE, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        }
        return id != 0;
    }

    @Override
    public boolean clear(long id) {
        List<Sets> setsList;
        SetDao setDao = new SetDao(context);
        setsList = setDao.getByParentId(id);
        if (setsList.size() > 0) {
            for (Sets set : setsList) {
                if (!setDao.delete(set)) return false;
            }
        }
        return true;
    }


    @Override
    public boolean update(Exercise object) {
        ExerciseDescriptionDao exerciseDescriptionDao = new ExerciseDescriptionDao(context);
        exerciseDescriptionDao.update(object.getExerciseDescription());
        ContentValues cv = getContentValuesFrom(object);
        long id = sqLiteDatabase.update(DBHelper.TABLE_EXERCISE, cv, DBHelper.TABLE_KEY_ID + " = ?", new String[]{String.valueOf(object.getId())});
        return id > 0;
    }

    @Override
    public Exercise getById(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                keys, DBHelper.TABLE_KEY_ID + " = ? ", new String[]{String.valueOf(id)}, null, null, null);
        Exercise exercise = null;
        if (cursor.moveToFirst()) {
            do {
                exercise = getExerciseFromCursor(cursor);
            } while (cursor.moveToNext());
        }
        if (exercise != null) {
            ExerciseDescriptionDao exerciseDescriptionDao = new ExerciseDescriptionDao(context);
            ExerciseDescription exerciseDescription = exerciseDescriptionDao.getById(exercise.getDescriptionId());
            exercise.setExerciseDescription(exerciseDescription);
        }
        return exercise;
    }

    @Override
    public List<Exercise> getByParentId(long id) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                keys, DBHelper.TABLE_KEY_PARENT_ID + " =? ", new String[]{String.valueOf(id)}, null, null, null);

        return getListExercise(cursor);
    }

    public List<Exercise> getAllByTypeMuscle(TypeMuscle typeMuscle) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                keys, DBHelper.TABLE_KEY_TYPE_EXERCISE + " = ? ", new String[]{typeMuscle.toString()}, null, null, null);
        return getListExercise(cursor);
    }

    public List<Exercise> getAllByTypeMuscleTemplate(TypeMuscle typeMuscle) {
        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_EXERCISE,
                keys, DBHelper.TABLE_KEY_TYPE_EXERCISE + " = ? and " + DBHelper.TABLE_KEY_TEMPLATE + " = ?"
                , new String[]{typeMuscle.toString(), String.valueOf(1)},
                null, null, null);
        return getListExercise(cursor);
    }

    private List<Exercise> getListExercise(Cursor cursor) {
        List<Exercise> exerciseList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Exercise exercise = getExerciseFromCursor(cursor);
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }
        if (exerciseList.size() > 0) {
            ExerciseDescriptionDao exerciseDescriptionDao = new ExerciseDescriptionDao(context);
            for (Exercise exercise : exerciseList) {
                ExerciseDescription exerciseDescription = exerciseDescriptionDao.getById(exercise.getDescriptionId());
                exercise.setExerciseDescription(exerciseDescription);
            }
        }

        return exerciseList;
    }

    @Override
    public long copyFromTemplate(long idItem, long id) {
        SetDao setDao = new SetDao(context);
        Exercise exercise = getById(idItem);

        List<Sets> setsList = setDao.getByParentId(exercise.getId());

        exercise.setId(0);
        exercise.setParentId(id);
        exercise.setTemplate(false);
        exercise.setDefaultType(false);
        long idNewExercise = create(exercise);

        for (Sets sets : setsList) {
            setDao.copyFromTemplate(sets.getId(), idNewExercise);
        }

        return idNewExercise;
    }
}
