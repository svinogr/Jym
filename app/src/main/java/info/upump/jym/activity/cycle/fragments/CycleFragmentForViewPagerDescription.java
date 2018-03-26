package info.upump.jym.activity.cycle.fragments;


import android.app.Activity;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import info.upump.jym.R;
import info.upump.jym.activity.cycle.IChangeItem;
import info.upump.jym.activity.cycle.IDescriptionFragment;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;


public class CycleFragmentForViewPagerDescription extends Fragment implements View.OnClickListener, IDescriptionFragment {
    public final static String ID_CYCLE = "id";
    public final static String START_DATA = "start";
    public final static String FINISH_DATA = "finish";
    public final static String COMMENT = "description";
    private static final String TITLE = "title";
    private Cycle cycle;
    private TextView startDataLabel, finishDataLabel, startTextData, finishTextData;
    //private WebView webView;
    private CycleDao cycleDao;
    private EditText description, title;
    private IChangeItem iChangeItem;
    CollapsingToolbarLayout collapsingToolbarLayout;
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
        if (cycle.getId() >0) {
            args.putLong(ID_CYCLE, cycle.getId());
            args.putString(TITLE, cycle.getTitle());
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
           cycle =  getCycleFromBundle();
        }
        if(savedInstanceState != null){
            cycle.setStartDate(savedInstanceState.getString(START_DATA));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_cycle_fragment_for_view_pager_description_edit, container, false);
        startDataLabel = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_label_edit_start);
        finishDataLabel = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_label_edit_finish);
        startTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_edit_start);
        finishTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_edit_finish);
        description = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_edit_web);
        title = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_edit_title);
        ///  saveFab = getActivity().findViewById(R.id.cycle_activity_detail_fab_main);

        startTextData.setOnClickListener(this);
        finishTextData.setOnClickListener(this);
        //   saveFab.setVisibility(View.GONE);
        title.setText(cycle.getTitle());
        startDataLabel.setText(R.string.label_start_cycle);
        finishDataLabel.setText(R.string.label_finish_cycle);
        description.setText(cycle.getComment());
        //webView.loadDataWithBaseURL(null,cycle.getComment(), "text/html", "UTF-8", null);
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

    private Cycle getCycleFromBundle() {
        Cycle cycle = new Cycle();
        cycle.setId(getArguments().getLong(ID_CYCLE, 0));
        if (cycle.getId() > 0) {
            System.out.println(12);
            cycle.setStartDate(getArguments().getString(START_DATA));
            cycle.setFinishDate(getArguments().getString(FINISH_DATA));
            cycle.setComment(getArguments().getString(COMMENT));
            cycle.setTitle(getArguments().getString(TITLE));
        } else {
            System.out.println(2);
            cycle.setStartDate(new Date());
            cycle.setFinishDate(new Date());
            cycle.setComment("");
            cycle.setTitle("");
        }
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
                        //   startTextData.setText(formatData(newDate.getTime()));
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
                        //finishTextData.setText(formatData(newDate.getTime()));
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
