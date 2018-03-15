package info.upump.jym;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class ExerciseDetailActivityEdit extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fabSave;
    FloatingActionButton fabPhoto;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = findViewById(R.id.exercise_activity_edit_collapsing);
        collapsingToolbarLayout.setTitle(" ");

        fabSave = findViewById(R.id.fab_save);
        fabPhoto = findViewById(R.id.fab_save);
        fabSave.setOnClickListener(this);
        fabPhoto.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
