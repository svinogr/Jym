package info.upump.jym.activity.exercise;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.ExerciseAdapter;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;
import info.upump.jym.loaders.ASTExercise;

import static info.upump.jym.activity.constant.Constants.TYPE_MUSCLE;

public class ExerciseListFragmentForViewPagerChoose extends Fragment {
    private TypeMuscle typeMuscle;
    private List<Exercise> exerciseList = new ArrayList<>();
    private ExerciseAdapter exerciseAdapter;
    private RecyclerView recyclerView;
    private ASTExercise astExercise;

    public ExerciseListFragmentForViewPagerChoose() {
    }
    public static ExerciseListFragmentForViewPagerChoose newInstance(TypeMuscle typeMuscle) {
        ExerciseListFragmentForViewPagerChoose fragment = new ExerciseListFragmentForViewPagerChoose();
        Bundle args = new Bundle();
        args.putString(TYPE_MUSCLE, typeMuscle.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String t = getArguments().getString(TYPE_MUSCLE);
            typeMuscle = TypeMuscle.valueOf(t);
        }
        createAsyncTask();
        exerciseAdapter = new ExerciseAdapter(exerciseList, ExerciseAdapter.CHOOSE, null);
    }

    private void createAsyncTask() {
        astExercise = new ASTExercise(getContext());
        astExercise.execute(Constants.LOADER_BY_TEMPLATE_TYPE, typeMuscle.ordinal());
        try {
            exerciseList = astExercise.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_exercise_list_fragment_for_view_pager_choose, container, false);
        recyclerView = inflate.findViewById(R.id.exercise_list_fragment_for_view_pager_choose_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(exerciseAdapter);

        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            String t = getArguments().getString(TYPE_MUSCLE);
            typeMuscle = TypeMuscle.valueOf(t);
        }
    }
}
