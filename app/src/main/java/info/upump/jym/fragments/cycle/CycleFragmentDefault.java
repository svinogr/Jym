package info.upump.jym.fragments.cycle;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.CycleAdapter;
import info.upump.jym.entity.Cycle;
import info.upump.jym.loaders.CycleFragmentLoader;


public class CycleFragmentDefault extends CycleFragment {

    @Override
    protected void setTitle() {
        iTitlable.setTitle(getResources().getString(R.string.cycle_fragment_template_title));
    }

    public static CycleFragmentDefault newInstance() {
        CycleFragmentDefault fragment = new CycleFragmentDefault();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void createAdapter() {
        cycleAdapter = new CycleAdapter(cycleList, Constants.LOADER_BY_DEFAULT_TYPE);
    }

    @Override
    protected void setFab() {
    addFab.setVisibility(View.INVISIBLE);
    }

    @Override
    public Loader<List<Cycle>> onCreateLoader(int id, Bundle args) {
        CycleFragmentLoader cycleFragmentLoader = new CycleFragmentLoader(getContext(), Constants.LOADER_BY_DEFAULT_TYPE);
        return cycleFragmentLoader;

    }
}
