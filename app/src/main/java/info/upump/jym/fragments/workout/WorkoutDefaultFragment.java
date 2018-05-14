/*
package info.upump.jym.fragments.workout;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.ASTWorkout;
import info.upump.jym.loaders.WorkoutFragmentLoader;

import static info.upump.jym.activity.constant.Constants.LOADER_BY_DEFAULT_TYPE;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_USER_TYPE;


public class WorkoutDefaultFragment extends WorkoutFragment {

    public static WorkoutDefaultFragment newInstance() {
        WorkoutDefaultFragment fragment = new WorkoutDefaultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void createAdapter() {
        workoutAdapter = new WorkoutAdapter(workoutList, LOADER_BY_DEFAULT_TYPE, null);
    }


    @Override
    protected void setFab() {
        addFab.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void setTitle() {
        iTitleble.setTitle(getResources().getString(R.string.workout_fragment_default_title));
    }

    @Override
    protected void createAsyncTask() {
        astWorkout = new ASTWorkout(getContext());
        astWorkout.execute(LOADER_BY_DEFAULT_TYPE);
    }
*/
/*   @Override
    public Loader<List<Workout>> onCreateLoader(int id, Bundle args) {
        WorkoutFragmentLoader workoutFragmentLoader = new WorkoutFragmentLoader(getContext(), Constants.LOADER_BY_DEFAULT_TYPE);
        return workoutFragmentLoader;
    }*//*

}
*/
