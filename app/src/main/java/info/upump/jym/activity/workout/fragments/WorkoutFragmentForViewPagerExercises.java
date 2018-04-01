package info.upump.jym.activity.workout.fragments;


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
import info.upump.jym.bd.ExerciseDao;
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
        LoaderManager.LoaderCallbacks<List<Exercise>>, View.OnClickListener {
    private Workout workout;
    private IChangeItem iChangeItem;
    private RecyclerView recyclerView;
    private FloatingActionButton addFab;
    private ExerciseAdapter exerciseAdapter;
    private List<Exercise> exerciseList = new ArrayList<>();

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
        if (getArguments() != null) {

        }
        exerciseAdapter = new ExerciseAdapter(exerciseList, ExerciseAdapter.INFO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_workout_fragment_for_view_pager_exercises, container, false);
        addFab = inflate.findViewById(R.id.workout_fragment_for_view_pager_exercises_fab_main);
        recyclerView = inflate.findViewById(R.id.workout_fragment_for_view_pager_exercises_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(exerciseAdapter);

        addFab.setOnClickListener(this);

        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getArguments() != null) {
            workout = new Workout();
            workout.setId(getArguments().getLong(Constants.ID, 0));
        }
        iChangeItem = (IChangeItem) context;
        iChangeItem.setInterfaceForItem(this);
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void addChosenItem(long idItem) {
        ExerciseDao exerciseDao = new ExerciseDao(getContext());
        long id = exerciseDao.copyFromTemplate(idItem, workout.getId());
        Exercise exercise = exerciseDao.getById(id);
        exerciseList.add(exercise);
        System.out.println("addChosenItem after "+exerciseList.size());
        exerciseAdapter.notifyDataSetChanged();

        System.out.println("выбрана " + idItem);

    }

    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public void addItem(long longExtra) {

    }

    @Override
    public Loader<List<Exercise>> onCreateLoader(int id, Bundle args) {
        ExerciseFragmentLoader exerciseFragmentLoader = new ExerciseFragmentLoader(getContext(), Constants.LOADER_BY_PARENT_ID, workout.getId() );
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

    @Override
    public void onClick(View v) {
        Intent intent = ExerciseActivityForChoose.createIntent(getContext());
        getActivity().startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE);
      /*  switch (v.getId()){
            case R.id.workout_fragment_for_view_pager_exercises_fab_main:
                String[] inputs = {"Своe", "Выбрать готовоe"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Выберите путь"); // заголовок для диалога
                builder.setNeutralButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
                builder.setItems(inputs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        Intent intent = null;
                        switch (item) {
                            case 1:
                                System.out.println(1);
                                intent = ExerciseActivityForChoose.createIntent(getContext());
                                getActivity().startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE);
                                break;
                            case 0:
                                System.out.println(2);
                               // intent  = WorkoutCreateActivity.createIntent(getContext());
                                getActivity().startActivityForResult(intent, Constants.REQUEST_CODE_CREATE);
                                break;
                        }

                    }
                });
                builder.setCancelable(true);
                builder.show();
                break;
        }*/
    }
}
