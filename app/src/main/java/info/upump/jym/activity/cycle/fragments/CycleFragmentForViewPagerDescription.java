package info.upump.jym.activity.cycle.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;

import static info.upump.jym.activity.constant.Constants.COMMENT;
import static info.upump.jym.activity.constant.Constants.FINISH_DATA;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.START_DATA;
import static info.upump.jym.activity.constant.Constants.TITLE;


public class CycleFragmentForViewPagerDescription extends Fragment implements View.OnClickListener, IDescriptionFragment {
    private Cycle cycle;
    private TextView startDataLabel, finishDataLabel, startTextData, finishTextData;
    private CycleDao cycleDao;
    private EditText description, title;
    private IChangeItem iChangeItem;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                collapsingToolbarLayout.setTitle(msg.obj.toString());
            }
        }
    };

    public CycleFragmentForViewPagerDescription() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CycleFragmentForViewPagerDescription newInstance(Cycle cycle) {
        CycleFragmentForViewPagerDescription fragment = new CycleFragmentForViewPagerDescription();
        Bundle args = new Bundle();
        args.putString(TITLE, cycle.getTitle());
        if (cycle.getId() >0) {
            args.putLong(Constants.ID, cycle.getId());
            args.putString(START_DATA, cycle.getStartStringFormatDate());
            args.putString(FINISH_DATA, cycle.getFinishStringFormatDate());
            args.putString(COMMENT, cycle.getComment());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           cycle =  getItemFromBundle();
        }
        if(savedInstanceState != null){
            cycle.setStartDate(savedInstanceState.getString(START_DATA));
            cycle.setFinishDate(savedInstanceState.getString(FINISH_DATA));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_cycle_fragment_for_view_pager_description, container, false);
        startDataLabel = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_label_edit_start);
        finishDataLabel = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_label_edit_finish);
        startTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_edit_start);
        finishTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_edit_finish);
        description = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_edit_web);
        title = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_edit_title);

        startTextData.setOnClickListener(this);
        finishTextData.setOnClickListener(this);
        title.setText(cycle.getTitle());
        startDataLabel.setText(R.string.label_start_cycle);
        finishDataLabel.setText(R.string.label_finish_cycle);
        description.setText(cycle.getComment());
        startTextData.setText(cycle.getStartStringFormatDate());
        finishTextData.setText(cycle.getFinishStringFormatDate());
        collapsingToolbarLayout = getActivity().findViewById(R.id.cycle_activity_detail_edit_collapsing);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(100);
                if ((title.getText().toString().trim()).equals("")) {
                    handler.sendMessageDelayed(handler.obtainMessage(100, title.getHint()), 250);
                } else handler.sendMessageDelayed(handler.obtainMessage(100, title.getText()), 250);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return inflate;
    }

    private Cycle getItemFromBundle() {
        Cycle cycle = new Cycle();
        cycle.setId(getArguments().getLong(ID, 0));
      //  String title = getArguments().getString(TITLE);
        if (cycle.getId() > 0) {
            System.out.println(12);
            cycle.setStartDate(getArguments().getString(START_DATA));
            cycle.setFinishDate(getArguments().getString(FINISH_DATA));
            cycle.setComment(getArguments().getString(COMMENT));
            cycle.setTitle(getArguments().getString(TITLE));
        }/* else {
            cycle.setStartDate(new Date());
            cycle.setFinishDate(new Date());
            cycle.setComment("");
            cycle.setTitle(title);
        }*/
        return cycle;
    }

    @Override
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iChangeItem = (IChangeItem) context;
        iChangeItem.setInterfaceForDescription(this);
    }

    @Override
    public Cycle getChangeableItem() {
        cycle.setTitle(title.getText().toString());
        cycle.setComment(description.getText().toString());
        return cycle;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(START_DATA,startTextData.getText().toString());
        outState.putString(FINISH_DATA,finishTextData.getText().toString());
    }
}
