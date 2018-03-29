package info.upump.jym.activity.cycle.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
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
import info.upump.jym.activity.workout.WorkoutActivityForChoose;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.WorkoutFragmentLoader;

public class CycleFragmentForViewPagerWorkouts extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<List<Workout>>, IItemFragment<Workout> {
    private final static String ID_CYCLE = "id";
    private Cycle cycle;
    private List<Workout> workoutList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton addFab;
    private WorkoutAdapter workoutAdapter;

    public CycleFragmentForViewPagerWorkouts() {
        // Required empty public constructor
    }


    public static CycleFragmentForViewPagerWorkouts newInstance(Cycle cycle) {
        CycleFragmentForViewPagerWorkouts fragment = new CycleFragmentForViewPagerWorkouts();
        Bundle args = new Bundle();
        if (cycle != null) {
            args.putLong(ID_CYCLE, cycle.getId());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workoutAdapter = new WorkoutAdapter(workoutList, WorkoutAdapter.DAY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_cycle_fragment_for_view_pager_workout, container, false);
        recyclerView = inflate.findViewById(R.id.cycle_fragment_for_view_pager_workouts_recycler);
        addFab = inflate.findViewById(R.id.cycle_activity_detail_fab_main);
        addFab.setOnClickListener(this);
        NestedScrollView nestedScrollView = inflate.findViewById(R.id.nested);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workoutAdapter);
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



    /*    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                System.out.println("addOnScrollListener recyclerView");
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
        });*/
        return inflate;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), WorkoutActivityForChoose.class);
        getActivity().startActivityForResult(intent, WorkoutActivityForChoose.CHOOSE_WORKOUT);
    }


    @Override
    public Loader<List<Workout>> onCreateLoader(int id, Bundle args) {
        WorkoutFragmentLoader workoutFragmentLoader = new WorkoutFragmentLoader(getContext(), WorkoutFragmentLoader.BY_PARENT_ID, cycle.getId());
        return workoutFragmentLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Workout>> loader, List<Workout> data) {
        workoutList.clear();
        workoutList.addAll(data);
        sortListByDay(workoutList);
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
            cycle.setId(getArguments().getLong(ID_CYCLE, 0));
        }
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void addChosenItem(long idItem) {
        WorkoutDao workoutDao = new WorkoutDao(getContext());
        long id = workoutDao.copyFromTemplate(idItem, cycle.getId());
        Workout workout = workoutDao.getById(idItem);
        System.out.println("addChosenItem before "+ workoutList.size());
        System.out.println(workout);

        workoutList.add(workout);
        System.out.println("addChosenItem after "+ workoutList.size());
        sortListByDay(workoutList);
        workoutAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean clear() {
        CycleDao cycleDao = new CycleDao(getContext());
        if(cycleDao.clear(cycle)){
            workoutList.clear();
            workoutAdapter.notifyDataSetChanged();
            return true;
        }else return false;

    }

    @Override
    public List<Workout> getListItem() {
        return workoutList;
    }
}