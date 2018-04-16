package info.upump.jym.activity.exercise;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import info.upump.jym.IControlFragment;
import info.upump.jym.ITitleble;
import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IChooseItem;
import info.upump.jym.adapters.PagerAdapterExercise;
import info.upump.jym.adapters.PagerAdapterExerciseForChoose;
import info.upump.jym.entity.Exercise;

import static info.upump.jym.activity.constant.Constants.ID;

public class ExerciseActivityForChoose extends AppCompatActivity implements  IChooseItem<Exercise> {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapterExerciseForChoose pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_for_choose);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Выберите упражнение");

        viewPager = findViewById(R.id.activity_exercise_for_choose_viewpager);
        tabLayout = findViewById(R.id.activity_exercise_for_choose_tab_layout);
        pagerAdapter = new PagerAdapterExerciseForChoose(getSupportFragmentManager(), this);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setPageTransform();

    }

    private void setPageTransform() {
        viewPager.setPageTransformer(
                false, new ViewPager.PageTransformer() {
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
    }

    public static Intent createIntent(Context context){
        System.out.println("crate inte");
        Intent intent = new Intent(context, ExerciseActivityForChoose.class);
        return intent;
    }

    @Override
    public void createIntentForChooseResult(Exercise exercise) {
       Intent intent = new Intent();
       intent.putExtra(ID, exercise.getId());
       setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
