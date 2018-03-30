package info.upump.jym.activity.workout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Day;
import info.upump.jym.entity.Workout;
import info.upump.jym.utils.LetterBitmap;

public class WorkoutCreateActivity extends AppCompatActivity {
    private EditText title, description;
    private Spinner spinner;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private AppBarLayout appBarLayout;
    private Workout workout;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                collapsingToolbarLayout.setTitle(msg.obj.toString());
            }

        }
    };

    private void setPic(int s) {
        Bitmap bitmap = Bitmap.createBitmap(100, 100,
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(getResources().getColor(getDay(s).getColor()));
        imageView.setImageBitmap(bitmap);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.workout_activity_create_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long idParent = intent.getLongExtra(Constants.ID, 0);
        workout = new Workout();
        workout.setParentId(idParent);

        imageView = findViewById(R.id.workout_activity_create_image_view);
        collapsingToolbarLayout = findViewById(R.id.workout_activity_create_collapsing);
        appBarLayout = findViewById(R.id.workout_activity_create_appbar);
        spinner = findViewById(R.id.content_workout_create_activity_spinner);
        description = findViewById(R.id.content_workout_create_activity_web);
        title = findViewById(R.id.content_workout_create_activity_title);

        String[] nameOfValues = getNameOfDays();
        ArrayAdapter<String> dayArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameOfValues);
        spinner.setAdapter(dayArrayAdapter);
        spinner.setFocusableInTouchMode(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                setPic(arg2);
                appBarLayout.setExpanded(true);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
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
                } else handler.sendMessageDelayed(handler.obtainMessage(200, title.getText()), 250);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (workout.getTitle() == null) {
            collapsingToolbarLayout.setTitle(title.getHint().toString());
        } else collapsingToolbarLayout.setTitle(workout.getTitle());

    }

    private String[] getNameOfDays() {
        Day[] values = Day.values();
        String[] nameOfValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            nameOfValues[i] = getResources().getString(values[i].getName());
        }
        return nameOfValues;
    }

    private Day getDay(int i) {
        Day[] values = Day.values();
        return values[i];
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, WorkoutCreateActivity.class);
        return intent;
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
                finishActivityWithAnimation();
            }
        });
        ad.show();

    }

    private Intent createIntentForResult() {
        Intent intent = new Intent();
        intent.putExtra(Constants.ID, workout.getId());
        return intent;
    }

    private void save() {
        WorkoutDao workoutDao = new WorkoutDao(this);
        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "времен, необходтио ввести имя", Toast.LENGTH_SHORT).show();
            return;
        }
        workout.setTitle(title.getText().toString());
        workout.setComment(description.getText().toString());
        workout.setStartDate(new Date());
        workout.setFinishDate(new Date());
        workout.setTemplate(true);
        workout.setDefaultType(false);
        int selectedItem = spinner.getSelectedItemPosition();
        Day day = getDay(selectedItem);
        workout.setDay(day);
        long id = workoutDao.create(workout);
        if (id != -1) {
            workout.setId(id);
            Toast.makeText(this, "времен, программа сохранена", Toast.LENGTH_SHORT).show();
            finishActivityWithAnimation();
        } else Toast.makeText(this, "времен, не возможно сохранить", Toast.LENGTH_SHORT).show();
    }

    private void finishActivityWithAnimation() {
        Intent intent = createIntentForResult(); // при создании из вне
        setResult(RESULT_OK, intent);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }

}
