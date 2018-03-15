package info.upump.jym.temp;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;
import info.upump.jym.entity.Workout;

/**
 * Created by explo on 14.03.2018.
 */

public class InflaiterDB {
    private Context context;

    public InflaiterDB(Context context) {
        this.context = context;
    }

    private Cycle createCycle() {
        return null;
    }

    private Workout createWorkout() {
        return null;
    }

    private List<Exercise> createBasicExercise() {
        TypeMuscle[] values = TypeMuscle.values();
        List<Exercise> exerciseList = new ArrayList<>();
        for (TypeMuscle v : values) {
            for (int i = 0; i < 7; i++) {
                Exercise exercise = new Exercise();
                exercise.setTitle(context.getResources().getString(v.getName()) + i);
                exercise.setComment("комент " + i);
                exercise.setTypeMuscle(v);
                exercise.setDefaultType(true);
                exercise.setImg("картинка " + i);
                exercise.setStartDate(new Date());
                exercise.setFinishDate(new Date());
                exerciseList.add(exercise);
            }
        }


        return exerciseList;
    }

    public void insertInBasicExercise() {
        ExerciseDao exerciseDao = new ExerciseDao(context);
        List<Exercise> exerciseList = createBasicExercise();
        for (Exercise exercise : exerciseList) {
            exerciseDao.create(exercise);
        }

    }

}
