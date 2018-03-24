package info.upump.jym.activity.cycle.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import info.upump.jym.R;
import info.upump.jym.activity.cycle.IChageItem;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;


public class CycleFragmentForViewPagerDescriptionEdit extends Fragment implements View.OnClickListener {
    public final static String ID_CYCLE = "id";
    public final static String START_DATA = "start";
    public final static String FINISH_DATA = "finish";
    public final static String COMMENT = "comment";
    private Cycle cycle;
    private TextView startDataLabel, finishDataLabel, startTextData, finishTextData;
    private FloatingActionButton saveFab;
    //private WebView webView;
    private CycleDao cycleDao;
    private EditText comment;
    private IChageItem iChageItem;

    public CycleFragmentForViewPagerDescriptionEdit() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CycleFragmentForViewPagerDescriptionEdit newInstance(Cycle cycle) {
        CycleFragmentForViewPagerDescriptionEdit fragment = new CycleFragmentForViewPagerDescriptionEdit();
        Bundle args = new Bundle();
        args.putLong(ID_CYCLE, cycle.getId());
        args.putString(START_DATA, cycle.getStartStringFormatDate());
        args.putString(FINISH_DATA, cycle.getFinishStringFormatDate());
        args.putString(COMMENT, cycle.getComment());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getCycleFromBundle();
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
        comment = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_edit_web);
        saveFab = getActivity().findViewById(R.id.cycle_activity_detail_edit_fab_save);

        startTextData.setOnClickListener(this);
        finishTextData.setOnClickListener(this);
        saveFab.setOnClickListener(this);

        startDataLabel.setText(R.string.label_start_cycle);
        finishDataLabel.setText(R.string.label_finis_cycle);
        comment.setText(cycle.getComment());
        //webView.loadDataWithBaseURL(null,cycle.getComment(), "text/html", "UTF-8", null);
        startTextData.setText(cycle.getStartStringFormatDate());
        finishTextData.setText(cycle.getFinishStringFormatDate());
        return inflate;
    }

    private Cycle getCycleFromBundle() {
        cycle = new Cycle();
        cycle.setStartDate(getArguments().getString(START_DATA));
        cycle.setFinishDate(getArguments().getString(FINISH_DATA));
        cycle.setComment(getArguments().getString(COMMENT));
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
            case R.id.cycle_activity_detail_edit_fab_save:
                iChageItem.update(cycle);
                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iChageItem = (IChageItem) context;
    }
}
