package info.upump.jym.activity.exercise;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import info.upump.jym.bd.ExerciseDao;
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
    private ExerciseDao exerciseDao;
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
        fabSave = findViewById(R.id.exercise_activity_detail_edit_fab_photo);
        fabPhoto = findViewById(R.id.exercise_activity_detail_edit_fab_save);
        fabSave.setOnClickListener(this);
        fabPhoto.setOnClickListener(this);

        if (savedInstanceState == null) {
            collapsingToolbarLayout.setTitle(title.getHint());
        }

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(100);
                if ((title.getText().toString().trim()).equals("")) {
                    handler.sendMessageDelayed(handler.obtainMessage(100, title.getHint()), 250);
                } else handler.sendMessageDelayed(handler.obtainMessage(100, title.getText()), 250);
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
        Uri imgUri = null;
        try {
            imgUri = Uri.parse(exercise.getImg());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(imgUri).error(R.drawable.ic_launcher_background).into(imageView);
    }

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetailActivityEdit.class);
        intent.putExtra(ExerciseDetailActivityEdit.TYPE_MUSCLE_EXERCISE, exercise.getTypeMuscle().toString());
        if (exercise.getId() != 0) {
            intent.putExtra(ExerciseDetailActivityEdit.ID_EXERCISE, exercise.getId());
        }
        return intent;
    }

    private Exercise getExerciseFromIntent(Intent intent) {
        Exercise exercise = null;
        long id = intent.getLongExtra(ID_EXERCISE, 0);
        if (id != 0) {
            exerciseDao = new ExerciseDao(this);
            exercise = exerciseDao.getById(id);
        } else {
            exercise = new Exercise();
            exercise.setTypeMuscle(TypeMuscle.valueOf(intent.getStringExtra(ExerciseDetailActivityEdit.TYPE_MUSCLE_EXERCISE)));
        }
        return exercise;
    }

    @Override
    public void onClick(View v) {
        System.out.println(v);
        switch (v.getId()) {
            case R.id.exercise_activity_detail_edit_fab_save:
                if (title.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "времен, необходим тайтл вести", Toast.LENGTH_SHORT).show();

                } else {
                    inflateItemFromFields();
                    if (exercise.getId() > 0) {
                        updateItem();
                    } else saveItem();
                    finish();   //   createResultIntent();
                }
                break;
            case R.id.exercise_activity_detail_edit_fab_photo:
                // start metod photo_
                Toast.makeText(this, "времен, получим фото", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void updateItem() {
        exerciseDao = getExerciseDao();
        if (exerciseDao.update(exercise)) {
            Toast.makeText(this, "времен, упражнение упражнение изменено", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "времен, не возможно изменить", Toast.LENGTH_SHORT).show();

    }

    private void saveItem() {
        exerciseDao = getExerciseDao();
        if (exerciseDao.create(exercise) != 1) {
            Toast.makeText(this, "времен, упражнение сохранено", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "времен, не возможно сохранить", Toast.LENGTH_SHORT).show();
    }

    private ExerciseDao getExerciseDao() {
        if (exerciseDao == null) {
            exerciseDao = new ExerciseDao(this);
        }
        return exerciseDao;
    }

  /*  private void createResultIntent() {
        Intent intent = new Intent();
        intent.putExtra(TITLE_EXERCISE, title.getText().toString());
        intent.putExtra(DESCRIPTION_EXERCISE, description.getText().toString());
        intent.putExtra(TYPE_MUSCLE_EXERCISE, exercise.getTypeMuscle().toString());
        setResult(RESULT_OK, intent);

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        cansel create new back to fragment
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
            outState.putString(TITLE, title.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    private Exercise inflateItemFromFields() {
        exercise.setTitle(title.getText().toString());
        exercise.setDescription(description.getText().toString());
        //exercise.setImg();
        return exercise;
    }
}
