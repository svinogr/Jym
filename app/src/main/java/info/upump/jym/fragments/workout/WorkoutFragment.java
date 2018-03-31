package info.upump.jym.fragments.workout;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.ITitleble;
import info.upump.jym.R;
import info.upump.jym.activity.cycle.CycleCreateActivity;
import info.upump.jym.activity.workout.WorkoutCreateActivity;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.WorkoutFragmentLoader;

import static info.upump.jym.activity.constant.Constants.LOADER_BY_TEMPLATE_TYPE;

public class WorkoutFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Workout>>, View.OnClickListener {
    private ITitleble iTitleble;
    private RecyclerView recyclerView;
    private WorkoutAdapter workoutAdapter;
    private List<Workout> workoutList = new ArrayList<>();
    private FloatingActionButton addFab;

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
        workoutAdapter = new WorkoutAdapter(workoutList, WorkoutAdapter.DEFAULT_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        iTitleble.setTitle(getResources().getString(R.string.workout_fragment_title));
        View inflate = inflater.inflate(R.layout.fragment_workout, container, false);

        recyclerView = inflate.findViewById(R.id.workout_fragment_recycler_view);
        addFab = inflate.findViewById(R.id.workout_fragment_fab_add);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workoutAdapter);
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

        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(0, null, this);
        iTitleble = (ITitleble) context;
    }

    @Override
    public Loader<List<Workout>> onCreateLoader(int id, Bundle args) {
        WorkoutFragmentLoader workoutFragmentLoader = new WorkoutFragmentLoader(getContext(), LOADER_BY_TEMPLATE_TYPE);
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
        switch (v.getId()){
            case R.id.workout_fragment_fab_add:
                addItem();
                break;
        }

    }
    private void addItem() {
        //TODO сделать вызов для резалт активити, типа красиво вставляем
      Intent intent = WorkoutCreateActivity.createIntent(getContext());
      startActivity(intent);
    }
}
