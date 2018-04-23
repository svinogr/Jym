package info.upump.jym.activity.workout.fragments;

import android.os.Bundle;

import info.upump.jym.adapters.ExerciseAdapter;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.ID;

/**
 * Created by explo on 06.04.2018.
 */

public class WorkoutFragmentForViewPagerExercisesDefault extends WorkoutFragmentForViewPagerExercises {

    public static WorkoutFragmentForViewPagerExercisesDefault newInstance(Workout workout) {
        WorkoutFragmentForViewPagerExercisesDefault fragment = new WorkoutFragmentForViewPagerExercisesDefault();
        Bundle args = new Bundle();
        args.putLong(ID, workout.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setAdapter(){
        exerciseAdapter = new ExerciseAdapter(exerciseList, ExerciseAdapter.INFO_DEFAULT);
    }

    @Override
    protected void setNestedScroll() {
    }
}
