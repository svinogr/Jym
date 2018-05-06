package info.upump.jym.activity.exercise;

import android.app.ActivityOptions;
import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.bd.ExerciseDescriptionDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.ExerciseDescription;
import info.upump.jym.entity.TypeMuscle;

import static info.upump.jym.activity.constant.Constants.CREATE;
import static info.upump.jym.activity.constant.Constants.DEFAULT_IMAGE;
import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.DELETE;
import static info.upump.jym.activity.constant.Constants.DESCRIPTION;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.ID_DESCRIPTION;
import static info.upump.jym.activity.constant.Constants.IMAGE;
import static info.upump.jym.activity.constant.Constants.REQUEST_CODE_CHANGE_OPEN;
import static info.upump.jym.activity.constant.Constants.TEMPLATE_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.TITLE;
import static info.upump.jym.activity.constant.Constants.TYPE_MUSCLE;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;

public class ExerciseDetailTemplateActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView muscle, description;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private AppBarLayout appBarLayout;
    private Exercise exercise;
    private FloatingActionButton editFab;
    private String[] nameOfValues;
    private boolean update;


    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                collapsingToolbarLayout.setTitle(msg.obj.toString());
            }
            if (msg.what == UPDATE) {
                exercise = (Exercise) msg.obj;
                createViewFrom();
                Toast.makeText(getApplicationContext(), R.string.toast_exercise_update, Toast.LENGTH_SHORT).show();
            }

        }
    };

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetailTemplateActivity.class);
        intent.putExtra(ID, exercise.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail_tempalte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.exercise_detail_template_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(UPDATE_DELETE) != false) {
                update = true;
            }
        }
        exercise = getItemFromIntent();
        nameOfValues = getNameOfMuscle();

        imageView = findViewById(R.id.exercise_detail_template_activity_image_view);
        collapsingToolbarLayout = findViewById(R.id.exercise_detail_template_activity_collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExtenddAppBar);
        appBarLayout = findViewById(R.id.exercise_detail_template_activity_appbar);
        description = findViewById(R.id.content_exercise_detail_template_activity_web);
        muscle = findViewById(R.id.content_exercise_detail_template_activity_muscle);
        editFab = findViewById(R.id.exercise_detail_template_activity_fab_main);
        editFab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_edit));
        editFab.setOnClickListener(this);

        if (exercise.isDefaultType() || !exercise.isTemplate()) {
            editFab.setVisibility(View.INVISIBLE);

        } else setFabVisible();

        createViewFrom();
    }

    protected void setFabVisible() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset < -20) {
                    if (editFab.isShown()) {
                        editFab.hide();
                    }
                } else if (!editFab.isShown()) {
                    editFab.show();
                }
            }
        });
    }

    private Exercise getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, 0);
        ExerciseDao exerciseDao = new ExerciseDao(this);
        Exercise exercise = exerciseDao.getById(id);
        ExerciseDescriptionDao exerciseDescriptionDao = new ExerciseDescriptionDao(this);
        ExerciseDescription exerciseDescription = exerciseDescriptionDao.getById(exercise.getDescriptionId());
        exercise.setExerciseDescription(exerciseDescription);
        return exercise;
    }

    private TypeMuscle getMuscle(int i) {
        TypeMuscle[] values = TypeMuscle.values();
        return values[i];
    }

    private String[] getNameOfMuscle() {
        TypeMuscle[] values = TypeMuscle.values();
        String[] nameOfValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            nameOfValues[i] = getResources().getString(values[i].getName());
        }
        return nameOfValues;
    }

    private void setPicUri(Uri uri) {
        RequestOptions options = new RequestOptions()
                .error(R.drawable.iview_place_erore_exercise)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if (uri == null) {
            int ident = getResources().getIdentifier(exercise.getExerciseDescription().getDefaultImg(), "drawable", getPackageName());
            Glide.with(this).load(ident).apply(options).into(imageView);
        } else Glide.with(this).load(uri).apply(options).into(imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHANGE_OPEN:
                    int changeOrDelete = data.getIntExtra(UPDATE_DELETE, -1);
                    switch (changeOrDelete) {
                        case UPDATE:
                             update = true;
                            updateDescription(data);
                            System.out.println("up");
                            break;
                    }
                    break;

            }
        }
    }

    private void updateDescription(Intent data) {
        ExerciseDescription exerciseDescription = new ExerciseDescription();
        final Exercise exercise = new Exercise();
        exercise.setExerciseDescription(exerciseDescription);

        exerciseDescription.setTitle(data.getStringExtra(TITLE));
        exerciseDescription.setDefaultImg(data.getStringExtra(DEFAULT_IMAGE));
        exerciseDescription.setImg(data.getStringExtra(IMAGE));
        long descriptionId = data.getLongExtra(ID_DESCRIPTION, 0);
        exerciseDescription.setId(descriptionId);

        exercise.setComment(data.getStringExtra(DESCRIPTION));
        TypeMuscle typeMuscle = TypeMuscle.valueOf(data.getStringExtra(TYPE_MUSCLE));
        exercise.setTypeMuscle(typeMuscle);
        exercise.setId(data.getLongExtra(ID, 0));
        exercise.setDescriptionId(descriptionId);
        exercise.setDefaultType(data.getBooleanExtra(DEFAULT_TYPE_ITEM, false));
        exercise.setTemplate(data.getBooleanExtra(TEMPLATE_TYPE_ITEM, true));
        exercise.setStartDate(new Date());
        exercise.setFinishDate(new Date());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExerciseDao exerciseDao = new ExerciseDao(getApplicationContext());
                if (exerciseDao.update(exercise)) {
                    handler.sendMessageDelayed(handler.obtainMessage(UPDATE, exercise), 0);
                }
            }
        });
        thread.start();
