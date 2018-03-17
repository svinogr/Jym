package info.upump.jym.activity.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import info.upump.jym.R;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Exercise;

public class ExerciseDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String ID_EXERCISE = "id";
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

        createViewFrom(exercise);


    }

    private void createViewFrom(Exercise exercise) {
        Picasso.with(this).load(R.drawable.ic_launcher_background).fit().into(imageView);// TODO картинкку из тренироывкм
        collapsingToolbarLayout.setTitle(exercise.getTitle());

        if (exercise.getDescription().equals("")) {
            exercise.setDescription(getResources().getString(R.string.no_description));
        }
        description.loadData(exercise.getDescription(), "text/html", "windows-1251");

    }

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetailActivity.class);
        intent.putExtra(ID_EXERCISE, exercise.getId());
        return intent;
    }

    private Exercise getExerciseFromIntent(Intent intent) {
        ExerciseDao exerciseDao = new ExerciseDao(this);
        long longExtra = intent.getLongExtra(ID_EXERCISE, 0);
        exercise = exerciseDao.getById(longExtra);
        System.out.println(exercise);
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
        startActivityForResult(intent, ExerciseDetailActivityEdit.REQUEST_CODE_FOR_EDIT_EXERCISE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ExerciseDetailActivityEdit.REQUEST_CODE_FOR_EDIT_EXERCISE) {
                ExerciseDao exerciseDao = new ExerciseDao(this);
                exercise.setTitle(data.getStringExtra(ExerciseDetailActivityEdit.TITLE_EXERCISE));
                exercise.setDescription(data.getStringExtra(ExerciseDetailActivityEdit.DESCRIPTION_EXERCISE));
                if(exerciseDao.update(exercise)){
                    createViewFrom(exercise);
                }
            }
        }

    }

}
