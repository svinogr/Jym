package info.upump.jym.activity.workout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import info.upump.jym.R;
import info.upump.jym.adapters.PagerAdapterCycle;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;

public class CycleDetailActivity extends AppCompatActivity {
    private static final String ID_CYCLE = "id";
    private Cycle cycle;
    private FloatingActionButton fab;
    private PagerAdapterCycle pagerAdapterCycle;
    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.cycle_activity_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = findViewById(R.id.cycle_activity_detail_fab);
        viewPager = findViewById(R.id.cycle_fragment_viewpager);
        collapsingToolbarLayout = findViewById(R.id.cycle_activity_detail_collapsing);
        TabLayout tabLayout = findViewById(R.id.cycle_fragment_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        cycle = getCycleFromIntent();

        pagerAdapterCycle = new PagerAdapterCycle(getSupportFragmentManager(),cycle);
        viewPager.setAdapter(pagerAdapterCycle);

        createViewFrom(cycle);

    }


    public static Intent createIntent(Context context, Cycle cycle) {
        Intent intent = new Intent(context, CycleDetailActivity.class);
        intent.putExtra(ID_CYCLE, cycle.getId());
        return intent;
    }


    private void createViewFrom(Cycle cycle) {
        if (cycle != null) {
            collapsingToolbarLayout.setTitle(cycle.getTitle());
        }
        System.out.println(cycle);


       /* if (exercise.getDescription().equals("")) {
            exercise.setDescription(getResources().getString(R.string.no_description));
        }
        collapsingToolbarLayout.setTitle(cycle.getTitle());
        setPic();
        description.loadDataWithBaseURL(null, exercise.getDescription(), "text/html", "UTF-8", null);
   */ }

    private Cycle getCycleFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID_CYCLE,0);
        CycleDao cycleDao = new CycleDao(this);
        Cycle cycle = cycleDao.getById(id);
        return cycle;
    }

    private void setPic() {

    }
}
