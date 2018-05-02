package info.upump.jym.activity.workout;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.exercise.ExerciseActivityForChoose;
import info.upump.jym.adapters.PagerAdapterWorkout;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.UPDATE;

public class WorkoutDetailActivity extends AppCompatActivity implements IChangeItem<Workout>, View.OnClickListener {
    protected Workout workout;
    protected ImageView imageView;
    protected PagerAdapterWorkout pagerAdapterWorkout;
    protected ViewPager viewPager;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    protected IDescriptionFragment iDescriptionFragment;
    protected IItemFragment iItemFragment;
    protected FloatingActionButton addFab;
    protected TabLayout tabLayout;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.workout_activity_detail_edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        workout = getItemFromIntent();
        setPagerAdapter();

        addFab = findViewById(R.id.workout_activity_detail_fab_main);
        imageView = findViewById(R.id.workout_activity_detail_edit_image_view);
        collapsingToolbarLayout = findViewById(R.id.workout_activity_detail_edit_collapsing);
        viewPager = findViewById(R.id.workout_activity_detail_viewpager);
        tabLayout = findViewById(R.id.workout_activity_detail_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        appBarLayout = findViewById(R.id.workout_activity_detail_edit_appbar);

        addFab.setOnClickListener(this);

        viewPager.setAdapter(pagerAdapterWorkout);
        setPageTransform();
        setFabVisible();
        setTabSelected();
        setPagerAdapter();
        createViewFrom(workout);

    }

    protected void setTabSelected() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setIconFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    void setIconFab(int positionTab) {
        if (positionTab == 0) {
            addFab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));
        } else addFab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_edit));
    }

    protected void setFabVisible() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset < -20) {
                    if (addFab.isShown()) {
                        addFab.hide();
                    }
                } else if (!addFab.isShown()) {
                    addFab.show();
                }
            }
        });
    }

    protected void setPagerAdapter() {
        pagerAdapterWorkout = new PagerAdapterWorkout(getSupportFragmentManager(), workout, this);
    }

    private void setPageTransform() {
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
    }

    public static Intent createIntent(Context context, Workout workout) {
        Intent intent = new Intent(context, WorkoutDetailActivity.class);
        intent.putExtra(ID, workout.getId());
        return intent;
    }

    private Workout getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, 0);
        WorkoutDao workoutDao = new WorkoutDao(this);
        return workoutDao.getById(id);
    }

    private void createViewFrom(Workout workout) {
        if (workout != null) {
            collapsingToolbarLayout.setTitle(workout.getTitle());
        }
        setPic();
    }

    private void setPic() {
        Bitmap bitmap = Bitmap.createBitmap(1080, 200,
                Bitmap.Config.ARGB_8888);

        bitmap.eraseColor(getResources().getColor(workout.getDay().getColor()));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(this).load(bitmap).apply(options).into(imageView);
    }

    @Override
    public void updateDescription() {
        WorkoutDao workoutDao = new WorkoutDao(this);
        workout = workoutDao.getById(workout.getId());
        collapsingToolbarLayout.setTitle(workout.getTitle());
        setPic();
        iDescriptionFragment.updateItem(workout);
    }

    @Override
    public void delete(long id) {
        WorkoutDao workoutDao = new WorkoutDao(this);
        if (workoutDao.delete(workout)) {
            Toast.makeText(this, R.string.toast_workout_delete, Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setInterfaceForDescription(IDescriptionFragment interfaceForDescription) {
        this.iDescriptionFragment = interfaceForDescription;
    }

    @Override
    public void setInterfaceForItem(IItemFragment interfaceForItem) {
        this.iItemFragment = interfaceForItem;
    }

    protected void exit() {
        finishActivityWithAnimation();
    }

    protected void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_menu_delete:
                Snackbar.make(this.imageView, R.string.snack_delete, Snackbar.LENGTH_LONG)
                        .setAction(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete(workout.getId());
                            }
                        }).show();
                break;
            case R.id.edit_menu_clear:
                Snackbar.make(this.imageView, R.string.snack_workout_delete_exercises, Snackbar.LENGTH_LONG)
                        .setAction(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clear();
                            }
                        }).show();
                break;
        }

        if (item.getItemId() == android.R.id.home) {
            exit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void clear() {
        if (iItemFragment.clear()) {
            Toast.makeText(this, R.string.toast_workout_delete_exercises, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CODE_CHOOSE:
                    iItemFragment.addChosenItem(data.getLongExtra(Constants.ID, 0)); // можно удалить
                    break;
                case Constants.UPDATE:
                    updateDescription();
                    break;
            }
        }
    }

    private void updateItem() {
        Intent intent = WorkoutCreateActivity.createIntent(this, workout);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
            String transitionNameIm = "cycle_activity_detail_edit_image_view";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    Pair.create(sharedViewIm, transitionNameIm));
            startActivityForResult(intent, UPDATE, transitionActivityOptions.toBundle());
        } else startActivityForResult(intent, UPDATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.workout_activity_detail_fab_main) {
            int selectedTabPosition = tabLayout.getSelectedTabPosition();
            switch (selectedTabPosition) {
                case 0:
                    showDialogCreateItems();
                    break;
                case 1:
                    updateItem();
                    break;
            }
        }
    }

    private void showDialogCreateItems() {
        Intent intent = ExerciseActivityForChoose.createIntent(this, workout);
        startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE);
    }
}
