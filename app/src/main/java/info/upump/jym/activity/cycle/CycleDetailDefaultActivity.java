package info.upump.jym.activity.cycle;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.View;

import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.PagerAdapterCycle;
import info.upump.jym.adapters.PagerAdapterCycleDefault;
import info.upump.jym.entity.Cycle;


public class CycleDetailDefaultActivity extends CycleDetailActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public static Intent createIntent(Context context, Cycle cycle) {
        System.out.println("CycleDetailDefaultActivity");
        Intent intent = new Intent(context, CycleDetailDefaultActivity.class);
        intent.putExtra(Constants.ID, cycle.getId());
        return intent;
    }

    @Override
    protected void setPagerAdapter() {
        pagerAdapterCycleEdit = new PagerAdapterCycleDefault(getSupportFragmentManager(), cycle,this);
    }

    @Override
    protected void exit() {
       finishActivityWithAnimation();
    }

    @Override
    protected void setFabVisible() {
        addFab.setVisibility(View.INVISIBLE);
    }
}
