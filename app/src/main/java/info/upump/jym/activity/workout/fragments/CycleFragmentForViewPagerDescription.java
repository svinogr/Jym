package info.upump.jym.activity.workout.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.entity.Cycle;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CycleFragmentForViewPagerDescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CycleFragmentForViewPagerDescription extends Fragment implements View.OnClickListener {
    private final static String START_DATA = "start";
    private final static String FINISH_DATA = "finish";
    private final static String COMMENT = "comment";
    private Cycle cycle;
    private TextView startDataLabel, finishDataLabel, startTextData, finishTextData;
    private WebView webView;


    public CycleFragmentForViewPagerDescription() {
        // Required empty public constructor
    }


    public static CycleFragmentForViewPagerDescription newInstance(Cycle cycle) {
        CycleFragmentForViewPagerDescription fragment = new CycleFragmentForViewPagerDescription();
        Bundle args = new Bundle();
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
            cycle = new Cycle();
            cycle.setStartDate(getArguments().getString(START_DATA));
            cycle.setFinishDate(getArguments().getString(FINISH_DATA));
            cycle.setComment(getArguments().getString(COMMENT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_cycle_fragment_for_view_pager_description, container, false);
        startDataLabel = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_label_start);
        finishDataLabel = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_label_finish);
        startTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_start);
        finishTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_finish);
        webView = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_web);

        startTextData.setOnClickListener(this);
        finishTextData.setOnClickListener(this);

        startDataLabel.setText(R.string.label_start_cycle);
        finishDataLabel.setText(R.string.label_finis_cycle);
        webView.loadDataWithBaseURL(null,cycle.getComment(), "text/html", "UTF-8", null);
        startTextData.setText(cycle.getStartStringFormatDate());
        finishTextData.setText(cycle.getFinishStringFormatDate());

        return inflate;
    }

    @Override
    public void onClick(View v) {

    }
}
