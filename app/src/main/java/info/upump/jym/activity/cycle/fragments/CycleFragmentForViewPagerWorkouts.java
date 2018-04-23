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

public class CycleFragmentForViewPagerWorkouts extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<List<Workout>>, IItemFragment<Workout> {
    protected Cycle cycle;
    protected List<Workout> workoutList = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected FloatingActionButton addFab;
    protected WorkoutAdapter workoutAdapter;
    protected NestedScrollView nestedScrollView;

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
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_cycle_fragment_for_view_pager_workout, container, false);
        recyclerView = inflate.findViewById(R.id.cycle_fragment_for_view_pager_workouts_recycler);
        addFab = getActivity().findViewById(R.id.cycle_activity_detail_fab_main);
        addFab.setOnClickListener(this);
        nestedScrollView = inflate.findViewById(R.id.nested);

        setNestedScroll();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workoutAdapter);
        return inflate;
    }


    protected void setNestedScroll() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    // Scroll Down
                    if (addFab.isShown()) {
                        addFab.hide();
                    }
                } else if (scrollY <= oldScrollY) {
                    // Scroll Up
                    if (!addFab.isShown()) {
                        addFab.show();
                    }
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        String[] inputs = {getString(R.string.workout_dialog_create_new), getString(R.string.workout_dialog_сhoose)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.workout_dialog_title); // заголовок для диалога
        builder.setItems(inputs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                Intent intent = null;
                switch (item) {
                    case 1:
                        System.out.println(1);
                        intent = WorkoutActivityForChoose.createIntent(getContext());
                        getActivity().startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE);
                        break;
                    case 0:
                        System.out.println(2);
                        intent = WorkoutCreateActivity.createIntent(getContext());
                        getActivity().startActivityForResult(intent, Constants.REQUEST_CODE_CREATE);
                        break;
                }

            }
        });
        builder.setCancelable(true);
        builder.show();

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
        WorkoutDao workoutDao = new WorkoutDao(getContext());
        long id = workoutDao.copyFromTemplate(idItem, cycle.getId());
        Workout workout = workoutDao.getById(id);
        workoutList.add(workout);
        System.out.println("addChosenItem after " + workoutList.size());
        sortListByDay(workoutList);
        workoutAdapter.notifyDataSetChanged();
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
        WorkoutDao workoutDao = new WorkoutDao(getContext());
        Workout workout = workoutDao.getById(idItem);
        workout.setParentId(cycle.getId());
        workout.setTemplate(false);
        workoutDao.update(workout);
        workoutList.add(workout);
        sortListByDay(workoutList);
        workoutAdapter.notifyDataSetChanged();
    }
}