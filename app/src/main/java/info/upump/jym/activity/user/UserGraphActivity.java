package info.upump.jym.activity.user;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import info.upump.jym.R;
import info.upump.jym.adapters.PagerAdapterUserGraph;

public class UserGraphActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapterUserGraph pagerAdapterUserGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = findViewById(R.id.user_graph_activity_viewpager);
        tabLayout = findViewById(R.id.user_graph_activity_tab_layout);
        setTitle("График");

        pagerAdapterUserGraph = new PagerAdapterUserGraph(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapterUserGraph);
        tabLayout.setupWithViewPager(viewPager);

    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, UserGraphActivity.class);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
           finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
