package info.upump.jym.activity.workout;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import info.upump.jym.R;
import info.upump.jym.activity.IChooseItem;
import info.upump.jym.activity.constant.Constants;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.entity.Workout;
import info.upump.jym.loaders.WorkoutFragmentLoader;

public class WorkoutActivityForChoose extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Workout>>, IChooseItem<Workout> {
    private RecyclerView recyclerView;
    private WorkoutAdapter workoutAdapter;
    private List<Workout> workoutList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_for_choose);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(0, null, this);

        workoutAdapter = new WorkoutAdapter(workoutList, WorkoutAdapter.DEFAULT_TYPE_CHOOSE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.activity_workout_for_choose_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workoutAdapter);
    }

    public static Intent createIntent(Context context){
        Intent intent = new Intent(context, WorkoutActivityForChoose.class);
        return intent;
    }


    @Override
    public Loader<List<Workout>> onCreateLoader(int id, Bundle args) {
        WorkoutFragmentLoader workoutFragmentLoader = new WorkoutFragmentLoader(this,WorkoutFragmentLoader.BY_TEMPLATE_TYPE);
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
        intent.putExtra(Constants.ID, workout.getId());
        setResult(RESULT_OK, intent);
        finish();
    }
}
