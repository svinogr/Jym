package info.upump.jym.activity.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import info.upump.jym.R;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.TypeMuscle;

public class ExerciseDetailActivityEdit extends AppCompatActivity implements View.OnClickListener {
    public static final String ID_EXERCISE = "id";
    public static final String TITLE_EXERCISE = "title";
    public static final String TYPE_MUSCLE_EXERCISE = "type";
    public static final String DEFAULT_EXERCISE = "default";
    public static final String DESCRIPTION_EXERCISE = "description";
    public static final String IMG_EXERCISE = "img";
    public static final String ACTION = "action";
    public static final int REQUEST_CODE_FOR_EDIT_EXERCISE = 1;
    public static final int REQUEST_CODE_FOR_NEW_EXERCISE = 0;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabPhoto;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private Exercise exercise;
    private EditText title, description;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                collapsingToolbarLayout.setTitle(msg.obj.toString());
            }
        }
    };
    private String TITLE = "title";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail_edit);
        Toolbar toolbar = findViewById(R.id.exercise_activity_detail_edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.exercise_detail_activity_edit_title);
        description = findViewById(R.id.exercise_detail_activity_edit_description);
        imageView = findViewById(R.id.exercise_activity_detail_edit_image_view);

        collapsingToolbarLayout = findViewById(R.id.exercise_activity_detail_edit_collapsing);
        if (savedInstanceState == null) {
            collapsingToolbarLayout.setTitle(title.getHint());
        }

        fabSave = findViewById(R.id.exercise_activity_detail_edit_fab_save);
        fabPhoto = findViewById(R.id.exercise_activity_detail_edit_fab_save);
        fabSave.setOnClickListener(this);
        fabPhoto.setOnClickListener(this);

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(100);
                if((title.getText().toString().trim()).equals("")){
                    handler.sendMessageDelayed(handler.obtainMessage(100, title.getHint()), 250);
                }else handler.sendMessageDelayed(handler.obtainMessage(100, title.getText()), 250);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        exercise = getExerciseFromIntent(getIntent());

        if (exercise.getId() != 0) {
            title.setText(exercise.getTitle());
            description.setText(exercise.getDescription());
            collapsingToolbarLayout.setTitle(title.getText());
        }

        Picasso.with(this).load(R.drawable.ic_menu_camera).into(imageView);


    }

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetailActivityEdit.class);
        if (exercise.getId() != 0) {
            intent.putExtra(ACTION, ExerciseDetailActivityEdit.REQUEST_CODE_FOR_EDIT_EXERCISE);
            intent.putExtra(ExerciseDetailActivityEdit.ID_EXERCISE, exercise.getId());
            intent.putExtra(ExerciseDetailActivityEdit.TITLE_EXERCISE, exercise.getTitle());
            intent.putExtra(ExerciseDetailActivityEdit.DESCRIPTION_EXERCISE, exercise.getDescription());
            intent.putExtra(ExerciseDetailActivityEdit.IMG_EXERCISE, exercise.getImg());
        } else {
            intent.putExtra(ACTION, ExerciseDetailActivityEdit.REQUEST_CODE_FOR_NEW_EXERCISE);

        }
        intent.putExtra(ExerciseDetailActivityEdit.TYPE_MUSCLE_EXERCISE, exercise.getTypeMuscle().toString());
        return intent;
    }

    private Exercise getExerciseFromIntent(Intent intent) {
        Exercise exercise = new Exercise();
        if (intent.getIntExtra(ACTION, 0) == REQUEST_CODE_FOR_EDIT_EXERCISE) {
            exercise.setId(intent.getLongExtra(ID_EXERCISE, 0));
            exercise.setTitle(intent.getStringExtra(TITLE_EXERCISE));
            exercise.setDescription(intent.getStringExtra(DESCRIPTION_EXERCISE));
            exercise.setImg(intent.getStringExtra(IMG_EXERCISE));
        }
        exercise.setTypeMuscle(TypeMuscle.valueOf(intent.getStringExtra(ExerciseDetailActivityEdit.TYPE_MUSCLE_EXERCISE)));
        return exercise;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fabSave.getId()) {
            if (title.getText().toString().trim().equals("")) {
                Toast.makeText(this, "времен, необходим тайтл вести", Toast.LENGTH_SHORT).show();
                return;
            }
            createResultIntent();
            finish();
        }

    }

    private void createResultIntent() {
        Intent intent = new Intent();
        intent.putExtra(TITLE_EXERCISE, title.getText().toString());
        intent.putExtra(DESCRIPTION_EXERCISE, description.getText().toString());
        intent.putExtra(TYPE_MUSCLE_EXERCISE, exercise.getTypeMuscle().toString());
        setResult(RESULT_OK, intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String titleS = null;

        if (savedInstanceState.get(TITLE) != null) {
            titleS = savedInstanceState.getString(TITLE);

        }
        title.setText(titleS);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (!title.getText().toString().trim().equals("")) {
            System.out.println("onSaveInstanceState "+title.getText().toString());
            outState.putString(TITLE, title.getText().toString());

        }
        super.onSaveInstanceState(outState);
    }
}
