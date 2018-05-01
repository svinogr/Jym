package info.upump.jym.activity.cycle.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.workout.WorkoutActivityForChoose;
import info.upump.jym.activity.workout.WorkoutCreateActivity;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.WorkoutFragmentLoader;

import static info.upump.jym.activity.constant.Constants.LOADER_BY_PARENT_ID;

public class CycleFragmentForViewPagerWorkouts extends Fragment implements LoaderManager.LoaderCallbacks<List<Workout>>, IItemFragment<Workout> {
    protected Cycle cycle;
    protected List<Workout> workoutList = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected WorkoutAdapter workoutAdapter;

    public CycleFragmentForViewPagerWorkouts() {
        // Required empty public constructor
    }

    public static CycleFragmentForViewPagerWorkouts newInstance(Cycle cycle) {
        CycleFragmentForViewPagerWorkouts fragment = new CycleFragmentForViewPagerWorkouts();
        Bundle args = new Bundle();
        if (cycle != null) {
            args.putLong(Constants.ID, cycle.getId());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAdapter();
    }

    protected void setAdapter() {
        System.out.println("CycleFragmentForViewPagerWorkout");
        workoutAdapter = new WorkoutAdapter(workoutList, WorkoutAdapter.DAY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_cycle_fragment_for_view_pager_workout, container, false);
        recyclerView = inflate.findViewById(R.id.cycle_fragment_for_view_pager_workouts_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workoutAdapter);
        return inflate;
    }

    @Override
    public Loader<List<Workout>> onCreateLoader(int id, Bundle args) {
        WorkoutFragmentLoader workoutFragmentLoader = new WorkoutFragmentLoader(getContext(), LOADER_BY_PARENT_ID, cycle.getId());
        return workoutFragmentLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Workout>> loader, List<Workout> data) {
        workoutList.clear();
        workoutList.addAll(data);
        sortListByDay(workoutList);
        workoutAdapter.notifyDataSetChanged();
    }

    private void sortListByDay(List<Workout> list) {
        Collections.sort(list, new Comparator<Workout>() {
            @Override
            public int compare(Workout o1, Workout o2) {

                return o1.getDay().ordinal() < o2.getDay().ordinal() ? -1 : o1.getDay().ordinal() == o2.getDay().ordinal() ? 0 : 1;
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<List<Workout>> loader) {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        IChangeItem iChangeItem = (IChangeItem) context;
        iChangeItem.setInterfaceForItem(this);

        if (getArguments() != null) {
            cycle = new Cycle();
            cycle.setId(getArguments().getLong(Constants.ID, 0));
        }
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void addChosenItem(long idItem) {
        sortListByDay(workoutList);
    }

    @Override
    public boolean clear() {
        CycleDao cycleDao = new CycleDao(getContext());
        boolean clear = cycleDao.clear(cycle.getId());
        if (clear) {
            workoutList.clear();
            workoutAdapter.notifyDataSetChanged();
            return true;
        } else return false;
    }

    @Override
    public void addItem(long idItem) {
//        System.out.println("id cycle "+cycle.getId());
//        WorkoutDao workoutDao = new WorkoutDao(getContext());
//        Workout workout = workoutDao.getById(idItem);
//        System.out.println(idItem+" "+ workout);
//        workout.setParentId(cycle.getId());
//        workoutDao.update(workout);
//        workoutList.add(workout);
        sortListByDay(workoutList);
       // workoutAdapter.notifyDataSetChanged();
    }
}