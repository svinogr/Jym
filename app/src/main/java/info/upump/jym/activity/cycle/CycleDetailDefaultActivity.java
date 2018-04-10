package info.upump.jym.activity.cycle;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.PagerAdapterCycleDefault;
import info.upump.jym.entity.Cycle;


public class CycleDetailDefaultActivity extends CycleDetailActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("CycleDetailDefaultActivity");
        return true;
    }

    public static Intent createIntent(Context context, Cycle cycle) {
        Intent intent = new Intent(context, CycleDetailDefaultActivity.class);
        intent.putExtra(Constants.ID, cycle.getId());
        return intent;
    }

    @Override
    protected void setFabVisible(boolean visible) {
        addFab.setVisibility(View.GONE);
    }

    @Override
    protected void setPagerAdapter() {
        pagerAdapterCycleEdit = new PagerAdapterCycleDefault(getSupportFragmentManager(), cycle);
    }

    @Override
    protected void exit() {
       finishActivityWithAnimation();
    }


}