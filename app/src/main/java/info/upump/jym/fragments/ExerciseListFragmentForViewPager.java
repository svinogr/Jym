package info.upump.jym.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.ExerciseAdapter;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;
import info.upump.jym.loaders.ExerciseLoader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseListFragmentForViewPager#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseListFragmentForViewPager extends Fragment implements LoaderManager.LoaderCallbacks<List<Exercise>> {

    private TypeMuscle typeMuscle;
    private static final String TYPE_MUSCLE = "type muscle";
    private List<Exercise> exerciseList = new ArrayList<>();
    private ExerciseAdapter exerciseAdapter;


    public ExerciseListFragmentForViewPager() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ExerciseListFragmentForViewPager newInstance(TypeMuscle typeMuscle) {
        ExerciseListFragmentForViewPager fragment = new ExerciseListFragmentForViewPager();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.exercise_list_fragment_for_view_pager, container, false);
        RecyclerView recyclerView = inflate.findViewById(R.id.exercise_fragment_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        exerciseAdapter = new ExerciseAdapter(exerciseList);
        recyclerView.setAdapter(exerciseAdapter);
        return inflate;
    }

    @Override
    public Loader<List<Exercise>> onCreateLoader(int id, Bundle args) {
        ExerciseLoader exerciseLoader = new ExerciseLoader(getContext(), typeMuscle);
        return exerciseLoader;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            String t = getArguments().getString(TYPE_MUSCLE);
            typeMuscle = TypeMuscle.valueOf(t);
        }
        getLoaderManager().initLoader(0, null, this);
    }


}
