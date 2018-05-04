package info.upump.jym.activity.workout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.IChooseItem;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.WorkoutFragmentLoader;

import static info.upump.jym.activity.constant.Constants.DEFAULT_TYPE_CHOOSE;
import static info.upump.jym.activity.constant.Constants.ID;
import static info.upump.jym.activity.constant.Constants.LOADER_BY_TEMPLATE_TYPE;

public class WorkoutActivityForChoose extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Workout>>, IChooseItem<Workout> {
    private RecyclerView recyclerView;
    private WorkoutAdapter workoutAdapter;
    private List<Workout> workoutList =  new ArrayList<>();
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_for_choose);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.workout_choose_title_add_workouts);
        id = getIntent().getLongExtra(ID,0);

        getSupportLoaderManager().initLoader(0, null, this);

        workoutAdapter = new WorkoutAdapter(workoutList, DEFAULT_TYPE_CHOOSE, null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.activity_workout_for_choose_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workoutAdapter);
    }

    public static Intent createIntent(Context context, Cycle cycle){
        Intent intent = new Intent(context, WorkoutActivityForChoose.class);
        intent.putExtra(ID, cycle.getId());///
        return intent;
    }

    @Override
    public Loader<List<Workout>> onCreateLoader(int id, Bundle args) {
        WorkoutFragmentLoader workoutFragmentLoader = new WorkoutFragmentLoader(this, LOADER_BY_TEMPLATE_TYPE);
        return workoutFragmentLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Workout>> loader, List<Workout> data) {
        workoutList.clear();
        workoutList.addAll(data);
        workoutAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Workout>> loader) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void createIntentForChooseResult(Workout workout) {
        Intent intent = new Intent();
        WorkoutDao workoutDao = new WorkoutDao(this);
//        workoutDao.copyFromTemplate(workout.getId(), id);
//        List<Workout> workoutList = new ArrayList<>();
//        workoutList.add(workout);
        workoutDao.alter(workout.getId(), id);
        setResult(RESULT_OK, intent);
        finish();
    }
}
