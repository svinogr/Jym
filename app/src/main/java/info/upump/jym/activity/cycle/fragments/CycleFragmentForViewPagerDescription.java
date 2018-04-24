package info.upump.jym.activity.cycle.fragments;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.activity.cycle.CycleCreateActivity;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;

import static info.upump.jym.activity.constant.Constants.ID;


public class CycleFragmentForViewPagerDescription extends Fragment implements View.OnClickListener {
    private Cycle cycle;
    private TextView startTextData, finishTextData;
    private TextView description;
    private FloatingActionButton editBtn;
    private IChangeItem iChangeItem;
    private ImageView transitionImageView;

    public CycleFragmentForViewPagerDescription() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CycleFragmentForViewPagerDescription newInstance(Cycle cycle) {
        CycleFragmentForViewPagerDescription fragment = new CycleFragmentForViewPagerDescription();
        Bundle args = new Bundle();
       /* if (cycle.getId() >0) {
            args.putLong(Constants.ID, cycle.getId());
            args.putString(START_DATA, cycle.getStartStringFormatDate());
            args.putString(FINISH_DATA, cycle.getFinishStringFormatDate());
            args.putString(COMMENT, cycle.getComment());
        }*/
        args.putLong(ID, cycle.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cycle = getItemFromBundle();
        }
     /*   if (savedInstanceState != null) {
            cycle.setStartDate(savedInstanceState.getString(START_DATA));
            cycle.setFinishDate(savedInstanceState.getString(FINISH_DATA));
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_cycle_fragment_for_view_pager_description, container, false);
        startTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_edit_start);
        finishTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_edit_finish);
        description = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_edit_web);
        editBtn = getActivity().findViewById(R.id.cycle_activity_detail_fab_main);
        editBtn.setOnClickListener(this);
        transitionImageView =  getActivity().findViewById(R.id.cycle_activity_detail_edit_image_view);
     /*   startDataLabel.setText(R.string.label_start_cycle);
        finishDataLabel.setText(R.string.label_finish_cycle);*/
        description.setText(cycle.getComment());
        startTextData.setText(cycle.getStartStringFormatDate());
        finishTextData.setText(cycle.getFinishStringFormatDate());
        return inflate;
    }

    private Cycle getItemFromBundle() {
      /*  Cycle cycle = new Cycle();
        cycle.setId(getArguments().getLong(ID, 0));
        if (cycle.getId() > 0) {
            System.out.println(12);
            cycle.setStartDate(getArguments().getString(START_DATA));
            cycle.setFinishDate(getArguments().getString(FINISH_DATA));
            cycle.setComment(getArguments().getString(COMMENT));
            cycle.setTitle(getArguments().getString(TITLE));
        }*/
        long id = getArguments().getLong(ID);
        CycleDao cycleDao = new CycleDao(getContext());
        Cycle cycle = cycleDao.getById(id);
        return cycle;
    }

   /* @Override
    public void onClick(View v) {
        Calendar newCalendar = null;
        DatePickerDialog j = null;
        switch (v.getId()) {
            case R.id.cycle_fragment_for_view_pager_description_data_edit_start:
                newCalendar = Calendar.getInstance();
                newCalendar.setTime(cycle.getStartDate());
                j = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        cycle.setStartDate(newDate.getTime());
                        startTextData.setText(cycle.getStartStringFormatDate());
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                j.show();

                break;
            case R.id.cycle_fragment_for_view_pager_description_data_edit_finish:
                newCalendar = Calendar.getInstance();
                newCalendar.setTime(cycle.getFinishDate());
                j = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        cycle.setFinishDate(newDate.getTime());
                        finishTextData.setText(cycle.getFinishStringFormatDate());
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                j.show();
                break;
        }
    }
*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println("atach");
        iChangeItem = (IChangeItem) context;

    }

    /*@Override
    public Cycle getChangeableItem() {
        cycle.setComment(description.getText().toString());
        return cycle;
    }
*/
 /*   @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(START_DATA, startTextData.getText().toString());
        outState.putString(FINISH_DATA, finishTextData.getText().toString());
    }*/

    @Override
    public void onResume() {
        System.out.println("resume");
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Intent intent = CycleCreateActivity.createIntent(getContext(), cycle);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = transitionImageView;
            String transitionNameIm = "cycle_activity_detail_edit_image_view";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            getActivity(),
                    Pair.create(sharedViewIm, transitionNameIm));
            getActivity().startActivity(intent, transitionActivityOptions.toBundle());
        } else
            getActivity().startActivity(intent);

    }
}
