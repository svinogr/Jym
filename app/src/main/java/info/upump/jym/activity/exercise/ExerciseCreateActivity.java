package info.upump.jym.activity.exercise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.bd.ExerciseDao;
import info.upump.jym.entity.Exercise;
import info.upump.jym.entity.ExerciseDescription;
import info.upump.jym.entity.TypeMuscle;

import static info.upump.jym.activity.constant.Constants.DEFAULT_IMAGE;
import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.DESCRIPTION;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.ID_DESCRIPTION;
import static info.upump.jym.activity.constant.Constants.IMAGE;
import static info.upump.jym.activity.constant.Constants.TEMPLATE_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.TITLE;
import static info.upump.jym.activity.constant.Constants.TYPE_MUSCLE;
import static info.upump.jym.activity.constant.Constants.UPDATE;
import static info.upump.jym.activity.constant.Constants.UPDATE_DELETE;

public class ExerciseCreateActivity extends AppCompatActivity {
    private EditText title, description;
    private Spinner spinner;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private Exercise exercise;
    private Uri uriImage;
//    private TextView spinner;
    private String[] nameOfValues;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                collapsingToolbarLayout.setTitle(msg.obj.toString());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_create);

        Toolbar toolbar = findViewById(R.id.exercise_activity_create_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exercise = getItemFromIntent();

        imageView = findViewById(R.id.exercise_activity_create_image_view);
        collapsingToolbarLayout = findViewById(R.id.exercise_activity_create_collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExtenddAppBar);
        title = findViewById(R.id.content_exercise_create_activity_title);
        description = findViewById(R.id.content_exercise_create_activity_web);
        spinner = findViewById(R.id.content_exercise_create_activity_spinner);

        nameOfValues = getNameOfMuscle();

        ArrayAdapter<String> dayArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameOfValues);
        spinner.setAdapter(dayArrayAdapter);
        spinner.setFocusableInTouchMode(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPic = createIntentForGetPic();
                startActivityForResult(photoPic, Constants.REQUEST_CODE_GALLERY_PHOTO);
            }
        });

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(200);
                if ((title.getText().toString().trim()).isEmpty()) {
                    handler.sendMessageDelayed(handler.obtainMessage(200, title.getHint()), 250);
                } else
                    handler.sendMessageDelayed(handler.obtainMessage(200, title.getText()), 250);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (savedInstanceState != null) {
            if (savedInstanceState.getString(Constants.URI_IMG) != null) {
                uriImage = Uri.parse(savedInstanceState.getString(Constants.URI_IMG));
            }
        }

        createViewFrom();
    }

    private void createViewFrom() {
        if (exercise.getId() != 0) {
            collapsingToolbarLayout.setTitle(exercise.getExerciseDescription().getTitle());
            title.setText(exercise.getExerciseDescription().getTitle());
            description.setText(exercise.getComment());
            spinner.setSelection(exercise.getTypeMuscle().ordinal());
//            spinner.setText(exercise.getTypeMuscle().getName());
        } else {
//            spinner.setText(exercise.getTypeMuscle().getName());
            collapsingToolbarLayout.setTitle(title.getHint().toString());
        }

        if (uriImage == null) {
            setPicUri(Uri.parse(exercise.getExerciseDescription().getImg()));
        } else setPicUri(uriImage);
    }

    private void setPicUri(Uri uri) {
        RequestOptions options = new RequestOptions()
                .error(R.drawable.iview_place_erore_exercise)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        if (exercise.isDefaultType()) {
            int ident = getResources().getIdentifier(exercise.getExerciseDescription().getImg(), "drawable", getPackageName());
            Glide.with(this).load(ident).apply(options).into(imageView);
        } else Glide.with(this).load(uri).apply(options).into(imageView);
    }

    private boolean onlyTypeMuscle() {
        Exercise exercises = getChangeableItem();
        if (!exercises.getExerciseDescription().getTitle().equals("")) return false;
        if (!exercises.getComment().equals("")) return false;

        return uriImage == null;
    }

    private Exercise getItemFromIntent() {
        long id = getIntent().getLongExtra(ID, 0);
        Exercise exercise;
        if (id != 0) {
            ExerciseDao exerciseDao = ExerciseDao.getInstance(this, null);
            exercise = exerciseDao.getById(id);
        } else {
            exercise = new Exercise();
            TypeMuscle typeMuscle = TypeMuscle.valueOf(getIntent().getStringExtra(TYPE_MUSCLE));
            exercise.setTypeMuscle(typeMuscle);
            ExerciseDescription exerciseDescription = new ExerciseDescription();
            exerciseDescription.setImg("");
            exerciseDescription.setDefaultImg(null);
            exercise.setExerciseDescription(exerciseDescription);
        }
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

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseCreateActivity.class);
        if(exercise.getId() == 0) { // иззв звлочкм
            intent.putExtra(TYPE_MUSCLE, exercise.getTypeMuscle().toString());
        }
        if (exercise.getId() != 0) {
            intent.putExtra(ID, exercise.getId());
        }
        return intent;
    }

    private Intent createIntentForGetPic() {
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        } else {
            intent = new Intent(Intent.ACTION_PICK);
        }
        intent.setType("image/*");
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CODE_GALLERY_PHOTO:
                    uriImage = data.getData();
                    setPicUri(uriImage);
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (uriImage != null) {
            outState.putString(Constants.URI_IMG, uriImage.toString());
        }
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
        return super.onOptionsItemSelected(item);
    }

    private void exit() {

        if (itemIsNotChanged() || onlyTypeMuscle()) {
            finishActivityWithAnimation();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle(getResources().getString(R.string.save));
            ad.setPositiveButton((getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (exercise.getId() != 0) {
                        update();
                    } else save();

                }
            });

            ad.setNegativeButton((getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = createIntentForResult(); // при создании из вне
                    setResult(RESULT_CANCELED);
                    finishActivityWithAnimation();
                }
            });
            ad.show();
        }
    }

    private void update() {
        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.toast_write_name, Toast.LENGTH_SHORT).show();
            return;
        }
        Exercise exerciseUpdate = getChangeableItem();
            Intent intent = createIntentForResult(exerciseUpdate);
            intent.putExtra(UPDATE_DELETE, UPDATE);
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
    }



    private Intent createIntentForResult(Exercise exercise) {
        Intent intent = new Intent();
        intent.putExtra(IMAGE, exercise.getExerciseDescription().getImg());
        intent.putExtra(DEFAULT_IMAGE, exercise.getExerciseDescription().getDefaultImg());
        intent.putExtra(TITLE, exercise.getExerciseDescription().getTitle());
        intent.putExtra(ID_DESCRIPTION, exercise.getExerciseDescription().getId());

        intent.putExtra(ID, exercise.getId());
        intent.putExtra(DESCRIPTION, exercise.getComment());
        intent.putExtra(TEMPLATE_TYPE_ITEM, exercise.isTemplate());
        intent.putExtra(DEFAULT_TYPE_ITEM, exercise.isDefaultType());
        intent.putExtra(TYPE_MUSCLE, exercise.getTypeMuscle().toString());
        return intent;
    }

    private void save() {
        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.toast_write_name, Toast.LENGTH_SHORT).show();
            return;
        }

        exercise = getChangeableItem();

        Intent  intent = createIntentForResult(exercise);
        setResult(RESULT_OK, intent);
        finishActivityWithAnimation();
    }

    private boolean itemIsNotChanged() {
        Exercise changeableItem = getChangeableItem();
        if (!changeableItem.getExerciseDescription().getTitle().equals(exercise.getExerciseDescription().getTitle()))
            return false;
        if (!changeableItem.getComment().equals(exercise.getComment())) return false;
        if (!(changeableItem.getTypeMuscle().toString().equals(exercise.getTypeMuscle().toString())))
            return false;
        return uriImage == null;
    }

    private Exercise getChangeableItem() {
        ExerciseDescription changeableExerciseDescription = new ExerciseDescription();
        if (uriImage != null) {
            changeableExerciseDescription.setImg(uriImage.toString());
        } else changeableExerciseDescription.setImg(exercise.getExerciseDescription().getImg());
        changeableExerciseDescription.setDefaultImg(exercise.getExerciseDescription().getDefaultImg());

        changeableExerciseDescription.setId(exercise.getExerciseDescription().getId());
        changeableExerciseDescription.setTitle(title.getText().toString());

        Exercise changeableExercise = new Exercise();
        changeableExercise.setId(exercise.getId());
        changeableExercise.setComment(description.getText().toString());
        changeableExercise.setDescriptionId(exercise.getDescriptionId());

        changeableExercise.setStartDate(new Date());
        changeableExercise.setFinishDate(new Date());

        changeableExercise.setTemplate(true);
        changeableExercise.setDefaultType(false);
        int selectedItem = spinner.getSelectedItemPosition();
        TypeMuscle typeMuscle = getMuscle(selectedItem);
        changeableExercise.setTypeMuscle(typeMuscle);
        changeableExercise.setExerciseDescription(changeableExerciseDescription);
        return changeableExercise;
    }

    private void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }
}
