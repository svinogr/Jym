package info.upump.jym.activity.workout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.PagerAdapterWorkout;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.ID;

public class WorkoutDetailActivity extends AppCompatActivity implements IChangeItem<Workout> {
   protected Workout workout;
   protected ImageView imageView;
   protected PagerAdapterWorkout pagerAdapterWorkout;
   protected ViewPager viewPager;
   protected CollapsingToolbarLayout collapsingToolbarLayout;
   protected IDescriptionFragment iDescriptionFragment;
   protected IItemFragment iItemFragment;
   protected FloatingActionButton addFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.workout_activity_detail_edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        workout = getItemFromIntent();
        setPagerAdapter();
        addFab = findViewById(R.id.workout_fragment_for_view_pager_exercises_fab_main);
        imageView = findViewById(R.id.workout_activity_detail_edit_image_view);
        collapsingToolbarLayout = findViewById(R.id.workout_activity_detail_edit_collapsing);
        viewPager = findViewById(R.id.workout_activity_detail_viewpager);
        TabLayout tabLayout = findViewById(R.id.workout_activity_detail_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setAdapter(pagerAdapterWorkout);
        setPageTransform();
       // setFabVisible(true);

       /* if (savedInstanceState != null) {
            if (savedInstanceState.getString(Constants.URI_IMG) != null) {
                //   uriImage = Uri.parse(savedInstanceState.getString(Constants.URI_IMG));
                // cycle.setImage(uriImage.toString());
            }
        }*/
        createViewFrom(workout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    setFabVisible(true);
                } else setFabVisible(false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    protected void setFabVisible(boolean visible) {
        if (visible) {
            addFab.setVisibility(View.VISIBLE);
        } else  addFab.setVisibility(View.GONE);
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
        System.out.println("WorkoutDetailActivity");
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
        Bitmap bitmap = Bitmap.createBitmap(100, 100,
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(getResources().getColor(workout.getDay().getColor()));
        imageView.setImageBitmap(bitmap);
    }


    @Override
    public void update(Workout object) {
    }

    @Override
    public void save(Workout object) {
    }


    @Override
    public void delete(long id) {
        WorkoutDao workoutDao = new WorkoutDao(this);
        if( workoutDao.delete(workout)){
            Toast.makeText(this, R.string.toast_workout_delete, Toast.LENGTH_SHORT).show();
            //exit();
            finishActivityWithAnimation();
        }else  Toast.makeText(this, R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setInterfaceForDescription(IDescriptionFragment interfaceForDescription) {
        System.out.println("interfaceForDescription "+interfaceForDescription);
        this.iDescriptionFragment = interfaceForDescription;
    }

    @Override
    public void setInterfaceForItem(IItemFragment interfaceForItem) {
        System.out.println("interfaceForItem "+interfaceForItem);
      this.iItemFragment = interfaceForItem;
    }

    protected void exit() {
        if (itemIsNotChanged()) {
            finishActivityWithAnimation();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle(getResources().getString(R.string.save));
            ad.setPositiveButton((getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    update();
                }
            });
            ad.setNegativeButton((getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishActivityWithAnimation();
                }
            });
            ad.show();
        }

    }

    private void update() {
        Workout sOU = (Workout) iDescriptionFragment.getChangeableItem();

        WorkoutDao workoutDao = new WorkoutDao(this);
        if (sOU.getTitle().trim().isEmpty()) {
            Toast.makeText(this, R.string.toast_write_name, Toast.LENGTH_SHORT).show();
            return;
        }
        workout.setTitle(sOU.getTitle());
        workout.setComment(sOU.getComment());
        workout.setDay(sOU.getDay());

        boolean id = workoutDao.update(workout);
        if (id) {
            Toast.makeText(this, R.string.toast_workout_update, Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, R.string.toast_dont_update, Toast.LENGTH_SHORT).show();
    }

    protected void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    private boolean itemIsNotChanged() {
        Workout changeableItem = (Workout) iDescriptionFragment.getChangeableItem();
        System.out.println(changeableItem);
        if (!changeableItem.getTitle().equals(workout.getTitle())) return false;
        if (!changeableItem.getComment().equals(workout.getComment())) return false;
        System.out.println(workout.getDay() + " " + changeableItem.getDay());
        System.out.println(changeableItem.getDay().toString().equals(workout.getDay().toString()));
        if (!changeableItem.getDay().toString().equals(workout.getDay().toString())) return false;
        return true;
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
                    System.out.println("interfaceForItem "+iItemFragment);
                    iItemFragment.addChosenItem(data.getLongExtra(Constants.ID, 0));
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }
}
