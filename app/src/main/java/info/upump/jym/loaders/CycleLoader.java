package info.upump.jym.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.jym.bd.CycleDao;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Workout;

/**
 * Created by explo on 22.03.2018.
 */

public class CycleLoader extends AsyncTaskLoader<List<Cycle>> {
    private Context context;
    public CycleLoader(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Nullable
    @Override
    public List<Cycle> loadInBackground() {
        CycleDao workoutDao = new CycleDao(context);
        return workoutDao.getAll();

    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }

    @Override
    public void deliverResult(List<Cycle> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }


}
