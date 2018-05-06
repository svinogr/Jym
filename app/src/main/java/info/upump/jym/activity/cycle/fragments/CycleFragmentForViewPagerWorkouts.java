package info.upump.jym.activity.cycle.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Workout;
import info.upump.jym.fragments.cycle.CRUD;
import info.upump.jym.loaders.ASTWorkout;

import static info.upump.jym.activity.constant.Constants.ID;

public class CycleFragmentForViewPagerWorkouts extends Fragment implements  IItemFragment<Workout> {
    protected Cycle cycle;
    protected List<Workout> workoutList = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected WorkoutAdapter workoutAdapter;
    protected ASTWorkout astWorkout;
    protected int index = -1;

    public CycleFragmentForViewPagerWorkouts() {
        // Required empty public constructor
    }

    public static CycleFragmentForViewPagerWorkouts newInstance(Cycle cycle) {
        CycleFragmentForViewPagerWorkouts fragment = new CycleFragmentForViewPagerWorkouts();
        Bundle args = new Bundle();
        if (cycle != null) {
            args.putLong(ID, cycle.getId());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAsyncTask();
        sort(workoutList);
        setAdapter();
    }

    private void createAsyncTask() {
        astWorkout = new ASTWorkout(getContext());
        astWorkout.execute(Constants.LOADER_BY_PARENT_ID, (int) cycle.getId());
        try {
            workoutList = astWorkout.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    protected void setAdapter() {
        workoutAdapter = new WorkoutAdapter(workoutList, WorkoutAdapter.DAY, (CRUD) getActivity());
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

    private void sortListByDay(List<Workout> list) {
        Collections.sort(list, new Comparator<Workout>() {
            @Override
            public int compare(Workout o1, Workout o2) {
                return o1.getDay().ordinal() < o2.getDay().ordinal() ? -1 : o1.getDay().ordinal() == o2.getDay().ordinal() ? 0 : 1;
            }
        });
    }

    private void sort(List<Workout> list) {
        List<Workout> workoutListEven = new ArrayList<>();
        List<Workout> workoutListNotEven = new ArrayList<>();
        for (Workout w : list) {
            if (w.isWeekEven()) {
                workoutListEven.add(w);
            } else workoutListNotEven.add(w);
        }
        sortListByDay(workoutListEven);
        sortListByDay(workoutListNotEven);
        list.clear();
        list.addAll(workoutListNotEven);
        list.addAll(workoutListEven);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        IChangeItem iChangeItem = (IChangeItem) context;
        iChangeItem.setInterfaceForItem(this);

        if (getArguments() != null) {
            cycle = new Cycle();
            cycle.setId(getArguments().getLong(ID, 0));
        }

    }

    @Override
    public void clear() {
        workoutList.clear();
        workoutAdapter.notifyDataSetChanged();
        if (workoutList.isEmpty()) {
            Toast.makeText(getContext(), R.string.toast_cycle_delete_workouts, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addItem(Workout workout) {
        workoutList.add(workout);
        sort(workoutList);
        long id = workout.getId();
        int index = -1;
        for (Workout w : workoutList) {
            if (w.getId() == id) {
                index = workoutList.indexOf(w);
                break;
            }
        }
        if (index != -1) {
            workoutAdapter.notifyItemInserted(index);
            recyclerView.smoothScrollToPosition(index);
        }
    }

    @Override
    public void delete(long id) {
        for (Workout delWorkout : workoutList) {
            if (delWorkout.getId() == id) {
                index = workoutList.indexOf(delWorkout);
                break;
            }
        }
        if (index != -1) {
            workoutList.remove(index);
            workoutAdapter.notifyItemRemoved(index);
            workoutAdapter.notifyItemRangeChanged(index, workoutList.size());
        }
    }

    @Override
    public void insertDeletedItem(long id) {
        WorkoutDao workoutDao = new WorkoutDao(getContext());
        Workout workout = workoutDao.getById(id);
        workoutList.add(index, workout);
        workoutAdapter.notifyItemInserted(index);
    }

    @Override
    public void update(Workout workout) {
        long id = workout.getId();
        int index = -1;
        boolean f = false;
        for (Workout updateWorkout : workoutList) {
            if (updateWorkout.getId() == id) {
                index = workoutList.indexOf(updateWorkout);
                if (updateWorkout.isWeekEven() != workout.isWeekEven()) {
                    f = true;
                }
                break;
            }
        }
        if (index != -1) {
            workoutList.set(index, workout);
            workoutAdapter.notifyItemChanged(index);
            recyclerView.smoothScrollToPosition(index);
        }

        if (f) {
            sort(workoutList);
            for (Workout updateWorkout : workoutList) {
                if (updateWorkout.getId() == id) {
                    index = workoutList.indexOf(updateWorkout);
                    break;
                }
            }
            if (index != -1) {
                workoutList.set(index, workout);
                workoutAdapter.notifyItemChanged(index);
                recyclerView.smoothScrollToPosition(index);
            }
        }
    }
}