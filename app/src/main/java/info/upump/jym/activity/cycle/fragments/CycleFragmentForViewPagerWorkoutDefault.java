package info.upump.jym.activity.cycle.fragments;

import android.os.Bundle;

import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.entity.Cycle;

public class CycleFragmentForViewPagerWorkoutDefault extends CycleFragmentForViewPagerWorkouts {
    @Override
    protected void setAdapter() {
        workoutAdapter = new WorkoutAdapter(workoutList, WorkoutAdapter.DAY_DEFAULT);
    }

    public static CycleFragmentForViewPagerWorkoutDefault newInstance(Cycle cycle) {
        CycleFragmentForViewPagerWorkoutDefault fragment = new CycleFragmentForViewPagerWorkoutDefault();
        Bundle args = new Bundle();
        if (cycle != null) {
            args.putLong(Constants.ID, cycle.getId());
        }
        fragment.setArguments(args);
        return fragment;
    }
}
