package info.upump.jym.activity.workout.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Day;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.ID;


public class WorkoutFragmentForViewPagerDescription extends Fragment implements IDescriptionFragment<Workout> {
    private Workout workout;
    private IChangeItem iChangeItem;
    private TextView description, day;
    private String[] nameOfValues;
    private TextView enebWeekLabel;

    public WorkoutFragmentForViewPagerDescription() {
    }

    public static WorkoutFragmentForViewPagerDescription newInstance(Workout workout) {
        WorkoutFragmentForViewPagerDescription fragment = new WorkoutFragmentForViewPagerDescription();
        Bundle args = new Bundle();
        args.putLong(ID, workout.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nameOfValues = getNameOfDays();
        if (getArguments() != null) {
            getItemFromBundle();
        }
    }

    private Workout getItemFromBundle() {
        long id = getArguments().getLong(ID);
        WorkoutDao workoutDao = WorkoutDao.getInstance(getContext(), null);
        workout = workoutDao.getById(id);
        return workout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_workout_fragment_for_view_pager_description, container, false);
        description = inflate.findViewById(R.id.workout_fragment_for_view_pager_description_web);
        day = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_day);
        enebWeekLabel = inflate.findViewById(R.id.workout_fragment_for_view_pager_description_label_switch);
        description.setText(workout.getComment());
        creteViewFrom();
        return inflate;
    }

    private void creteViewFrom() {
        day.setText(nameOfValues[workout.getDay().ordinal()]);
        description.setText(workout.getComment());
        if(workout.isWeekEven()){
            enebWeekLabel.setVisibility(View.VISIBLE);
        } else enebWeekLabel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateItem(Workout object) {
     workout = object;
     creteViewFrom();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iChangeItem = (IChangeItem) context;
        iChangeItem.setInterfaceForDescription(this);
    }

    private String[] getNameOfDays() {
        Day[] values = Day.values();
        String[] nameOfValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            nameOfValues[i] = getResources().getString(values[i].getName());
        }
        return nameOfValues;
    }

}
