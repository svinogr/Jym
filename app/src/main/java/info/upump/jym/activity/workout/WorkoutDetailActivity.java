package info.upump.jym.activity.workout;

import static info.upump.jym.activity.constant.Constants.CLEAR;
import static info.upump.jym.activity.constant.Constants.CREATE;
import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.ERROR;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.WorkoutViewActivity;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.exercise.ExerciseActivityForChoose;
import info.upump.jym.activity.exercise.ExerciseDetail;
import info.upump.jym.adapters.PagerAdapterWorkout;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.Workout;
import info.upump.jym.fragments.cycle.CRUD;

public class WorkoutDetailActivity extends AppCompatActivity implements IChangeItem<Workout>, View.OnClickListener, CRUD<Exercise> {
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
    private boolean update;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DELETE) {
                Toast.makeText(getApplicationContext(), R.string.toast_exercise_delete, Toast.LENGTH_SHORT).show();
            }

            if (msg.what == UPDATE) {
                Exercise exercise = (Exercise) msg.obj;
                iItemFragment.update(exercise);
            }

          /*  if (msg.what == ERROR) {
                long id = (long) msg.obj;
                Toast.makeText(getApplicationContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
                iItemFragment.insertDeletedItem(id);
            }*/
            if (msg.what == CLEAR) {
                iItemFragment.clear();
            }

            if (msg.what == CREATE) {
                iItemFragment.addItem(msg.obj);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        Toolbar toolbar = findViewById(R.id.workout_activity_detail_edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        workout = getItemFromIntent();
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(UPDATE_DELETE) != false) {
                update = true;
            }
        }
        setPagerAdapter();

        addFab = findViewById(R.id.workout_activity_detail_fab_main);
        imageView = findViewById(R.id.workout_activity_detail_edit_image_view);
        collapsingToolbarLayout = findViewById(R.id.workout_activity_detail_edit_collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExtenddAppBar);
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

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
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

                } else { // (1,+Infinity]
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
        WorkoutDao workoutDao = WorkoutDao.getInstance(this, null);
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
        WorkoutDao workoutDao = WorkoutDao.getInstance(this, null
        );
        workout = workoutDao.getById(workout.getId());
        collapsingToolbarLayout.setTitle(workout.getTitle());
        setPic();
        iDescriptionFragment.updateItem(workout);
    }

    @Override
    public void delete(long id) {
        update = false;
        Intent intent = new Intent();
        intent.putExtra(ID, id);
        intent.putExtra(UPDATE_DELETE, DELETE);
        setResult(RESULT_OK, intent);
        finishActivityWithAnimation();
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
        if (update) {
            Intent intent = new Intent();
            intent.putExtra(ID, workout.getId());
            intent.putExtra(UPDATE_DELETE, UPDATE);
            setResult(RESULT_OK, intent);
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_now:
                Intent intent = WorkoutViewActivity.newInstance(
                        workout.getId(), this);
                startActivity(intent);
                break;

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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WorkoutDao workoutDao = WorkoutDao.getInstance(getApplicationContext(), null);
                boolean clear = workoutDao.clear(workout.getId());
                if (clear) {
                    handler.sendMessageDelayed(handler.obtainMessage(CLEAR), 0);
                }
            }
        });
        thread.start();
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            long id = data.getLongExtra(ID, 0);

            switch (requestCode) {
                case REQUEST_CODE_CHANGE_OPEN:
                    id = data.getLongExtra(ID, 0);
                    int changeOrDelete = data.getIntExtra(UPDATE_DELETE, -1);
                    switch (changeOrDelete) {
                        case UPDATE:
                            System.out.println("update");
                            updateInnerItem(id);
                            break;
                        case DELETE:
                            deleteInnerItem(id);
                            break;
                    }
                    break;
                case Constants.REQUEST_CODE_CHOOSE:
                    addItem(id);
                    break;
                case Constants.UPDATE:
                    update = true;
                    updateDescription();
                    break;
            }
        }
    }

    private void updateInnerItem(final long id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDao exerciseDao = ExerciseDao.getInstance(getApplicationContext(), null);
                Exercise exercise = exerciseDao.getById(id);
                if (exercise != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(UPDATE, exercise), 0);
                }
            }
        });
        thread.start();
    }

    private void deleteInnerItem(final long id) {
        iItemFragment.delete(id);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDao exerciseDao = ExerciseDao.getInstance(getApplicationContext(), null);
                Exercise exercise = new Exercise();
                exercise.setId(id);
                if (exerciseDao.delete(exercise)) {
                    handler.sendMessageDelayed(handler.obtainMessage(DELETE, id), 0);
                } else handler.sendMessageDelayed(handler.obtainMessage(ERROR, id), 0);
            }
        });
        thread.start();
    }

    private void addItem(final long id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDao exerciseDao = ExerciseDao.getInstance(getApplicationContext(), null);
                Exercise exercise = exerciseDao.alterCopy(id, workout.getId());
                if (exercise != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(CREATE, exercise), 0);
                }
            }
        });
        thread.start();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(UPDATE_DELETE, update);
    }

    @Override
    public void createIntentForResult(ActivityOptions activityOptions, Exercise exercise) {
        Intent intent = ExerciseDetail.createIntent(this, exercise);
        if (activityOptions != null) {
            startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN, activityOptions.toBundle());
        } else startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN);

    }
}
