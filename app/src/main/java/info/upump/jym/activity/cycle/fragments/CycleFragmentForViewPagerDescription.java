package info.upump.jym.activity.cycle.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;

import static info.upump.jym.activity.constant.Constants.ID;


public class CycleFragmentForViewPagerDescription extends Fragment implements IDescriptionFragment<Cycle> {
    private Cycle cycle;
    private TextView startTextData, finishTextData;
    private TextView description;
    private IChangeItem iChangeItem;
    private FloatingActionButton editFab;

    public CycleFragmentForViewPagerDescription() {
    }

    // TODO: Rename and change types and number of parameters
    public static CycleFragmentForViewPagerDescription newInstance(Cycle cycle) {
        CycleFragmentForViewPagerDescription fragment = new CycleFragmentForViewPagerDescription();
        Bundle args = new Bundle();
        args.putLong(ID, cycle.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getItemFromBundle();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_cycle_fragment_for_view_pager_description, container, false);
        startTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_edit_start);
        finishTextData = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_data_edit_finish);
        description = inflate.findViewById(R.id.cycle_fragment_for_view_pager_description_edit_web);
        editFab = getActivity().findViewById(R.id.cycle_activity_detail_fab_main);
        creteViewFrom();
        return inflate;
    }

    private void creteViewFrom() {
        description.setText(cycle.getComment());
        startTextData.setText(cycle.getStartStringFormatDate());
        finishTextData.setText(cycle.getFinishStringFormatDate());
    }

    private void getItemFromBundle() {
        long id = getArguments().getLong(ID);
        CycleDao cycleDao = CycleDao.getInstance(getContext(), null);
        cycle = cycleDao.getById(id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iChangeItem = (IChangeItem) context;
        iChangeItem.setInterfaceForDescription(this);
    }


    @Override
    public void updateItem(Cycle object) {
        cycle = object;
        creteViewFrom();
    }
}
