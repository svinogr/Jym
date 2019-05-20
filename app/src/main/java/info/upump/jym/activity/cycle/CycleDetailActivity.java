package info.upump.jym.activity.cycle;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import info.upump.jym.R;
import info.upump.jym.activity.IChangeItem;
import info.upump.jym.activity.IDescriptionFragment;
import info.upump.jym.activity.IItemFragment;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.activity.workout.WorkoutActivityForChoose;
import info.upump.jym.activity.workout.WorkoutCreateActivity;
import info.upump.jym.activity.workout.WorkoutDetailActivity;
import info.upump.jym.adapters.PagerAdapterCycle;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Day;
import info.upump.jym.entity.Workout;
import info.upump.jym.fragments.cycle.CRUD;

import static info.upump.jym.activity.constant.Constants.CLEAR;
import static info.upump.jym.activity.constant.Constants.CREATE;
import static info.upump.jym.activity.constant.Constants.DAY;
import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.DESCRIPTION;
import static info.upump.jym.activity.constant.Constants.ERROR;
import static info.upump.jym.activity.constant.Constants.FINISH_DATA;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.PARENT_ID;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
import static info.upump.jym.activity.constant.Constants.START_DATA;
import static info.upump.jym.activity.constant.Constants.TEMPLATE_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.TITLE;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;
import static info.upump.jym.activity.constant.Constants.WEEK_EVEN;

