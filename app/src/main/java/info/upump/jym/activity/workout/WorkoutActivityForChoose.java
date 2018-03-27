package info.upump.jym.activity.workout;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import info.upump.jym.R;
import info.upump.jym.adapters.WorkoutAdapter;
import info.upump.jym.entity.Workout;

public class WorkoutActivityForChoose extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Workout>>{
    private RecyclerView recyclerView;
    private WorkoutAdapter workoutAdapter;
    private List<Workout> workoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_for_choose);
    }


    @Override
    public Loader<List<Workout>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Workout>> loader, List<Workout> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Workout>> loader) {

    }
}
