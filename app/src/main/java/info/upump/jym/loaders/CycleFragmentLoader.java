package info.upump.jym.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.jym.activity.constant.Constants;
import info.upump.jym.bd.CycleDao;
import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Workout;

/**
 * Created by explo on 22.03.2018.
 */

public class CycleFragmentLoader extends AsyncTaskLoader<List<Cycle>> {
    private Context context;
    private int type;

    public CycleFragmentLoader(@NonNull Context context, int type) {
        super(context);
        this.type = type;
        this.context = context;
    }

    @Nullable
    @Override
    public List<Cycle> loadInBackground() {
        CycleDao cycleDao = new CycleDao(context);
        List<Cycle> cycleList = null;
        switch (type) {
            case Constants.LOADER_BY_DEFAULT_TYPE:
                cycleList = cycleDao.getAllDefault();
                break;
            case Constants.LOADER_BY_USER_TYPE:
                cycleList = cycleDao.getAllUser();
                break;
        }

        return cycleList;

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
