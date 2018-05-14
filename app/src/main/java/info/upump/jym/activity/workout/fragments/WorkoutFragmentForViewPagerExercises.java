package info.upump.jym.activity.workout.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.ExerciseAdapter;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Workout;
import info.upump.jym.fragments.cycle.CRUD;
import info.upump.jym.loaders.ASTExercise;

import static info.upump.jym.activity.constant.Constants.ID;
public class WorkoutFragmentForViewPagerExercises extends Fragment implements IItemFragment<Exercise>{
    protected Workout workout;
    protected IChangeItem iChangeItem;
    protected RecyclerView recyclerView;
    protected ExerciseAdapter exerciseAdapter;
    protected List<Exercise> exerciseList = new ArrayList<>();
    protected ASTExercise astExercise;
    protected int index = -1;

    public WorkoutFragmentForViewPagerExercises() {
    }

    public static WorkoutFragmentForViewPagerExercises newInstance(Workout workout) {
        WorkoutFragmentForViewPagerExercises fragment = new WorkoutFragmentForViewPagerExercises();
        Bundle args = new Bundle();
        args.putLong(ID, workout.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAsyncTask();
        setAdapter();
    }

    private void createAsyncTask() {
        astExercise = new ASTExercise(getContext());
        astExercise.execute(Constants.LOADER_BY_PARENT_ID, (int) workout.getId());
        try {
            exerciseList = astExercise.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    protected void setAdapter() {
        exerciseAdapter = new ExerciseAdapter(exerciseList, ExerciseAdapter.INFO, (CRUD) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_workout_fragment_for_view_pager_exercises, container, false);
        recyclerView = inflate.findViewById(R.id.workout_fragment_for_view_pager_exercises_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(exerciseAdapter);
        return inflate;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        iChangeItem = (IChangeItem) context;
        iChangeItem.setInterfaceForItem(this);

        if (getArguments() != null) {
            workout = new Workout();
            workout.setId(getArguments().getLong(Constants.ID, 0));
        }
    }

    @Override
    public void clear() {
        exerciseList.clear();
        exerciseAdapter.notifyDataSetChanged();
        if (exerciseList.isEmpty()) {
            Toast.makeText(getContext(), R.string.toast_workout_delete_exercises, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addItem(Exercise exercise) {
        exerciseList.add(exercise);
        long id = exercise.getId();
        int index = -1;
        for (Exercise e : exerciseList) {
            if (e.getId() == id) {
                index = exerciseList.indexOf(e);
                break;
            }
        }
        if (index != -1) {
            exerciseAdapter.notifyItemInserted(index);
            recyclerView.smoothScrollToPosition(index);
        }
    }

    @Override
    public void delete(long id) {
        for (Exercise delExercise : exerciseList) {
            if (delExercise.getId() == id) {
                index = exerciseList.indexOf(delExercise);
                break;
            }
        }
        if (index != -1) {
            exerciseList.remove(index);
            exerciseAdapter.notifyItemRemoved(index);
            exerciseAdapter.notifyItemRangeChanged(index, exerciseList.size());
        }
    }

    @Override
    public void insertDeletedItem(long id) {
        ExerciseDao exerciseDao = new ExerciseDao(getContext());
        Exercise exercise = exerciseDao.getById(id);
        exerciseList.add(index, exercise);
        exerciseAdapter.notifyItemInserted(index);
    }

    @Override
    public void update(Exercise exercise) {
        long id = exercise.getId();
        int index = -1;
        for (Exercise updateExercise : exerciseList) {
            if (updateExercise.getId() == id) {
                index = exerciseList.indexOf(updateExercise);
                break;
            }
        }
        if (index != -1) {
            exerciseList.set(index, exercise);
            exerciseAdapter.notifyItemChanged(index);
            recyclerView.smoothScrollToPosition(index);
        }


    }
}
