package info.upump.jym.activity.workout.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.upump.jym.R;
import info.upump.jym.entity.Workout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragmentForViewPagerExercises#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragmentForViewPagerExercises extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public WorkoutFragmentForViewPagerExercises() {
        // Required empty public constructor
    }

    public static WorkoutFragmentForViewPagerExercises newInstance(Workout workout) {
        WorkoutFragmentForViewPagerExercises fragment = new WorkoutFragmentForViewPagerExercises();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_fragment_for_view_pager_exercises, container, false);
    }

}
