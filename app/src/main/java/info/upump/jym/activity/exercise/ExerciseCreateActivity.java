package info.upump.jym.activity.exercise;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class ExerciseCreateActivity extends AppCompatActivity {
    private EditText title, description;
    private Spinner spinner;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private AppBarLayout appBarLayout;
    private Exercise exercise;
    private Uri uriImage;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.exercise_activity_create_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //   exerciseDescription = new ImageForItem();
        exercise = getItemFromIntent();

        imageView = findViewById(R.id.exercise_activity_create_image_view);
        collapsingToolbarLayout = findViewById(R.id.exercise_activity_create_collapsing);
        appBarLayout = findViewById(R.id.exercise_activity_create_appbar);
        title = findViewById(R.id.content_exercise_create_activity_title);
        description = findViewById(R.id.content_exercise_create_activity_web);
        spinner = findViewById(R.id.content_exercise_create_activity_spinner);

        String[] nameOfValues = getNameOfMuscle();
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
                setPic(uriImage);
            }
        }

        if (exercise.getTitle() == null) {
            collapsingToolbarLayout.setTitle(title.getHint().toString());
        } else collapsingToolbarLayout.setTitle(exercise.getTitle());
    }

    // можно убрать
    private Exercise getItemFromIntent() {
        return new Exercise();
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

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ExerciseCreateActivity.class);
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
                    setPic(uriImage);
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

    private void setPic(Uri uri) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_add_black_24dp)
                .error(R.drawable.ic_add_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(this).load(uri).apply(options).into(imageView);
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
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(getResources().getString(R.string.save));
        ad.setPositiveButton((getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();

            }
        });
        ad.setNegativeButton((getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = createIntentForResult(); // при создании из вне
                setResult(RESULT_CANCELED, intent);
                finishActivityWithAnimation();
            }
        });
        ad.show();

    }

    private Intent createIntentForResult() {
        Intent intent = new Intent();
        intent.putExtra(Constants.ID, exercise.getId());
        return intent;
    }

    private void save() {
        ExerciseDao exerciseDao = new ExerciseDao(this);
        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this,  R.string.toast_write_name, Toast.LENGTH_SHORT).show();
            return;
        }

        exercise = getChangeableItem();
        long id = exerciseDao.create(exercise);

        if (id != -1) {
            exercise.setId(id);
            Toast.makeText(this, R.string.toast_exercise_saved, Toast.LENGTH_SHORT).show();
            Intent intent = createIntentForResult(); // при создании из вне
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
        } else Toast.makeText(this,  R.string.toast_dont_save, Toast.LENGTH_SHORT).

                show();

    }

    private Exercise getChangeableItem() {
        ExerciseDescription  exerciseDescription = new ExerciseDescription();
        exerciseDescription.setTitle(title.getText().toString());
        if (uriImage != null) {
            exerciseDescription.setImg(uriImage.toString());
        } else exerciseDescription.setImg("");

        Exercise changeableExercise = new Exercise();
        changeableExercise.setComment(description.getText().toString());
        changeableExercise.setId(exercise.getId());
        changeableExercise.setStartDate(new Date());
        changeableExercise.setFinishDate(new Date());
        changeableExercise.setTemplate(true);
        changeableExercise.setDefaultType(false);
        int selectedItem = spinner.getSelectedItemPosition();
        TypeMuscle typeMuscle = getMuscle(selectedItem);
        changeableExercise.setTypeMuscle(typeMuscle);
        changeableExercise.setExerciseDescription(exerciseDescription);
        return changeableExercise;
    }


    private void finishActivityWithAnimation() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }
}
