package info.upump.jym.activity.cycle;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import info.upump.jym.R;
import info.upump.jym.adapters.PagerAdapterCycle;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.entity.Cycle;

public class CycleDetailActivity extends AppCompatActivity implements IChangeItem<Cycle> {
    private static final String ID_CYCLE = "id";
    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Cycle cycle;
    private PagerAdapterCycle pagerAdapterCycleEdit;
    private  CycleDao cycleDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.cycle_activity_detail_edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.cycle_fragment_edit_viewpager);
        collapsingToolbarLayout = findViewById(R.id.cycle_activity_detail_edit_collapsing);
        TabLayout tabLayout = findViewById(R.id.cycle_fragment_edit_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        cycle = getCycleFromIntent();

        pagerAdapterCycleEdit = new PagerAdapterCycle(getSupportFragmentManager(), cycle);
        viewPager.setAdapter(pagerAdapterCycleEdit);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();
                int pageHeight = view.getHeight();
                final float MIN_SCALE = 0.85f;
                float MIN_ALPHA = 0.5f;

                if (position < -1)

                { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1)

                { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        view.setTranslationX(horzMargin - vertMargin / 2);
                    } else {
                        view.setTranslationX(-horzMargin + vertMargin / 2);
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    view.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else

                { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }


        });
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
    }

    private Cycle getCycleFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID_CYCLE, 0);
        CycleDao cycleDao = new CycleDao(this);
        Cycle cycle = cycleDao.getById(id);
        return cycle;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
          finishActivityWithAnimation();
        }
        return super.onOptionsItemSelected(item);
    }
    private void finishActivityWithAnimation(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }



    @Override
    public void update(Cycle cycleSave) {
        //cycle.setTitle(cycleSave.getTitle());
        cycle.setStartDate(cycleSave.getStartDate());
        cycle.setFinishDate(cycleSave.getFinishDate());
        cycle.setComment(cycleSave.getComment());
        cycleDao = getCycleDao();
        if (cycleDao.update(cycle)) {
            Toast.makeText(this, "времен, упражнение сохранено", Toast.LENGTH_SHORT).show();
            finish();
        } else Toast.makeText(this, "времен, не возможно сохранить", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void delete(long id) {

    }

    private CycleDao getCycleDao() {
        if (cycleDao == null) {
            cycleDao = new CycleDao(this);
        }
        return cycleDao;
    }
}
