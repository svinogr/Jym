package info.upump.jym.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import info.upump.jym.bd.WorkoutDao;
import info.upump.jym.entity.Workout;

import static info.upump.jym.activity.constant.Constants.*;


public class WorkoutFragmentLoader extends AsyncTaskLoader<List<Workout>> {
    private Context context;
    private int operation;
    private long parentId;

    public WorkoutFragmentLoader(@NonNull Context context, int operation) {
        super(context);
        this.context = context;
        this.operation = operation;
    }

    public WorkoutFragmentLoader(@NonNull Context context, int operation, long id) {
        this(context, operation);
        this.parentId = id;
    }

    @Nullable
    @Override
    public List<Workout> loadInBackground() {
        WorkoutDao workoutDao = new WorkoutDao(context);
        List<Workout> workoutList = null;
        switch (operation) {
            case LOADER_BY_PARENT_ID:
                workoutList = workoutDao.getByParentId(parentId);
                break;
                case LOADER_BY_DEFAULT_TYPE:  // для готовых тренировок
                workoutList = workoutDao.getDefault();
                break;
            case LOADER_BY_USER_TYPE:
                workoutList = workoutDao.getTemplateUser();// эзерские темплетй
                break;
            case LOADER_BY_TEMPLATE_TYPE:
                workoutList =  workoutDao.getAllTemplate();// для выбора всех темплейтов, в чуз воркаут

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
