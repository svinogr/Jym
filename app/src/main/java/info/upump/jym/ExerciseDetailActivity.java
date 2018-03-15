package info.upump.jym;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

public class ExerciseDetailActivity extends AppCompatActivity {
    private static final String ID_EXERCISE = "id";
    private static final String TITLE_EXERCISE = "title";
    private static final String TYPE_MUSCLE_EXERCISE = "type";
    private static final String DEFAULT_EXERCISE = "default";
    private ImageView imageView;
    private WebView description;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.exercise_activity_collapsing);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            exercise = getExerciseFromIntent(intent);
        }

        if(!exercise.isDefaultType()){ //убрать ! после заполнения базы
            fab.setVisibility(View.GONE);
        }

        imageView = findViewById(R.id.exercise_detail_activity_image_view);
        description = findViewById(R.id.exercise_detail_activity_description);
        Picasso.with(this).load(R.drawable.ic_launcher_background).fit().into(imageView);

        collapsingToolbarLayout.setTitle(exercise.getTitle());
        ExerciseDescription description = getDescription(exercise.getId());
        if (description == null) {
            description = new ExerciseDescription();
            description.setDescription(getResources().getString(R.string.no_description));
        }
        this.description.loadData(description.getDescription(), "text/html", "windows-1251");
    }

    public static Intent getIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetailActivity.class);
        intent.putExtra(ID_EXERCISE, exercise.getId());
        intent.putExtra(TITLE_EXERCISE, exercise.getTitle());
        intent.putExtra(TYPE_MUSCLE_EXERCISE, exercise.getTypeMuscle().toString());
        intent.putExtra(DEFAULT_EXERCISE, exercise.isDefaultType());
        return intent;
    }

    private Exercise getExerciseFromIntent(Intent intent) {
        Exercise exercise = new Exercise();
        exercise.setId(intent.getLongExtra(ID_EXERCISE, 0));
        exercise.setTitle(intent.getStringExtra(TITLE_EXERCISE));
        exercise.setTypeMuscle(TypeMuscle.valueOf(intent.getStringExtra(TYPE_MUSCLE_EXERCISE)));
        exercise.setDefaultType(intent.getBooleanExtra(DEFAULT_EXERCISE, true));
        return exercise;
    }

    private ExerciseDescription getDescription(long id) {
        ExerciseDescriptionDao exerciseDescriptionDao = new ExerciseDescriptionDao(this);
        List<ExerciseDescription> byParentId = exerciseDescriptionDao.getByParentId(id);
        if (byParentId != null) {
            return byParentId.get(0);
        } else return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
