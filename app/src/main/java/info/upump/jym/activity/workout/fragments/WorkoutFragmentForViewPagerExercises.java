package info.upump.jym.activity.workout.fragments;


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
import java.util.List;

import info.upump.jym.IControlFragment;
import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.exercise.ExerciseActivityForChoose;
import info.upump.jym.activity.workout.WorkoutActivityForChoose;
import info.upump.jym.activity.workout.WorkoutCreateActivity;
import info.upump.jym.adapters.ExerciseAdapter;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.ExerciseFragmentLoader;

import static info.upump.jym.activity.constant.Constants.ID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragmentForViewPagerExercises#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragmentForViewPagerExercises extends Fragment implements IItemFragment<Workout>,
        LoaderManager.LoaderCallbacks<List<Exercise>> {
   protected Workout workout;
   protected IChangeItem iChangeItem;
   protected RecyclerView recyclerView;
//   protected FloatingActionButton addFab;
   protected ExerciseAdapter exerciseAdapter;
   protected List<Exercise> exerciseList = new ArrayList<>();
//   protected NestedScrollView nestedScrollView;

    public WorkoutFragmentForViewPagerExercises() {
        // Required empty public constructor
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
//        if (getArguments() != null) {
//
//        }
       setAdapter();
    }

    protected void setAdapter(){
        exerciseAdapter = new ExerciseAdapter(exerciseList, ExerciseAdapter.INFO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_workout_fragment_for_view_pager_exercises, container, false);
//        addFab = getActivity().findViewById(R.id.workout_fragment_for_view_pager_exercises_fab_main);
        recyclerView = inflate.findViewById(R.id.workout_fragment_for_view_pager_exercises_recycler);
//        nestedScrollView = inflate.findViewById(R.id.nested);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(exerciseAdapter);

//        addFab.setOnClickListener(this);
//        setNestedScroll();

        return inflate;
    }
//
//    protected void setNestedScroll() {
//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY > oldScrollY) {
//                    // Scroll Down
//                    if (addFab.isShown()) {
//                        addFab.hide();
//                    }
//                } else if (scrollY <= oldScrollY) {
//                    // Scroll Up
//                    if (!addFab.isShown()) {
//                        addFab.show();
//                    }
//                }
//
//            }
//        });
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        iChangeItem = (IChangeItem) context;
        iChangeItem.setInterfaceForItem(this);

        if (getArguments() != null) {
            workout = new Workout();
            workout.setId(getArguments().getLong(Constants.ID, 0));
        }

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void addChosenItem(long idItem) {
        ExerciseDao exerciseDao = new ExerciseDao(getContext());
        long id = exerciseDao.copyFromTemplate(idItem, workout.getId());
        Exercise exercise = exerciseDao.getById(id);
        exerciseList.add(exercise);
        exerciseAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean clear() {
        WorkoutDao workoutDao = new WorkoutDao(getContext());
        boolean clear = workoutDao.clear(workout.getId());
        if (clear) {
            exerciseList.clear();
            exerciseAdapter.notifyDataSetChanged();
            return true;
        } else return false;
    }

    @Override
    public void addItem(long longExtra) {
    }


    @Override
    public Loader<List<Exercise>> onCreateLoader(int id, Bundle args) {
        ExerciseFragmentLoader exerciseFragmentLoader = new ExerciseFragmentLoader(getContext(), Constants.LOADER_BY_PARENT_ID, workout.getId());
        return exerciseFragmentLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Exercise>> loader, List<Exercise> data) {
        exerciseList.clear();
        exerciseList.addAll(data);
        exerciseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Exercise>> loader) {
    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent = ExerciseActivityForChoose.createIntent(getContext());
//        getActivity().startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE);
//    }
}