public class CycleDetailActivity extends AppCompatActivity implements IChangeItem<Cycle>, View.OnClickListener, CRUD<Workout> {
    protected ViewPager viewPager;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    protected NestedScrollView nestedScrollView;
    protected ImageView imageView;
    protected Cycle cycle;
    protected PagerAdapterCycle pagerAdapterCycleEdit;
    protected IDescriptionFragment iDescriptionFragment;
    protected IItemFragment iItemFragment;
    protected FloatingActionButton addFab;
    protected TabLayout tabLayout;
    protected AppBarLayout appBarLayout;
    private boolean update;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DELETE) {
                Toast.makeText(getApplicationContext(), R.string.toast_workout_delete, Toast.LENGTH_SHORT).show();
            }

            if (msg.what == UPDATE) {
                Workout workout = (Workout) msg.obj;
                iItemFragment.update(workout);
            }

            if (msg.what == ERROR) {
                long id = (long) msg.obj;
                Toast.makeText(getApplicationContext(), R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
                iItemFragment.insertDeletedItem(id);
            }
           if(msg.what == CLEAR){
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

        setContentView(R.layout.activity_cycle_detail);

        Toolbar toolbar = findViewById(R.id.cycle_activity_detail_edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.cycle_fragment_edit_viewpager);
        viewPager = findViewById(R.id.cycle_fragment_edit_viewpager);
        imageView = findViewById(R.id.cycle_activity_detail_edit_image_view);
        addFab = findViewById(R.id.cycle_activity_detail_fab_main);
        collapsingToolbarLayout = findViewById(R.id.cycle_activity_detail_edit_collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExtenddAppBar);
        nestedScrollView = findViewById(R.id.nested_scroll);
        tabLayout = findViewById(R.id.cycle_fragment_edit_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        addFab.setOnClickListener(this);
        appBarLayout = findViewById(R.id.cycle_activity_detail_edit_appbar);

        cycle = getItemFromIntent();

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(UPDATE_DELETE) != false) {
                update = true;
            }
        }

        setFabVisible();
        setTabSelected();
        setPagerAdapter();
        viewPager.setAdapter(pagerAdapterCycleEdit);
        setPageTransform();
        createViewFrom();
    }

    void setIconFab(int positionTab) {
        if (positionTab == 0) {
            addFab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));
        } else addFab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_edit));
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
        pagerAdapterCycleEdit = new PagerAdapterCycle(getSupportFragmentManager(), cycle, this);
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

    public static Intent createIntent(Context context, Cycle cycle) {
        Intent intent = new Intent(context, CycleDetailActivity.class);
        intent.putExtra(Constants.ID, cycle.getId());
        return intent;
    }

    private void createViewFrom() {
        if (cycle != null) {
            collapsingToolbarLayout.setTitle(cycle.getTitle());
        }
        setPic();
    }

    private void setPic() {
        if (cycle.getDefaultImg() != null) {
            setDefaultPic();
        } else if (cycle.getImage() != null) {
            setPicUri(Uri.parse(cycle.getImage()));
        } else setPicUri(Uri.parse(("2513")));
    }

    private void setDefaultPic() {
        RequestOptions options = getOptionsGlide();
        int ident = getResources().getIdentifier(cycle.getDefaultImg(), "drawable", getPackageName());
        Glide.with(this).load(ident).apply(new RequestOptions()).into(imageView);
    }

    private RequestOptions getOptionsGlide() {
        RequestOptions options = new RequestOptions()
                .transforms(new RoundedCorners(50))
                .error(R.drawable.iview_place_erore_exercise)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        return options;
    }

    private Cycle getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(Constants.ID, 0);
        Cycle cycle = null;
        CycleDao cycleDao = CycleDao.getInstance(this, null);
        cycle = cycleDao.getById(id);
        return cycle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        if (resultCode == RESULT_OK) {
            long id;
            switch (requestCode) {
                case  REQUEST_CODE_CHANGE_OPEN :
                    id = data.getLongExtra(ID, 0);
                    int changeOrDelete = data.getIntExtra(UPDATE_DELETE, -1);
                    switch (changeOrDelete) {
                        case UPDATE:
                            updateInnerItem(id);
                            break;
                        case DELETE:
                            deleteInnerItem(id);
                            break;
                    }
                    break;
                case Constants.REQUEST_CODE_CHOOSE:
                    id = data.getLongExtra(ID, 0);
                    addItem(id);
                    break;
                case Constants.REQUEST_CODE_CREATE:
                    addNewItem(data);
                    break;
                case UPDATE:
                    update = true;
                    updateDescription();
                    break;
            }
        }
    }

    private void updateInnerItem(final long id) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WorkoutDao workoutDao = WorkoutDao.getInstance(getApplicationContext(), null);
                Workout workout = workoutDao.getById(id);
                if (workout != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(UPDATE, workout), 0);
                }
            }
        });
        thread.start();

    }

    private void deleteInnerItem(final long id) {
        System.out.println("delere");
        iItemFragment.delete(id);
              final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WorkoutDao workoutDao = WorkoutDao.getInstance(getApplicationContext(), null);
                Workout workout = new Workout();
                workout.setId(id);
                if (workoutDao.delete(workout)) {
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
                WorkoutDao workoutDao = WorkoutDao.getInstance(getApplicationContext(), null);
                Workout workout = workoutDao.alter(id, cycle.getId());
                if (workout != null) {
                    handler.sendMessageDelayed(handler.obtainMessage(CREATE, workout), 0);
                }
            }
        });
        thread.start();
    }

    private void addNewItem(Intent data) {
        final Workout workout = new Workout();
        workout.setTitle(data.getStringExtra(TITLE));
        workout.setComment(data.getStringExtra(DESCRIPTION));
        workout.setWeekEven(data.getBooleanExtra(WEEK_EVEN, false));
        workout.setDefaultType(data.getBooleanExtra(DEFAULT_TYPE_ITEM, false));
        workout.setTemplate(data.getBooleanExtra(TEMPLATE_TYPE_ITEM, false));
        workout.setDay(Day.valueOf(data.getStringExtra(DAY)));
        workout.setStartDate(data.getStringExtra(START_DATA));
        workout.setFinishDate(data.getStringExtra(FINISH_DATA));
        workout.setParentId(data.getLongExtra(PARENT_ID, 0));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WorkoutDao workoutDao = WorkoutDao.getInstance(getApplicationContext(), null);
                long id = workoutDao.create(workout);
                if (id != -1) {
                    workout.setId(id);
                    handler.sendMessageDelayed(handler.obtainMessage(CREATE, workout), 0);
                }
            }
        });
        thread.start();
    }



    @Override
    public void updateDescription() {
        CycleDao cycleDao = CycleDao.getInstance(this, null);
        cycle = cycleDao.getById(cycle.getId());
        createViewFrom();
        iDescriptionFragment.updateItem(cycle);
    }

    private void setPicUri(Uri uri) {
        RequestOptions options = getOptionsGlide();
        Glide.with(this).load(uri).apply(options).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_menu_delete:
                Snackbar.make(this.imageView, R.string.snack_delete, Snackbar.LENGTH_LONG)
                        .setAction(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                delete(cycle.getId());
                            }
                        }).show();
                break;
            case R.id.edit_menu_clear:
                Snackbar.make(this.imageView, R.string.snack_cycle_delete_workouts, Snackbar.LENGTH_LONG)
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

    @Override
    public void onBackPressed() {
        exit();
    }

    protected void exit() {
        finishActivityWithAnimation();
    }

    private void clear() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CycleDao cycleDao = CycleDao.getInstance(getApplicationContext(), null);
                boolean clear = cycleDao.clear(cycle.getId());
                if (clear) {
                    handler.sendMessageDelayed(handler.obtainMessage(CLEAR), 0);
                }
            }
        });
        thread.start();
    }

    protected void finishActivityWithAnimation() {
        if (update) {
            Intent intent = new Intent();
            intent.putExtra(ID, cycle.getId());
            intent.putExtra(UPDATE_DELETE, UPDATE);
            setResult(RESULT_OK, intent);
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    @Override
    public void delete(long id) {
        Intent intent = new Intent();
        intent.putExtra(ID, id);
        update = false;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cycle_activity_detail_fab_main) {
            int selectedTabPosition = tabLayout.getSelectedTabPosition();
            switch (selectedTabPosition) {
                case 0:
                    showDialogCreateItems();
                    break;
                case 1:
                    updateDescriptionItem();
                    break;
            }
        }
    }


    private void updateDescriptionItem() {
        Intent intent = CycleCreateActivity.createIntent(this, cycle);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
            String transitionNameIm = "cycle_activity_detail_edit_image_view";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    Pair.create(sharedViewIm, transitionNameIm));
            startActivityForResult(intent, UPDATE, transitionActivityOptions.toBundle());
        } else startActivityForResult(intent, UPDATE);
    }

    private void showDialogCreateItems() {
        String[] inputs = {getString(R.string.workout_dialog_create_new), getString(R.string.workout_dialog_сhoose)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.workout_dialog_title); // заголовок для диалога
        builder.setItems(inputs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                Intent intent;
                switch (item) {
                    case 1:
                        intent = WorkoutActivityForChoose.createIntent(getApplicationContext());
                        startActivityForResult(intent, Constants.REQUEST_CODE_CHOOSE);
                        break;
                    case 0:
                        Workout workout = new Workout();
                        workout.setDefaultType(false);
                        workout.setTemplate(false);
                        workout.setParentId(cycle.getId());
                        intent = WorkoutCreateActivity.createIntent(getApplicationContext(), workout);
                        startActivityForResult(intent, Constants.REQUEST_CODE_CREATE);
                        break;
                }
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(UPDATE_DELETE, update);
    }


    @Override
    public void createIntentForResult(ActivityOptions activityOptions, Workout workout) {
            Intent intent = WorkoutDetailActivity.createIntent(this, workout);
            if (activityOptions != null) {
                startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN, activityOptions.toBundle());
            } else startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN);
        }
}
