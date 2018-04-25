package info.upump.jym.fragments.workout;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.ITitleble;
import info.upump.jym.R;
import info.upump.jym.activity.workout.WorkoutCreateActivity;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.WorkoutFragmentLoader;

import static info.upump.jym.activity.constant.Constants.LOADER_BY_USER_TYPE;

public class WorkoutFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Workout>>, View.OnClickListener {
    protected ITitleble iTitleble;
    protected RecyclerView recyclerView;
    protected WorkoutAdapter workoutAdapter;
    protected List<Workout> workoutList = new ArrayList<>();
    protected FloatingActionButton addFab;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    public static WorkoutFragment newInstance() {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        createAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setTitle();
        View inflate = inflater.inflate(R.layout.fragment_workout, container, false);

        recyclerView = inflate.findViewById(R.id.workout_fragment_recycler_view);
        addFab = inflate.findViewById(R.id.workout_fragment_fab_add);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workoutAdapter);
        setFab();

        return inflate;
    }

    protected void setTitle() {
        iTitleble.setTitle(getResources().getString(R.string.workout_fragment_title));
    }


    protected void createAdapter() {
        workoutAdapter = new WorkoutAdapter(workoutList, LOADER_BY_USER_TYPE);
    }

    protected void setFab() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    // Scroll Down
                    if (addFab.isShown()) {
                        addFab.hide();
                    }
                } else if (dy <= 0) {
                    // Scroll Up
                    if (!addFab.isShown()) {
                        addFab.show();
                    }
                }
            }
        });
        addFab.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(0, null, this);
        iTitleble = (ITitleble) context;
    }

    @Override
    public Loader<List<Workout>> onCreateLoader(int id, Bundle args) {
        WorkoutFragmentLoader workoutFragmentLoader = new WorkoutFragmentLoader(getContext(), LOADER_BY_USER_TYPE);
        return workoutFragmentLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Workout>> loader, List<Workout> data) {
        workoutList.clear();
        workoutList.addAll(data);
        workoutAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Workout>> loader) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.workout_fragment_fab_add:
                createNewItem();
                break;
        }
    }

    private void createNewItem() {
        Workout workout = new Workout();
        workout.setId(-1);
        Intent intent = WorkoutCreateActivity.createIntent(getContext(), workout);
        startActivity(intent);
    }
}
