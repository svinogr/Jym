package info.upump.jym.temp.activity.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import info.upump.jym.R;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;

public class ExerciseDetailActivityEdit extends AppCompatActivity implements View.OnClickListener {
    private static final String ID_EXERCISE = "id";
    private static final String TITLE_EXERCISE = "title";
    private static final String TYPE_MUSCLE_EXERCISE = "type";
    private static final String DEFAULT_EXERCISE = "default";
    private static final String DESCRIPTION_EXERCISE = "description";
    private static final String IMG_EXERCISE = "img";
    private static final String EDIT_EXERCISE = "edit";
    private boolean edit;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabPhoto;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Exercise exercise;
    private EditText title, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.exercise_detail_activity_edit_title);
        description = findViewById(R.id.exercise_detail_activity_edit_description);

        collapsingToolbarLayout = findViewById(R.id.exercise_activity_edit_collapsing);
        collapsingToolbarLayout.setTitle(" ");


        fabSave = findViewById(R.id.fab_save);
        fabPhoto = findViewById(R.id.fab_save);
        fabSave.setOnClickListener(this);
        fabPhoto.setOnClickListener(this);

        exercise = getExerciseFromIntent(getIntent());
        if(exercise != null){
            title.setText(exercise.getTitle());
            description.setText(exercise.getDescription());

        }

    }

    public static Intent createIntent(Context context, Exercise exercise){
        Intent intent = new Intent(context, ExerciseDetailActivityEdit.class);
        if (exercise!=null) {
            intent.putExtra(EDIT_EXERCISE, true);
            intent.putExtra(ID_EXERCISE, exercise.getId());
            intent.putExtra(TITLE_EXERCISE, exercise.getTitle());
            intent.putExtra(DESCRIPTION_EXERCISE, exercise.getDescription());
            intent.putExtra(IMG_EXERCISE, exercise.getImg());
            intent.putExtra(TYPE_MUSCLE_EXERCISE, exercise.getTypeMuscle().toString());
            intent.putExtra(DEFAULT_EXERCISE, exercise.isDefaultType());
        }else intent.putExtra(EDIT_EXERCISE, false);
        return intent;
    }

    private Exercise getExerciseFromIntent(Intent intent) {
        Exercise exercise = null;
        edit = intent.getBooleanExtra(EDIT_EXERCISE, true);
        if(edit) {
            exercise = new Exercise();
            exercise.setId(intent.getLongExtra(ID_EXERCISE, 0));
            exercise.setTitle(intent.getStringExtra(TITLE_EXERCISE));
            exercise.setDescription(intent.getStringExtra(DESCRIPTION_EXERCISE));
            exercise.setImg(intent.getStringExtra(IMG_EXERCISE));
            exercise.setTypeMuscle(TypeMuscle.valueOf(intent.getStringExtra(TYPE_MUSCLE_EXERCISE)));
            exercise.setDefaultType(intent.getBooleanExtra(DEFAULT_EXERCISE, true));
        }
        return exercise;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == fabSave.getId()){
            if(edit){
                Intent intent = createResultIntent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

    }

    private Intent createResultIntent() {
        Intent intent = new Intent();
        intent.putExtra(TITLE_EXERCISE, title.getText().toString());
        intent.putExtra(DESCRIPTION_EXERCISE, description.getText().toString());

        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
