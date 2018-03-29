package info.upump.jym.loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Workout;

/**
 * Created by explo on 23.03.2018.
 */

public class WorkoutFragmentLoader extends AsyncTaskLoader<List<Workout>> {
    private Context context;
    public final static int BY_PARENT_ID = 1;
    public final static int BY_TEMPLATE_TYPE = 2;
    private int operation;
    private long id;

    public WorkoutFragmentLoader(@NonNull Context context, int operation) {
        super(context);
        this.context = context;
        this.operation = operation;
    }
    public WorkoutFragmentLoader(@NonNull Context context, int operation, long id) {
        this(context,operation);
        this.id = id;
    }

    @Nullable
    @Override
    public List<Workout> loadInBackground() {
        WorkoutDao workoutDao = new WorkoutDao(context);
        List<Workout> workoutList =null;
        switch (operation){
            case BY_PARENT_ID:
               workoutList =  workoutDao.getByParentId(id);
                break;
            case BY_TEMPLATE_TYPE:
                workoutList = workoutDao.getTemplate();
        }
        return workoutList;
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
    public void deliverResult(List<Workout> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
