package info.upump.jym.activity.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.IChooseItem;
import info.upump.jym.adapters.PagerAdapterExerciseForChoose;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.ID;

public class ExerciseActivityForChoose extends AppCompatActivity implements IChooseItem<Exercise> {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapterExerciseForChoose pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_for_choose);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.exercise_choose_title_add_exercise);
//        id = getIntent().getLongExtra(ID, 0);
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

    public static Intent createIntent(Context context, Workout workout) {
        Intent intent = new Intent(context, ExerciseActivityForChoose.class);
        intent.putExtra(ID, workout.getId());
        return intent;
    }

    @Override
    public void createIntentForChooseResult(Exercise exercise) {
        Intent intent = new Intent();
        intent.putExtra(ID, exercise.getId());
        setResult(RESULT_OK, intent);
        exit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        exit();
        return super.onOptionsItemSelected(item);
    }

    private void exit() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }
}
