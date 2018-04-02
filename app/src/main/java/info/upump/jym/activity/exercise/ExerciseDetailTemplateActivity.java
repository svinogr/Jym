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
import info.upump.jym.entity.TypeMuscle;

import static info.upump.jym.activity.constant.Constants.ID;

public class ExerciseDetailTemplateActivity extends AppCompatActivity {
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

    public static Intent createIntent(Context context, Exercise exercise) {
        Intent intent = new Intent(context, ExerciseDetailTemplateActivity.class);
        intent.putExtra(ID, exercise.getId());
        return intent;
    }

    private Exercise getItemFromIntent() {
        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, 0);
        ExerciseDao exerciseDao = new ExerciseDao(this);
        return exerciseDao.getById(id);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (uriImage != null) {
            outState.putString(Constants.URI_IMG, uriImage.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult  " + requestCode + "  " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_CODE_GALLERY_PHOTO:
                    uriImage = data.getData();
                    //  exercise.setImg(uriImage.toString());
                    //setPic(Uri.parse(exercise.getImg()));
                    setPic(uriImage);
                    break;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail_tempalte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.exercise_detail_template_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exercise = getItemFromIntent();

        imageView = findViewById(R.id.exercise_detail_template_activity_image_view);
        collapsingToolbarLayout = findViewById(R.id.exercise_detail_template_activity_collapsing);
        appBarLayout = findViewById(R.id.exercise_detail_template_activity_appbar);
        title = findViewById(R.id.content_exercise_detail_template_activity_title);
        description = findViewById(R.id.content_exercise_detail_template_activity_web);
        spinner = findViewById(R.id.content_exercise_detail_template_activity_spinner);

        String[] nameOfValues = getNameOfMuscle();
        ArrayAdapter<String> dayArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameOfValues);
        spinner.setAdapter(dayArrayAdapter);
        spinner.setFocusableInTouchMode(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO взможно стоит убрать
                //appBarLayout.setExpanded(true);
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

        createViewFrom();

        if (savedInstanceState != null) {
            if (savedInstanceState.getString(Constants.URI_IMG) != null) {
                // exercise.setImg(savedInstanceState.getString(Constants.URI_IMG));

                // setPic(Uri.parse(exercise.getImg()));
                uriImage = Uri.parse(savedInstanceState.getString(Constants.URI_IMG));
                setPic(uriImage);
            }
        }

    }

    private void createViewFrom() {

        //TODO убрать взможность сохранить для не своих
        title.setText(exercise.getTitle());

        if (exercise.getDescription() != null) {
            description.setText(exercise.getDescription());
        }

        spinner.setSelection(exercise.getTypeMuscle().ordinal());

        if (exercise.getImg() != null) {
            setPic(Uri.parse(exercise.getImg()));
        }
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
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
        if (itemIsNotChanged()) {
            finishActivityWithAnimation();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle(getResources().getString(R.string.save));
            ad.setPositiveButton((getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    update();

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

    }

    private boolean itemIsNotChanged() {
        Exercise changeableItem = getChangeableItem();
        System.out.println(changeableItem);
        if (!changeableItem.getTitle().equals(exercise.getTitle())) return false;
        if (!changeableItem.getDescription().equals(exercise.getDescription())) return false;
        if (!(changeableItem.getTypeMuscle().toString().equals(exercise.getTypeMuscle().toString())))
            return false;
        if (uriImage != null) return false;
        return true;
    }

    private Exercise getChangeableItem() {
        Exercise changeableExercise = new Exercise();
        changeableExercise.setId(exercise.getId());
        changeableExercise.setTitle(title.getText().toString());
         changeableExercise.setDescription(description.getText().toString());

        changeableExercise.setStartDate(new Date());
        changeableExercise.setFinishDate(new Date());
        changeableExercise.setTemplate(true);
        changeableExercise.setDefaultType(false);
        int selectedItem = spinner.getSelectedItemPosition();
        TypeMuscle typeMuscle = getMuscle(selectedItem);
        changeableExercise.setTypeMuscle(typeMuscle);
        if (uriImage != null) {
            changeableExercise.setImg(uriImage.toString());
        } else changeableExercise.setImg(exercise.getImg());
        return changeableExercise;
    }


    private void update() {
        ExerciseDao exerciseDao = new ExerciseDao(this);
        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }
        Exercise exerciseUpdate = getChangeableItem();
        boolean id = exerciseDao.update(exerciseUpdate);
        if (id) {
            Toast.makeText(this, "времен, программа сохранена", Toast.LENGTH_SHORT).show();
            Intent intent = createIntentForResult(); // при создании из вне
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, не возможно сохранить", Toast.LENGTH_SHORT).show();
    }

    private void finishActivityWithAnimation() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }


    private Intent createIntentForResult() {
        Intent intent = new Intent();
        intent.putExtra(Constants.ID, exercise.getId());
        return intent;
    }














  /*

    private Exercise getExerciseFromIntent(Intent intent) {
        exerciseDao = new ExerciseDao(this);
        long longExtra = intent.getLongExtra(ID_EXERCISE, 0);
        exercise = exerciseDao.getById(longExtra);
        return exercise;
    }

    private void setPic() {
        Uri uri = null;
        if(exercise.getImg()!=null) {
            uri = Uri.parse(exercise.getImg());
        }
        System.out.println("exer uri "+ exercise.getImg());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_add_black_24dp)
                .error(R.drawable.ic_add_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(this).load(uri).apply(options).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishActivityWithAnimation();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exercise_activity_detail_fab_edit:
             startActivityWithoutAnimation();
                break;
            case R.id.exercise_activity_detail_fab_delete:
                Snackbar.make(v, "ТОчно удадить", Snackbar.LENGTH_LONG)
                        .setAction("сто пудов", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteItem();
                              finishActivityWithAnimation();
                            }
                        }).show();
        }
    }

    private void finishActivityWithAnimation(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }
    private void finishActivityWithoutAnimation(){
      finish();
    }

    private void startActivityAnimation() {
        Intent intent = ExerciseDetailActivityEdit.createIntent(this, exercise);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View sharedViewIm = imageView;
            View sharedViewT = description;
            String transitionNameIm = "exercise_card_layout_image";
            String transitionNameT = "exercise_detail_activity_description";
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)
                            this,
                    Pair.create(sharedViewIm, transitionNameIm),  Pair.create(sharedViewT, transitionNameT));
            startActivity(intent, transitionActivityOptions.toBundle());
        } else startActivity(intent);
    }
    private void startActivityWithoutAnimation(){
        Intent intent = ExerciseDetailActivityEdit.createIntent(this, exercise);
        startActivity(intent);
    }

    private void deleteItem() {
        if (exerciseDao.delete(exercise)) {
            Toast.makeText(this, "времен, упражнение удалено", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "времен, не возможно удалить", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createViewFrom();

    }
*/
}
