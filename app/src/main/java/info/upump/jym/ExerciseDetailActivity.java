package info.upump.jym;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.bd.ExerciseDescriptionDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.ExerciseDescription;
import info.upump.jym.entity.TypeMuscle;

public class ExerciseDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String ID_EXERCISE = "id";
    private static final String IMG_EXERCISE = "img";
    private static final String TITLE_EXERCISE = "title";
    private static final String TYPE_MUSCLE_EXERCISE = "type";
    private static final String DEFAULT_EXERCISE = "default";
    private static final String DESCRIPTION_EXERCISE = "description";
    private static final int REQUEST_CODE_FOR_EDIT_EXERCISE = 1;
    private ImageView imageView;
    private WebView description;
    private Exercise exercise;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout = findViewById(R.id.exercise_activity_collapsing);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            exercise = getExerciseFromIntent(intent);
        }


        if (exercise.isDefaultType()) {
            fab.setVisibility(View.GONE);
        }

        imageView = findViewById(R.id.exercise_detail_activity_image_view);
        description = findViewById(R.id.exercise_detail_activity_description);
        Picasso.with(this).load(R.drawable.ic_launcher_background).fit().into(imageView);// TODO картинкку из тренироывкм

        collapsingToolbarLayout.setTitle(exercise.getTitle());

        if (exercise.getDescription().equals("") && exercise.getDescription() == null) {
            exercise.setDescription(getResources().getString(R.string.no_description));
        }
        description.loadData(exercise.getDescription(), "text/html", "windows-1251");
    }

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetailActivity.class);
        intent.putExtra(ID_EXERCISE, exercise.getId());
        intent.putExtra(TITLE_EXERCISE, exercise.getTitle());
        intent.putExtra(DESCRIPTION_EXERCISE, exercise.getDescription());
        intent.putExtra(IMG_EXERCISE, exercise.getImg());
        intent.putExtra(TYPE_MUSCLE_EXERCISE, exercise.getTypeMuscle().toString());
        intent.putExtra(DEFAULT_EXERCISE, exercise.isDefaultType());
        return intent;
    }

    private Exercise getExerciseFromIntent(Intent intent) {
        Exercise exercise = new Exercise();
        exercise.setId(intent.getLongExtra(ID_EXERCISE, 0));
        exercise.setTitle(intent.getStringExtra(TITLE_EXERCISE));
        exercise.setDescription(intent.getStringExtra(DESCRIPTION_EXERCISE));
        exercise.setImg(intent.getStringExtra(IMG_EXERCISE));
        exercise.setTypeMuscle(TypeMuscle.valueOf(intent.getStringExtra(TYPE_MUSCLE_EXERCISE)));
        exercise.setDefaultType(intent.getBooleanExtra(DEFAULT_EXERCISE, true));
        return exercise;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = ExerciseDetailActivityEdit.createIntent(this, exercise);
        startActivityForResult(intent, REQUEST_CODE_FOR_EDIT_EXERCISE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_FOR_EDIT_EXERCISE) {
                String stringExtra = data.getStringExtra(DESCRIPTION_EXERCISE);
                exercise.setDescription(stringExtra);
                String stringExtra1 = data.getStringExtra(TITLE_EXERCISE);
                exercise.setTitle(stringExtra1);
                collapsingToolbarLayout.setTitle(exercise.getTitle());
                description.loadData(exercise.getDescription(), "text/html", "windows-1251");
                System.out.println(stringExtra + " " + stringExtra1);
            }
        }

    }
}
