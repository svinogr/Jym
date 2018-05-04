package info.upump.jym.fragments.cycle;

import android.os.Bundle;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.CycleAdapter;
import info.upump.jym.loaders.ASTCycle;


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
    protected void createAsyncTask() {
        astCycle = new ASTCycle(getContext());
        astCycle.execute(Constants.LOADER_BY_DEFAULT_TYPE);
    }

    @Override
    protected void createAdapter() {
        cycleAdapter = new CycleAdapter(cycleList, Constants.LOADER_BY_DEFAULT_TYPE,this);
    }

    @Override
    protected void setFab() {
    addFab.setVisibility(View.INVISIBLE);
    }

}
