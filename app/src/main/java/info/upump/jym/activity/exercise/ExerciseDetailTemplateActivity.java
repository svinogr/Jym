package info.upump.jym.activity.exercise;

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
import android.support.v7.app.AlertDialog;
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

import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.UPDATE;

public class ExerciseDetailTemplateActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView muscle, description;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private AppBarLayout appBarLayout;
    private Exercise exercise;
    private FloatingActionButton editFab;
    private String[] nameOfValues;


    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                collapsingToolbarLayout.setTitle(msg.obj.toString());
            }

        }
    };

    public static Intent createIntent(Context context, Exercise exercise) {
        System.out.println("ExerciseDetailTemplateActivity");
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

        exercise = getItemFromIntent();
        nameOfValues = getNameOfMuscle();

        imageView = findViewById(R.id.exercise_detail_template_activity_image_view);
        collapsingToolbarLayout = findViewById(R.id.exercise_detail_template_activity_collapsing);
        appBarLayout = findViewById(R.id.exercise_detail_template_activity_appbar);
        description = findViewById(R.id.content_exercise_detail_template_activity_web);
        muscle = findViewById(R.id.content_exercise_detail_template_activity_muscle);
        editFab = findViewById(R.id.exercise_detail_template_activity_fab_main);
        editFab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_edit));
        editFab.setOnClickListener(this);
        if(exercise.isDefaultType()){
            editFab.setVisibility(View.INVISIBLE);

        }else setFabVisible();


        createViewFrom();
    }

    protected void setFabVisible() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                System.out.println(verticalOffset);
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
                .centerCrop()
                .placeholder(R.drawable.view_place_holder_exercise)
                .error(R.drawable.iview_place_erore_exercise)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if (exercise.isDefaultType()) {
            int ident = getResources().getIdentifier(exercise.getExerciseDescription().getImg(), "drawable", getPackageName());
            Glide.with(this).load(ident).apply(options).into(imageView);
        } else Glide.with(this).load(uri).apply(options).into(imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult  " + requestCode + "  " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.UPDATE:
                    updateDescription();
                    break;
            }
        }

    }

    private void updateDescription() {
        ExerciseDao exerciseDao  = new ExerciseDao(this);
        exercise = exerciseDao.getById(exercise.getId());
        createViewFrom();
    }

    private void createViewFrom() {
        collapsingToolbarLayout.setTitle(exercise.getExerciseDescription().getTitle());

        if (exercise.getComment() != null) {
            description.setText(exercise.getComment());
        }

        muscle.setText(nameOfValues[exercise.getTypeMuscle().ordinal()]);
        setPicUri(Uri.parse(exercise.getExerciseDescription().getImg()));
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
        ExerciseDao exerciseDao = new ExerciseDao(this);
        if (exerciseDao.delete(exercise)) {
            Toast.makeText(this, R.string.toast_exercise_delete, Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, R.string.toast_dont_delete, Toast.LENGTH_SHORT).show();
    }


    private void exit() {
        finishActivityWithAnimation();
    }


    private void finishActivityWithAnimation() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!exercise.isDefaultType()) {
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
            startActivityForResult(intent, UPDATE, transitionActivityOptions.toBundle());
        } else startActivityForResult(intent, UPDATE);

    }
}
