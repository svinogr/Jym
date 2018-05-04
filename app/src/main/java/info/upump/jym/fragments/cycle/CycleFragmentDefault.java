package info.upump.jym.fragments.cycle;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.cycle.CycleDetailActivity;
import info.upump.jym.activity.cycle.CycleDetailDefaultActivity;
import info.upump.jym.adapters.CycleAdapter;
import info.upump.jym.entity.Cycle;
import info.upump.jym.loaders.ASTCycle;
import info.upump.jym.loaders.CycleFragmentLoader;

import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_DELETE;


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

    @Override
    public void createIntentForResult(ActivityOptions activityOptions, Cycle cycle) {
        Intent intent = CycleDetailDefaultActivity.createIntent(getContext(), cycle);
        if (activityOptions != null) {
            startActivity(intent, activityOptions.toBundle());
        } else startActivity(intent);

    }

 /*   @Override
    public Loader<List<Cycle>> onCreateLoader(int id, Bundle args) {
        CycleFragmentLoader cycleFragmentLoader = new CycleFragmentLoader(getContext(), Constants.LOADER_BY_DEFAULT_TYPE);
        return cycleFragmentLoader;
    }*/
}