//
//        ExerciseDao exerciseDao = new ExerciseDao(this);
//        exercise = exerciseDao.getById(exercise.getId());
//        createViewFrom();
    }

    private void createViewFrom() {
        collapsingToolbarLayout.setTitle(exercise.getExerciseDescription().getTitle());

        if (exercise.getComment() != null) {
            description.setText(exercise.getComment());
        }

        muscle.setText(nameOfValues[exercise.getTypeMuscle().ordinal()]);
        if (exercise.getExerciseDescription().getDefaultImg() != null) {
            setPicUri(null);
        } else setPicUri(Uri.parse(exercise.getExerciseDescription().getImg()));
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            exit();
        }
        if (item.getItemId() == R.id.edit_menu_delete) {
            Snackbar.make(this.imageView, R.string.snack_delete, Snackbar.LENGTH_LONG)
                    .setAction(R.string.yes, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delete();
                        }
                    }).show();

        }
        return super.onOptionsItemSelected(item);
    }

    private void delete() {
        Intent intent = new Intent();

        intent.putExtra(UPDATE_DELETE, DELETE);
        intent.putExtra(ID, exercise.getId());
        setResult(RESULT_OK, intent);
        exit();
     /*   ExerciseDao exerciseDao = new ExerciseDao(this);
        if (exerciseDao.delete(exercise)) {
            Toast.makeText(this, R.string.toast_exercise_delete, Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();*/
    }

    private void exit() {
        if (update) {
            Intent intent = new Intent();
            intent.putExtra(ID, exercise.getId());
            intent.putExtra(UPDATE_DELETE, UPDATE);
            setResult(RESULT_OK, intent);
        }
        finishActivityWithAnimation();
    }


    private void finishActivityWithAnimation() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!exercise.isDefaultType() && exercise.isTemplate()) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.edit_template_exercise_menu, menu);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = ExerciseCreateActivity.createIntent(this, exercise);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
            String transitionNameIm = "exercise_activity_create_image";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    Pair.create(sharedViewIm, transitionNameIm));
            startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN, transitionActivityOptions.toBundle());
        } else startActivityForResult(intent, REQUEST_CODE_CHANGE_OPEN);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(UPDATE_DELETE, update);
    }

}
