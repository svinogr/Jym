package info.upump.jym.activity.workout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import info.upump.jym.R;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Day;
import info.upump.jym.entity.Workout;
import info.upump.jym.utils.LetterBitmap;

public class WorkoutCreateActivity extends AppCompatActivity {
    private EditText title, description;
    private Spinner spinner;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private Workout workout;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                setPic(msg.obj.toString());
            }
        }
    };

    private void setPic(String s) {
       // int i = Integer.parseInt(s);
        int COVER_IMAGE_SIZE = 500; //in pixels
        LetterBitmap letterBitmap = new LetterBitmap(this);
        Bitmap letterTile = letterBitmap.getLetterTile("", s, COVER_IMAGE_SIZE, COVER_IMAGE_SIZE);
        imageView.setImageBitmap(letterTile);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.workout_activity_create_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        workout = new Workout();

        imageView = findViewById(R.id.workout_activity_create_image_view);
        collapsingToolbarLayout = findViewById(R.id.workout_activity_create_collapsing);
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
                System.out.println(arg2 + " " + arg3 + " " + arg1 + " " + arg0);
                handler.removeMessages(100);
                handler.sendMessageDelayed(handler.obtainMessage(100, arg2), 250);

            }

            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });





    }

    private String[] getNameOfDays() {
        Day[] values = Day.values();
        String[] nameOfValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            nameOfValues[i] = getResources().getString(values[i].getName());
        }
        return nameOfValues;
    }

    public static Intent createIntent(Context context, long idParent) {
        Intent intent = new Intent(context, WorkoutCreateActivity.class);
        intent.putExtra(Constants.ID, idParent);
        return intent;
    }
}
