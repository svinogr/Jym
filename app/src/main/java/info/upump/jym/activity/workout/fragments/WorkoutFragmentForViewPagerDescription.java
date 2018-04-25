package info.upump.jym.activity.workout.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Day;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.*;


public class WorkoutFragmentForViewPagerDescription extends Fragment implements IDescriptionFragment<Workout> {
    private Workout workout;
    private IChangeItem iChangeItem;
    private TextView description, day;
    private String[] nameOfValues;
    //    private CollapsingToolbarLayout collapsingToolbarLayout;
    //    private Spinner day;
//    private AppBarLayout appBarLayout;


    public WorkoutFragmentForViewPagerDescription() {
        // Required empty public constructor
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
            System.out.println(workout);
        }
    }

    private Workout getItemFromBundle() {
        long id = getArguments().getLong(ID);
        WorkoutDao workoutDao = new WorkoutDao(getContext());
        workout = workoutDao.getById(id);
        return workout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_workout_fragment_for_view_pager_description, container, false);
        description = inflate.findViewById(R.id.workout_fragment_for_view_pager_description_web);
//        collapsingToolbarLayout = getActivity().findViewById(R.id.workout_activity_detail_edit_collapsing);
        day = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_day);
//        appBarLayout = getActivity().findViewById(R.id.workout_activity_detail_edit_appbar);
//        imageView = getActivity().findViewById(R.id.workout_activity_detail_edit_image_view);


//        ArrayAdapter<String> dayArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nameOfValues);
//        day.setAdapter(dayArrayAdapter);
//        day.setSelection(workout.getDay().ordinal());
//        title.setText(workout.getTitle());
        description.setText(workout.getComment());
//        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                       int arg2, long arg3) {
//                setPic(arg2);
//                appBarLayout.setExpanded(true);
//            }
//
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });

//        title.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                handler.removeMessages(100);
//                if ((title.getText().toString().trim()).equals("")) {
//                    handler.sendMessageDelayed(handler.obtainMessage(100, title.getHint()), 250);
//                } else handler.sendMessageDelayed(handler.obtainMessage(100, title.getText()), 250);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        creteViewFrom();
        return inflate;
    }

    private void creteViewFrom() {
        day.setText(nameOfValues[workout.getDay().ordinal()]);
        description.setText(workout.getComment());


    }
//
//    private void setPic(int s) {
//        Bitmap bitmap = Bitmap.createBitmap(100, 100,
//                Bitmap.Config.ARGB_8888);
//        bitmap.eraseColor(getResources().getColor(getDay(s).getColor()));
//        imageView.setImageBitmap(bitmap);
//
//    }
//
//    @Override
//    public Workout getChangeableItem() {
//        Day day = getDay(this.day.getSelectedItemPosition());
//        workout.setDay(day);
//        workout.setTitle(title.getText().toString());
//        workout.setComment(description.getText().toString());
//        return workout;
//    }

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

//    private Day getDay(int i) {
//        Day[] values = Day.values();
//        return values[i];
//    }

    @Override
    public Workout getChangeableItem() {
        return null;
    }

}
