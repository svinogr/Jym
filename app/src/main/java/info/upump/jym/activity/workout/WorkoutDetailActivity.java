package info.upump.jym.activity.workout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.PagerAdapterWorkout;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Day;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.ID;

public class WorkoutDetailActivity extends AppCompatActivity implements IChangeItem<Workout> {
    private Workout workout;
    private ImageView imageView;
    private PagerAdapterWorkout pagerAdapterWorkout;
    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private IDescriptionFragment iDescriptionFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.workout_activity_detail_edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        workout = getItemFromIntent();
        System.out.println("worko " + workout);
        pagerAdapterWorkout = new PagerAdapterWorkout(getSupportFragmentManager(), workout);
        imageView = findViewById(R.id.workout_activity_detail_edit_image_view);
        collapsingToolbarLayout = findViewById(R.id.workout_activity_detail_edit_collapsing);
        viewPager = findViewById(R.id.workout_activity_detail_viewpager);
        TabLayout tabLayout = findViewById(R.id.workout_activity_detail_tab_layout);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.setAdapter(pagerAdapterWorkout);
        setPageTransform();


        if (savedInstanceState != null) {
            if (savedInstanceState.getString(Constants.URI_IMG) != null) {
                //   uriImage = Uri.parse(savedInstanceState.getString(Constants.URI_IMG));
                // cycle.setImage(uriImage.toString());
            }
        }
        createViewFrom(workout);

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
        System.out.println("id " + id);
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

    }

    @Override
    public void setInterfaceForDescription(IDescriptionFragment interfaceForDescription) {
        this.iDescriptionFragment = interfaceForDescription;

    }

    @Override
    public void setInterfaceForItem(IItemFragment interfaceForItem) {

    }

    private void exit() {
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
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }
        workout.setTitle(sOU.getTitle());
        workout.setComment(sOU.getComment());
        workout.setDay(sOU.getDay());

        boolean id = workoutDao.update(workout);
        if (id) {
            Toast.makeText(this, "времен, программа изменена", Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, не возможно изменить", Toast.LENGTH_SHORT).show();
    }

    private void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    private boolean itemIsNotChanged() {
        Workout changeableItem = (Workout) iDescriptionFragment.getChangeableItem();
        System.out.println(changeableItem);
        if (!changeableItem.getTitle().equals(workout.getTitle())) return false;
        if (!changeableItem.getComment().equals(workout.getComment())) return false;
        System.out.println(workout.getDay()+" " +changeableItem.getDay());
        System.out.println(changeableItem.getDay().toString().equals(workout.getDay().toString()));
        if (!changeableItem.getDay().toString().equals(workout.getDay().toString())) return false;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_menu_delete:
               /* Snackbar.make(this.imageView, "Удалить программу?", Snackbar.LENGTH_LONG)
                        .setAction("Да", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete(cycle.getId());
                                finishActivityWithAnimation();
                            }
                        }).show();*/
                break;
            case R.id.edit_menu_clear:
             /*   Snackbar.make(this.imageView, "Очистить программу?", Snackbar.LENGTH_LONG)
                        .setAction("Да", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clear();
                            }
                        }).show();
                break;*/
        }

        if (item.getItemId() == android.R.id.home) {
            exit();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exit();

    }
}