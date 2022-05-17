package info.upump.jym.activity.workout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.android.material.appbar.AppBarLayout;
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
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;

import info.upump.jym.R;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Day;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.DAY;
import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.DESCRIPTION;
import static info.upump.jym.activity.constant.Constants.FINISH_DATA;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.PARENT_ID;
import static info.upump.jym.activity.constant.Constants.START_DATA;
import static info.upump.jym.activity.constant.Constants.TEMPLATE_TYPE_ITEM;
import static info.upump.jym.activity.constant.Constants.TITLE;
import static info.upump.jym.activity.constant.Constants.WEEK_EVEN;

public class WorkoutCreateActivity extends AppCompatActivity {
    private EditText title, description;
    private Spinner spinner;
    private Switch aSwitch;
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
        Bitmap bitmap = Bitmap.createBitmap(1080, 200,
                Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(getResources().getColor(getDay(s).getColor()));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.color.colorTextLabel)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        Glide.with(this).load(bitmap).apply(options).into(imageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);
        Toolbar toolbar = findViewById(R.id.workout_activity_create_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        workout = getFromIntent();

        imageView = findViewById(R.id.workout_activity_create_image_view);
        collapsingToolbarLayout = findViewById(R.id.workout_activity_create_collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExtenddAppBar);
        appBarLayout = findViewById(R.id.workout_activity_create_appbar);
        spinner = findViewById(R.id.content_workout_create_activity_spinner);
        description = findViewById(R.id.content_workout_create_activity_web);
        title = findViewById(R.id.content_workout_create_activity_title);
        aSwitch = findViewById(R.id.content_workout_create_activity_switch);

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

        createViewFrom();
    }

    private void createViewFrom() {
        if (workout.getId() != 0) {
            collapsingToolbarLayout.setTitle(title.getHint().toString());

            spinner.setSelection(workout.getDay().ordinal());
            description.setText(workout.getComment());
        }
        aSwitch.setChecked(workout.isWeekEven());
        title.setText(workout.getTitle());
    }

    private Workout getFromIntent() {
        long id = getIntent().getLongExtra(ID, 0);
        long idParent = getIntent().getLongExtra(PARENT_ID, 0);
        Workout workout;
        switch ((int) id) {
            case 0:
                workout = new Workout();
                workout.setParentId(idParent);
                break;
            case -1:
                workout = new Workout();
                workout.setTemplate(true);
                workout.setDefaultType(false);
                break;
            default:
                WorkoutDao workoutDao = WorkoutDao.getInstance(this, null);
                workout = workoutDao.getById(id);
                break;
        }
        return workout;
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

    public static Intent createIntent(Context context, Workout workout) {
        Intent intent = new Intent(context, WorkoutCreateActivity.class);
        if (workout != null) {
            intent.putExtra(ID, workout.getId());
            intent.putExtra(PARENT_ID, workout.getParentId());
        }
        return intent;
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
        if (itemIsNotChanged() || onlyWeek()) {
            finishActivityWithAnimation();
        } else {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setTitle(getResources().getString(R.string.save));
            ad.setPositiveButton((getResources().getString(R.string.yes)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (workout.getId() != 0) {
                        update();
                    } else save();
                }
            });
            ad.setNegativeButton((getResources().getString(R.string.no)), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = createIntentForResult(0); // при создании из вне
                    setResult(RESULT_CANCELED, intent);
                    finishActivityWithAnimation();
                }
            });
            ad.show();
        }
    }

    private boolean onlyWeek() {
        Workout workoutC = getChangeableItem();
        if (!workoutC.getTitle().equals("")) return false;
        return workoutC.getComment().equals("");
    }

    private boolean itemIsNotChanged() {
        Workout changeableItem = getChangeableItem();
        if (!changeableItem.getTitle().equals(workout.getTitle())) return false;
        if (!changeableItem.getComment().equals(workout.getComment())) return false;
        if (changeableItem.isWeekEven() != workout.isWeekEven()) return false;
        return changeableItem.getDay().toString().equals(workout.getDay().toString());
    }

    public Workout getChangeableItem() {
        Workout changeable = new Workout();
        Day day = getDay(spinner.getSelectedItemPosition());
        changeable.setId(workout.getId());
        changeable.setTemplate(workout.isTemplate());
        changeable.setDefaultType(workout.isDefaultType());
        changeable.setWeekEven(aSwitch.isChecked());
        changeable.setDay(day);
        if (workout.getId() == 0) {
            changeable.setStartDate(new Date());
            changeable.setFinishDate(new Date());
        } else {
            changeable.setStartDate(workout.getStartDate());
            changeable.setFinishDate(workout.getFinishDate());
        }
        changeable.setParentId(workout.getParentId());
        changeable.setTitle(title.getText().toString());
        changeable.setComment(description.getText().toString());
        return changeable;
    }


    private Intent createIntentForResult(long id) {
        Intent intent = new Intent();
        intent.putExtra(ID, id);
        return intent;
    }

    private void save() {
        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.toast_write_name, Toast.LENGTH_SHORT).show();
            return;
        }
        Workout workoutSave = getChangeableItem();
        Toast.makeText(this, R.string.toast_workout_saved, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(TITLE, workoutSave.getTitle());
        intent.putExtra(DESCRIPTION, workoutSave.getComment());
        intent.putExtra(WEEK_EVEN, workoutSave.isWeekEven());
        intent.putExtra(DEFAULT_TYPE_ITEM, workoutSave.isDefaultType());
        intent.putExtra(TEMPLATE_TYPE_ITEM, workoutSave.isTemplate());
        intent.putExtra(DAY, workoutSave.getDay().toString());
        intent.putExtra(START_DATA, workoutSave.getStartStringFormatDate());
        intent.putExtra(FINISH_DATA, workoutSave.getFinishStringFormatDate());
        intent.putExtra(PARENT_ID, workoutSave.getParentId());
        setResult(RESULT_OK, intent);
        finishActivityWithAnimation();
    }

    private void update() {
        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.toast_write_name, Toast.LENGTH_SHORT).show();
            return;
        }
        Workout workoutUpdate = getChangeableItem();
        WorkoutDao workoutDao = WorkoutDao.getInstance(this, null);
        if (workoutDao.update(workoutUpdate)) {
            Toast.makeText(this, R.string.toast_workout_update, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finishActivityWithAnimation();
        } else Toast.makeText(this, R.string.toast_dont_update, Toast.LENGTH_SHORT).show();
    }

    private void finishActivityWithAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else finish();
    }
}
