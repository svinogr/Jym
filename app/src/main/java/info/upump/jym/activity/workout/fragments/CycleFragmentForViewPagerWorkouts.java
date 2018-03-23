package info.upump.jym.activity.workout.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.bd.IData;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.WorkoutFragmentLoader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CycleFragmentForViewPagerWorkouts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CycleFragmentForViewPagerWorkouts extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<List<Workout>> {
    private final static String ID_CYCLE = "id";
    private Cycle cycle;
    private List<Workout> workoutList = new ArrayList<>();
    private RecyclerView recyclerView;

    public CycleFragmentForViewPagerWorkouts() {
        // Required empty public constructor
    }


    public static CycleFragmentForViewPagerWorkouts newInstance(Cycle cycle) {
        CycleFragmentForViewPagerWorkouts fragment = new CycleFragmentForViewPagerWorkouts();
        Bundle args = new Bundle();
        args.putLong(ID_CYCLE, cycle.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_cycle_fragment_for_view_pager_workout, container, false);
        recyclerView = inflate.findViewById(R.id.cycle_fragment_for_view_pager_workouts_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(workoutList);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workoutAdapter);


        return inflate;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Loader<List<Workout>> onCreateLoader(int id, Bundle args) {
        WorkoutFragmentLoader workoutFragmentLoader = new WorkoutFragmentLoader(getContext(),WorkoutFragmentLoader.BY_PARENT_ID,cycle.getId());
        return workoutFragmentLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Workout>> loader, List<Workout> data) {
        workoutList.clear();
        workoutList.addAll(data);


    }

    @Override
    public void onLoaderReset(Loader<List<Workout>> loader) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            cycle = new Cycle();
            cycle.setId(getArguments().getLong(ID_CYCLE, 0));
        }
        getLoaderManager().initLoader(0, null, this);
    }
}
